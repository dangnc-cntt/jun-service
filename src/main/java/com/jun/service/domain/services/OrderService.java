package com.jun.service.domain.services;

import com.jun.service.app.dtos.CartProductDTO;
import com.jun.service.app.dtos.OrderDTO;
import com.jun.service.app.dtos.VnpDTO;
import com.jun.service.domain.entities.Order;
import com.jun.service.domain.entities.Product;
import com.jun.service.domain.entities.ProductOption;
import com.jun.service.domain.entities.VoucherAccount;
import com.jun.service.domain.entities.types.PaymentMethod;
import com.jun.service.domain.entities.voucher.Voucher;
import com.jun.service.domain.exceptions.BadRequestException;
import com.jun.service.domain.exceptions.LockTimeoutException;
import com.jun.service.domain.exceptions.ResourceExitsException;
import com.jun.service.domain.exceptions.ResourceNotFoundException;
import com.jun.service.domain.utils.LockManager;
import com.jun.service.domain.utils.Vnp_Ultil;
import com.mongodb.MongoCommandException;
import com.mongodb.MongoException;
import jun.message.CartProduct;
import jun.message.OrderMessage;
import lombok.extern.log4j.Log4j2;
import org.redisson.api.RLock;
import org.springframework.data.mongodb.MongoTransactionException;
import org.springframework.data.mongodb.UncategorizedMongoDbException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Log4j2
public class OrderService extends BaseService {

  @Retryable(
      value = {MongoCommandException.class, MongoException.class},
      exclude = {MongoTransactionException.class, UncategorizedMongoDbException.class},
      backoff = @Backoff(delay = 10),
      maxAttempts = 10)
  public Object order(OrderDTO dto, Integer accountId)
      throws InterruptedException, LockTimeoutException, UnsupportedEncodingException {

    if (dto == null || dto.getProducts() == null || dto.getProducts().size() == 0) {
      throw new BadRequestException("Product null");
    }
    RLock lock = lockManager.lockAccount(accountId.toString());
    boolean success =
        lock.tryLock(LockManager.WAIT_TIME, LockManager.TIME_LOCK_IN_SECOND, TimeUnit.SECONDS);
    long startLockTime = System.currentTimeMillis();

    if (success) {
      DefaultTransactionDefinition def = new DefaultTransactionDefinition();
      def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
      TransactionStatus status = transactionManager.getTransaction(def);
      try {

        // send to kafka
        OrderMessage orderMessage = new OrderMessage();
        orderMessage = modelMapper.toOrderMessage(dto);
        orderMessage.setOrderedBy(accountId);
        List<CartProduct> cartProducts = new ArrayList<>();
        for (CartProductDTO product : dto.getProducts()) {
          CartProduct cartProduct = new CartProduct();
          cartProduct.setId(product.getId());
          cartProduct.setOptionId(product.getOptionId());
          cartProducts.add(cartProduct);
        }

        long orderId = generateSequence(Order.SEQUENCE_NAME);
        orderMessage.setProductList(cartProducts);
        orderMessage.setVoucherId(dto.getVoucherId());
        orderMessage.setId(Vnp_Ultil.getRandomNumber(5) + orderId);
        orderMessage.setPrice(dto.getTotal());

        String result = "";

        if (dto.getPaymentMethod().equals(PaymentMethod.CASH)) {
          result = "Đặt hàng thành công!";
        } else {
          if (dto.getBankCode() != null) {
            throw new BadRequestException("No bank is selected");
          }
          VnpDTO vnpDTO;
          if (dto.getVoucherId() != null && dto.getVoucherId() != -1) {
            Voucher voucher = voucherStorage.findById(dto.getVoucherId());

            if (voucher == null || voucher.getExpiryDate().isAfter(LocalDateTime.now())) {
              throw new ResourceNotFoundException("Voucher not found or out of expiry time");
            }

            vnpDTO =
                new VnpDTO(
                    orderId,
                    dto.getBankCode(),
                    (int) ((dto.getTotal() - dto.getDiscount() - voucher.getDiscount()) + 100));
          } else {
            vnpDTO =
                new VnpDTO(
                    orderId, dto.getBankCode(), (int) ((dto.getTotal() - dto.getDiscount()) + 100));
          }

          result = requestToVNP(vnpDTO);
        }

        producer.sendOrderMessage(orderMessage);

        if (System.currentTimeMillis() - startLockTime < LockManager.TIME_LOCK_IN_SECOND * 1000) {
          transactionManager.commit(status);
          log.info("=====commit transaction---->");
        } else {
          log.error("========Exception: program execution time is too long, lock timeout");
          throw new LockTimeoutException();
        }

        return result;
      } catch (MongoTransactionException | UncategorizedMongoDbException ex) {
        log.info("=====rollback runtime---->");
        transactionManager.rollback(status);
        mongoExceptionUtils.processException(ex);
      } catch (UnsupportedEncodingException e) {
        throw e;
      } finally {
        if (lock != null) {
          lock.unlockAsync();
        }
      }
    } else {
      log.error("========Exception: program execution time is too long, lock timeout");
      throw new LockTimeoutException();
    }
    return null;
  }

