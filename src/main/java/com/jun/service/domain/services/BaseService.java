package com.jun.service.domain.services;

import com.iprediction.email.MailMessage;
import com.jun.service.app.dtos.ChangePasswordDTO;
import com.jun.service.app.dtos.RegisterDTO;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

  // todo: make it in kafka
  //  protected Boolean processPoint(
  //      Account account,
  //      String transactionRefId,
  //      Long amount,
  //      TransactionType type,
  //      OperationType operationType,
  //      String name) {
  //
  //    if (operationType == OperationType.ADD) {
  //      account.getProfile().setPoint((account.getProfile().getPoint() + amount));
  //    } else {
  //      if (account.getProfile().getPoint() < amount) {
  //        account.getProfile().setPoint(0L);
  //      } else {
  //        account.getProfile().setPoint((account.getProfile().getPoint() - amount));
  //      }
  //    }
  //
  //    //    // tao transaction
  //    Transaction transaction = new Transaction();
  //    transaction.setAmount(amount);
  //    transaction.setType(type);
  //    transaction.setOperationType(operationType);
  //    transaction.setAccountId(account.getId());
  //    transaction.setName(name);
  //    transaction.setBalancePoint(account.getProfile().getPoint());
  //    transaction.setBalanceCoin(account.getProfile().getCoin());
  //    transaction.setUnit(TransactionUnit.POINT);
  //    transaction.setTransactionRefId(transactionRefId);
  //
  //    // todo: socket
  //    accountStorage.save(account);
  //    transactionStorage.save(transaction);
  //    return true;
  //  }
}
