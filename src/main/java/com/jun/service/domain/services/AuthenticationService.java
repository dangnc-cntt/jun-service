package com.jun.service.domain.services;

import com.iprediction.email.MailMessage;
import com.jun.service.app.dtos.*;
import com.jun.service.domain.data.ForgotPasswordInfo;
import com.jun.service.domain.data.MailBodyObject;
import com.jun.service.domain.data.TokenInfo;
import com.jun.service.domain.data.VerifyInfo;
import com.jun.service.domain.entities.account.Account;
import com.jun.service.domain.entities.types.AccountActionType;
import com.jun.service.domain.entities.types.AccountState;
import com.jun.service.domain.exceptions.*;
import com.jun.service.domain.templates.Template;
import com.jun.service.domain.utils.CacheKey;
import com.jun.service.domain.utils.Helper;
import com.jun.service.domain.utils.JsonParser;
import com.jun.service.domain.utils.Message;
import lombok.extern.log4j.Log4j2;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Service
@Log4j2
public class AuthenticationService extends BaseService {

  @Transactional(isolation = Isolation.SERIALIZABLE)
  public void register(RegisterDTO registerDTO) {
    if (registerDTO.getUsername() != null) {
      Account account = accountStorage.findAccountByUserNameNotCache(registerDTO.getUsername());
      checkAccount(account);
      comparePassword(registerDTO.getPassword(), registerDTO.getConfirmPassword());
      account = savedAccount(registerDTO);

      if (Helper.regexEmail(registerDTO.getEmail())) {
        sendOTPEmail(account, AccountActionType.REG);
      }
    }
  }

  public void sendOTPChangePassword(SendCodeDTO dto) {

    Account account = accountStorage.findAccountByUserName(dto.getUsername());

    if (account == null) {
      throw new AccountNotExistsException();
    }
    sendOTPEmail(account, AccountActionType.CHANGE_PASSWORD);
  }

  public String sendOTPEmail(Account account, AccountActionType accountActionType) {

    String code = Helper.generateCode();
    log.info(code);
    String redisKey = null;
    switch (accountActionType) {
      case REG:
        redisKey = CacheKey.genRegEmailKey(account.getUsername());
        break;

      case FORGOT:
        redisKey = CacheKey.genForgotPasswordKey(account.getUsername());
        break;

      case CHANGE_PASSWORD:
        redisKey = CacheKey.genChangePasswordKey(account.getUsername());
        break;

      default:
        redisKey = null;
    }

    // send to kafka
    sendMailOTPMessage(
        account.getEmail(),
        accountActionType.toString(),
        code,
        account.getFullName() == null ? "None" : account.getFullName(),
        account.getState());

    VerifyInfo verifyInfo = new VerifyInfo();
    verifyInfo.setCode(code);
    verifyInfo.setAccountId(account.getId());
    caching.put(redisKey, JsonParser.toJson(verifyInfo), 600);

    return code;
  }

  @Transactional(isolation = Isolation.SERIALIZABLE)
  public boolean verify(VerifyDTO dto) throws IOException {
    String redisKey = CacheKey.genRegEmailKey(dto.getUsername());

    if (!caching.exists(redisKey)) {
      Account account = accountStorage.findAccountByUserName(dto.getUsername());
      if (account == null) {
        throw new AccountNotExistsException();
      } else {
        throw new AuthenticationCodeNotExistsException();
      }
    }
    processVerify(redisKey, dto.getCode());
    return true;
  }

  protected void processVerify(String redisKey, String code) throws IOException {
    VerifyInfo verifyInfo = JsonParser.entity(caching.get(redisKey), VerifyInfo.class);
    log.info(verifyInfo.toString());

    if (!code.equals(verifyInfo.getCode())) {
      throw new AuthenticationCodeNotExistsException();
    }
    Account account = updatedConfirmed(verifyInfo.getAccountId());
    if (account.getConfirmedAt() != null) {
      caching.del(redisKey);
    }
  }

  protected Account updatedConfirmed(int accountId) {
    Account account = accountStorage.findAccountById(accountId);
    if (account == null) {
      throw new AccountNotExistsException();
    }
    if (account.getConfirmedAt() != null) {
      throw new AccountHasBeenActivatedException();
    }

    account.setConfirmedAt(LocalDateTime.now());
    account.setState(AccountState.ACTIVATED);
    return accountStorage.save(account);
  }

  public Map<String, String> login(LoginDTO loginDTO) {
    if (loginDTO.getUsername() != null) {
      Account account = accountStorage.findAccountByUserName(loginDTO.getUsername());
      if (account == null || account.getPassword() == null) {
        throw new WrongAccountOrPasswordException();
      }

      boolean checkPassword = BCrypt.checkpw(loginDTO.getPassword(), account.getPassword());

      if (checkPassword) {
        if (!account.getState().equals(AccountState.ACTIVATED)) {
          throw new BadRequestException(Message.ACCOUNT_NOT_ACTIVATED);
        }
        Map<String, String> accessToken = processLogin(account);
        return accessToken;
      } else {
        throw new WrongAccountOrPasswordException();
      }
    } else {
      throw new BadRequestException("UserName is required!");
    }
  }

  public void forgotPassword(ForgotPasswordDTO forgotPasswordDTO) {
    Account account = accountStorage.findAccountByUserName(forgotPasswordDTO.getUsername());
    if (account == null) {
      throw new AccountNotExistsException();
    }
    if (Helper.regexEmail(account.getEmail())) {
      sendOTPEmail(account, AccountActionType.FORGOT);
    }
  }