  //  public static void main(String[] args) {
  //    String test = Vnp_Ultil.getRandomNumber(5) + 123456;
  //    System.out.println(test);
  //    System.out.println(test.substring(5, test.length()));
  //  }

  public void processOrder(List<OrderMessage> orderMessages) {
    if (orderMessages != null && orderMessages.size() > 0) {
      List<ProductOption> optionList = new ArrayList<>();
      List<Order> orders = new ArrayList<>();
      List<VoucherAccount> voucherAccounts = new ArrayList<>();
      for (OrderMessage orderMessage : orderMessages) {
        Long orderId =
            Long.parseLong(orderMessage.getId().substring(5, orderMessage.getId().length()));
        Order order = orderRepository.findOrderById(orderId);
        if (order != null) {
          throw new ResourceExitsException("Order is exist!");
        }
        order = new Order();

        if (orderMessage.getProductList() == null || orderMessage.getProductList().size() == 0) {
          throw new ResourceExitsException("List product null");
        }

        List<Order.ProductView> productViews = new ArrayList<>();

        for (CartProduct cartProduct : orderMessage.getProductList()) {
          Product product = productStorage.findById(cartProduct.getId());
          if (product == null) {
            throw new ResourceNotFoundException("No product found!");
          }
          Order.ProductView productView = new Order.ProductView();
          productView.setId(product.getId());
          productView.setCode(product.getCode());
          productView.setPrice(product.getPrice());
          productView.setCostPrice(product.getCostPrice());
          productView.setName(product.getName());
          productView.setDiscount(product.getDiscount());
          ProductOption option =
              productOptionRepository.findProductOptionByIdAndProductId(
                  cartProduct.getOptionId(), product.getId());
          if (option == null) {
            throw new ResourceNotFoundException("No option found!");
          }

          int amount = option.getAmount() - orderMessage.getAmount();
          if (amount < 0) {
            throw new ResourceNotFoundException("Out of product with option " + option.getId());
          }
          option.setAmount(amount);
          optionList.add(option);

          productView.setOption(option);
          productView.getOption().setAmount(orderMessage.getAmount());
          productViews.add(productView);
        }
        Voucher voucher = voucherStorage.findById(orderMessage.getVoucherId());

        if (voucher == null) {
          throw new ResourceNotFoundException("Voucher not found");
        }

        if (orderMessage.getVoucherId() != -1) {
          Order.VoucherView voucherView = new Order.VoucherView();
          voucherView.setCode(voucher.getCode());
          voucherView.setType(voucher.getType());
          voucherView.setId(voucher.getId());
          voucherView.setDiscount(voucher.getDiscount());
          voucher.setExpiryDate(voucher.getExpiryDate());

          VoucherAccount voucherAccount =
              voucherAccountRepository.findVoucherAccountByAccountIdAndVoucherId(
                  orderMessage.getOrderedBy(), voucher.getId());
          voucherAccounts.add(voucherAccount);

          order.assign(productViews, orderId, voucherView, orderMessage);
        } else {
          order.assign(productViews, orderId, null, orderMessage);
        }
      }

      orderRepository.saveAll(orders);
      productOptionRepository.saveAll(optionList);
      voucherAccountRepository.deleteAll(voucherAccounts);
    }
  }
}
