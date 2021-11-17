package com.jun.service.domain.services;

import com.iprediction.email.MailMessage;
import com.jun.service.app.dtos.ChangePasswordDTO;
import com.jun.service.app.dtos.RegisterDTO;
import com.jun.service.app.dtos.VnpDTO;
import com.jun.service.domain.ModelMapper;
import com.jun.service.domain.data.TokenInfo;
import com.jun.service.domain.entities.Sequence;
import com.jun.service.domain.entities.account.Account;
import com.jun.service.domain.entities.types.AccountState;
import com.jun.service.domain.exceptions.AccountAlreadyExistsException;
import com.jun.service.domain.exceptions.AccountRegisteredNotActivatedException;
import com.jun.service.domain.exceptions.AccountRetypePasswordNotMatchException;
import com.jun.service.domain.exceptions.BadRequestException;
import com.jun.service.domain.factory.RequestFactory;
import com.jun.service.domain.producers.Producer;
import com.jun.service.domain.repositories.*;
import com.jun.service.domain.repositories.voucher.VoucherAccountRepository;
import com.jun.service.domain.storages.*;
import com.jun.service.domain.utils.*;
import lombok.extern.log4j.Log4j2;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Log4j2
public class BaseService {

  @Autowired protected ModelMapper modelMapper;

  @Autowired protected Caching caching;

  @Autowired protected JwtTokenUtil jwtTokenUtil;
  @Autowired protected JwtRefreshTokenUtil jwtRefreshTokenUtil;
  @Autowired protected Producer producer;
  @Autowired protected RequestFactory requestFactory;
  @Autowired protected MapperUtil mapperUtil;
  @Autowired protected LockManager lockManager;
  @Autowired protected PlatformTransactionManager transactionManager;
  @Autowired protected MongoExceptionUtils mongoExceptionUtils;

  @Autowired protected AccountStorage accountStorage;

  @Autowired protected VoucherStorage voucherStorage;

  @Autowired private MongoOperations mongoOperations;
  @Autowired protected ProductStorage productStorage;
  @Autowired protected ProductRepository productRepository;
  @Autowired protected ProductOptionRepository productOptionRepository;
  @Autowired protected ColorRepository colorRepository;
  @Autowired protected SizeRepository sizeRepository;
  @Autowired protected CategoryStorage categoryStorage;
  @Autowired protected CategoryRepository categoryRepository;
  @Autowired protected ConfigStorage configStorage;
  @Autowired protected VoucherAccountRepository voucherAccountRepository;
  @Autowired protected OrderRepository orderRepository;
  @Autowired protected CartRepository cartRepository;
  @Autowired protected CartStorage cartStorage;
  @Autowired protected ProductService productService;
  @Autowired protected ProductOptionStorage productOptionStorage;

  public long generateSequence(String seqName) {
    Sequence counter =
        mongoOperations.findAndModify(
            Query.query(Criteria.where("_id").is(seqName)),
            new Update().inc("seq", 1),
            FindAndModifyOptions.options().returnNew(true).upsert(true),
            Sequence.class);
    return !Objects.isNull(counter) ? counter.getSeq() : 1;
  }

  public void checkAccount(Account account) {
    if (account != null) {
      if (account.getState() == null) {
        throw new AccountRegisteredNotActivatedException("This account is not activated!");
      } else {
        throw new AccountAlreadyExistsException("This account is exist!");
      }
    }
  }

  protected void comparePassword(String password, String confirmedPassword) {
    if (!password.equals(confirmedPassword)) {
      throw new AccountRetypePasswordNotMatchException();
    }
  }

