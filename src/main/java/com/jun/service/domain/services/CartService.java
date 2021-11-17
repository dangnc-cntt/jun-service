package com.jun.service.domain.services;

import com.jun.service.app.dtos.CartProductDTO;
import com.jun.service.app.responses.CartProductResponse;
import com.jun.service.app.responses.ProductResponse;
import com.jun.service.domain.entities.Cart;
import com.jun.service.domain.entities.ProductOption;
import com.jun.service.domain.exceptions.BadRequestException;
import com.jun.service.domain.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService extends BaseService {
  public List<CartProductResponse> findByAccountId(Integer accountId) {
    Cart cart = cartRepository.findByAccountId(accountId);
    if (cart == null || cart.getProducts() == null || cart.getProducts().size() == 0) {
      return new ArrayList<>();
    }
    return findByDTO(cart.getProducts());
  }

  public boolean checkProduct(CartProductDTO dto) {
    ProductResponse product = productService.findByIdNoException(dto.getId());
    return product != null;
  }

  public List<CartProductResponse> findByDTO(List<CartProductDTO> dtoList) {
    if (dtoList == null || dtoList.size() == 0) {
      return new ArrayList<>();
    }

    List<CartProductResponse> productResponses = new ArrayList<>();
    for (CartProductDTO dto : dtoList) {
      CartProductResponse response = productService.findByIdNoExceptionForCart(dto.getId());

      if (response == null) {
        throw new ResourceNotFoundException("No product foudnd!");
      }
      ProductOption option =
          productOptionRepository.findProductOptionByIdAndProductId(dto.getOptionId(), dto.getId());
      if (option == null || option.getAmount() < dto.getAmount()) {
        throw new BadRequestException("Option not found or out of amount!");
      }
      option.setAmount(dto.getAmount());
      response.setOption(option);
      productResponses.add(response);
    }
    return productResponses;
  }

  public List<CartProductResponse> addProductToCart(CartProductDTO dto, Integer accountId) {
    if (dto == null) {
      throw new BadRequestException("No product or option be selected");
    }
    Cart cart = cartRepository.findByAccountId(accountId);
    if (cart == null) {
      cart = new Cart();
      cart.setAccountId(accountId);
      cart.setProducts(new ArrayList<>());
    }
    if (!checkProduct(dto)) {
      throw new ResourceNotFoundException("No product found");
    }
    List<CartProductDTO> listCartProdDTO =
        cart.getProducts() == null ? new ArrayList<>() : cart.getProducts();

    int count = 0;
    if (listCartProdDTO.size() > 0) {
      for (CartProductDTO cartProductDTO : listCartProdDTO) {
        if (cartProductDTO.getId().equals(dto.getId())) {
          if (cartProductDTO.getOptionId().equals(dto.getOptionId())) {
            cartProductDTO.setAmount(
                cartProductDTO.getAmount() == null
                    ? dto.getAmount()
                    : cartProductDTO.getAmount() + dto.getAmount());
          } else {
            cartProductDTO.setOptionId(dto.getOptionId());
            cartProductDTO.setAmount(dto.getAmount());
          }
        } else {
          listCartProdDTO.add(dto);
        }
      }
    } else {
      listCartProdDTO.add(dto);
    }
    //    Predicate<CartProductDTO> dtoPredicate =
    //        s -> {
    //          return Objects.equals(s.getId(), dto.getId())
    //              && Objects.equals(s.getOptionId(), dto.getOptionId());
    //        };
    //    Predicate<CartProductDTO> predicate =
    //        s -> {
    //          return Objects.equals(s.getId(), dto.getId());
    //        };
    //
    //    CartProductDTO cartProductDTO = new CartProductDTO();
    //    if (listCartProdDTO.size() > 0) {
    //      List<CartProductDTO> cartProductDTOs =
    //          listCartProdDTO.stream().filter(dtoPredicate).collect(Collectors.toList());
    //      if (cartProductDTOs.size() > 0) {
    //        cartProductDTO = cartProductDTOs.stream().findFirst().get();
    //        cartProductDTO.setAmount(
    //            cartProductDTO.getAmount() == null
    //                ? dto.getAmount()
    //                : cartProductDTO.getAmount() + dto.getAmount());
    //
    //      }
    //    }
    cart.setProducts(listCartProdDTO);
    cart = cartStorage.save(cart);
    return findByDTO(cart.getProducts());
  }

  public List<CartProductResponse> update(List<CartProductDTO> dtoList, Integer accountId) {
    if (dtoList != null && dtoList.size() > 0) {

      Cart cart = cartRepository.findByAccountId(accountId);
      if (cart == null) {
        cart = new Cart();
        cart.setAccountId(accountId);
      }
      List<CartProductDTO> listProduct = new ArrayList<>();

      for (CartProductDTO dto : dtoList) {
        if (checkProduct(dto)) {
          listProduct.add(dto);
        }
      }
      cart.setProducts(listProduct);
      cart = cartStorage.save(cart);
      return findByDTO(cart.getProducts());
    }
    return new ArrayList<>();
  }
}