  public boolean checkOtpForgotPassword(CheckOtpDTO dto) throws IOException {
    Account account = accountStorage.findAccountByUserName(dto.getUsername());
    if (account == null) {
      throw new AccountNotExistsException();
    }
    String redisKey = CacheKey.genForgotPasswordKey(dto.getUsername());
    if (caching.exists(redisKey)) {
      ForgotPasswordInfo forgotPasswordInfo =
          JsonParser.entity(caching.get(redisKey), ForgotPasswordInfo.class);
      if (!dto.getCode().equals(forgotPasswordInfo.getCode())) {
        throw new AuthenticationCodeNotExistsException();
      }
    } else {
      throw new AuthenticationCodeNotExistsException();
    }
    return true;
  }

  @Transactional(isolation = Isolation.SERIALIZABLE)
  public boolean updatedNewPassword(UpdateNewPasswordDTO dto) throws IOException {
    Account account = accountStorage.findAccountByUserName(dto.getUsername());
    if (account == null) {
      throw new AccountNotExistsException();
    }
    CheckOtpDTO checkOtpDTO = new CheckOtpDTO();
    checkOtpDTO.setUsername(account.getUsername());
    checkOtpDTO.setCode(dto.getCode());
    checkOtpForgotPassword(checkOtpDTO);
    comparePassword(dto.getPassword(), dto.getConfirmedPassword());
    String hashPwd = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt(12));
    account.setPassword(hashPwd);
    accountStorage.save(account);
    caching.del(CacheKey.genForgotPasswordKey(dto.getUsername()));
    return true;
  }

  @Transactional(isolation = Isolation.SERIALIZABLE)
  public boolean changePassword(ChangePasswordDTO changePasswordDTO, int accountId) {

    Account account = accountStorage.findAccountById(accountId);

    if (account == null) {
      throw new AccountNotExistsException();
    }
    //    checkOtpChangePassword(changePasswordDTO.username(), changePasswordDTO.getCode());
    //
    comparePassword(changePasswordDTO, account.getPassword());

    String password = BCrypt.hashpw(changePasswordDTO.getNewPassword(), BCrypt.gensalt(12));
    account.setPassword(password);
    accountStorage.save(account);

    //    caching.del(CacheKey.genChangePasswordKey(changePasswordDTO.getUsername()));

    return true;
  }

  //  public void checkOtpChangePassword(String userName, String code) {
  //    Account account = accountStorage.findAccountByUserName(userName);
  //    if (account == null) {
  //      throw new AccountNotExistsException();
  //    }
  //    String redisKey = CacheKey.genChangePasswordKey(userName);
  //    if (!caching.exists(redisKey)) {
  //      throw new AuthenticationCodeNotExistsException();
  //    }
  //  }

  public boolean logout(LogoutDTO logoutDTO) {
    jwtRefreshTokenUtil.delete(logoutDTO.getRefreshToken());
    return true;
  }

  public boolean resendCode(SendCodeDTO dto, AccountActionType type) {
    Account account = accountStorage.findAccountByUserName(dto.getUsername());
    if (account == null) {
      throw new AccountNotExistsException();
    }
    if (Helper.regexEmail(dto.getUsername())) {
      sendOTPEmail(account, type);
    }
    return true;
  }

  public Map<String, String> refreshToken(RefreshTokenDTO refreshTokenDTO) {
    TokenInfo tokenInfo = jwtRefreshTokenUtil.validate(refreshTokenDTO.getRefreshToken());
    if (tokenInfo == null) {
      throw new UnauthorizedException();
    }

    Map<String, String> response = new HashMap<>();
    response.put("refreshToken", jwtTokenUtil.generateToken(tokenInfo));

    return response;
  }

  public Boolean sendMail(List<MailMessage> mailMessages) throws MessagingException {
    final String fromMail = "dangnc.cntt@gmail.com";
    final String password = "0nomon@4namratruong";
    //    final String toMail = "daidangtalavua@gmail.com";

    final String subject = "Jun Shop OTP";

    Properties properties = new Properties();
    properties.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
    properties.put("mail.smtp.port", "587"); // TLS Port
    properties.put("mail.smtp.auth", "true"); // enable authentication
    properties.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS

    Session session =
        Session.getDefaultInstance(
            properties,
            new Authenticator() {
              @Override
              protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromMail, password);
              }
            });

    for (MailMessage message : mailMessages) {

      MimeMessage mimeMessage = new MimeMessage(session);

      mimeMessage.setFrom(new InternetAddress(fromMail));
      mimeMessage.setRecipients(
          javax.mail.Message.RecipientType.TO, InternetAddress.parse(message.getEmailTo(), false));
      mimeMessage.setSubject(subject);
      Map<String, String> body = new HashMap<>();
      body.put("code", message.getBody().get("code"));
      body.put("fullName", message.getBody().get("fullName"));
      MailBodyObject bodyObject = new MailBodyObject(body);
      mimeMessage.setContent(htmlContent(bodyObject), "text/html");

      Transport.send(mimeMessage);
    }
    return true;
  }

  private String htmlContent(MailBodyObject obj) {
    return Template.UserTemplate.replace("#CODE#", obj.getCode())
        .replace("#FULL_NAME#", obj.getFullName());
  }
}