  protected Account savedAccount(RegisterDTO dto) {
    Account account = modelMapper.toAccount(dto);
    account.setId((int) generateSequence(Account.ACCOUNT_SEQUENCE));
    account.setState(AccountState.NOT_VERIFIED);
    account.setPassword(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt(12)));
    return accountStorage.save(account);
  }

  public Map<String, String> processLogin(Account account) {
    TokenInfo tokenInfo = new TokenInfo(account);

    Map<String, String> response = new HashMap<>();
    response.put("accessToken", jwtTokenUtil.generateToken(tokenInfo));
    response.put("refreshToken", jwtRefreshTokenUtil.generateToken(tokenInfo));

    return response;
  }

  protected void comparePassword(ChangePasswordDTO dto, String password) {
    boolean checkOldPassword = BCrypt.checkpw(dto.getOldPassword(), password);
    if (!checkOldPassword) {
      throw new BadRequestException("Wrong old password!");
    }
    comparePassword(dto.getNewPassword(), dto.getConfirmedPassword());
  }

  protected void sendMailOTPMessage(
      String email, String type, String code, String fullName, AccountState state) {
    MailMessage mailMessage = new MailMessage();
    mailMessage.setEmailTo(email);
    mailMessage.setMessageType("email");
    mailMessage.setSendType(type);
    Map<String, String> body = new HashMap<>();
    body.put("code", code);
    body.put("fullName", fullName);
    body.put("state", state.name());
    mailMessage.setBody(body);

    log.error("==================================" + mailMessage);
    producer.sendMailOTPMessage(mailMessage);
  }

  public String requestToVNP(VnpDTO dto) throws UnsupportedEncodingException {
    String vnp_IpAddr = Vnp_Ultil.vnp_ip;
    Map<String, String> vnp_Params = new HashMap<>();
    vnp_Params.put("vnp_Version", Vnp_Ultil.vnp_version);
    vnp_Params.put("vnp_Command", Vnp_Ultil.vnp_command);
    vnp_Params.put("vnp_TmnCode", Vnp_Ultil.vnp_TmnCode);
    vnp_Params.put("vnp_Amount", String.valueOf(dto.getPrice() * 100));
    vnp_Params.put("vnp_CurrCode", "VND");
    vnp_Params.put("vnp_BankCode", dto.getBankCode());
    vnp_Params.put("vnp_TxnRef", dto.getOderId().toString());
    vnp_Params.put("vnp_OrderInfo", "thanh toan don " + dto.getOderId());
    vnp_Params.put("vnp_OrderType", "250000");
    vnp_Params.put("vnp_Locale", "vn");

    vnp_Params.put("vnp_ReturnUrl", Vnp_Ultil.vnp_Returnurl);
    vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

    Date dt = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    String vnp_CreateDate = formatter.format(dt);
    vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

    Calendar cldvnp_ExpireDate = Calendar.getInstance();
    cldvnp_ExpireDate.add(Calendar.SECOND, 30);
    Date vnp_ExpireDateD = cldvnp_ExpireDate.getTime();
    String vnp_ExpireDate = formatter.format(vnp_ExpireDateD);

    vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

    List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
    Collections.sort(fieldNames);
    StringBuilder hashData = new StringBuilder();
    StringBuilder query = new StringBuilder();
    Iterator<String> itr = fieldNames.iterator();
    while (itr.hasNext()) {
      String fieldName = (String) itr.next();
      String fieldValue = (String) vnp_Params.get(fieldName);
      if ((fieldValue != null) && (fieldValue.length() > 0)) {
        // Build hash data
        hashData.append(fieldName);
        hashData.append('=');
        // hashData.append(fieldValue); //sử dụng và 2.0.0 và 2.0.1 checksum sha256
        hashData.append(
            URLEncoder.encode(
                fieldValue,
                StandardCharsets.US_ASCII.toString())); // sử dụng v2.1.0  check sum sha512
        // Build query
        query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
        query.append('=');
        query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
        if (itr.hasNext()) {
          query.append('&');
          hashData.append('&');
        }
      }
    }
    String queryUrl = query.toString();
    // String vnp_SecureHash = Config.Sha256(Config.vnp_HashSecret + hashData.toString());
    String vnp_SecureHash = Vnp_Ultil.hmacSHA512(Vnp_Ultil.vnp_HashSecret, hashData.toString());
    queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
    return Vnp_Ultil.vnp_PayUrl + "?" + queryUrl;
  }
}
