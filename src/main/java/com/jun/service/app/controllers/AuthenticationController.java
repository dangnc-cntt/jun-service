package com.jun.service.app.controllers;

import com.jun.service.app.dtos.*;
import com.jun.service.domain.entities.types.AccountActionType;
import com.jun.service.domain.services.AccountService;
import com.jun.service.domain.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("v1/auth")
public class AuthenticationController {

  @Autowired private AuthenticationService authenticationService;
  @Autowired private AccountService accountService;

  @PostMapping(path = "register")
  public ResponseEntity<Boolean> register(@RequestBody @Valid RegisterDTO registerDTO) {
    authenticationService.register(registerDTO);
    return ResponseEntity.ok(true);
  }

  @PutMapping(path = "verify")
  public ResponseEntity<Boolean> verify(@RequestBody @Valid VerifyDTO verifyDTO)
      throws IOException {
    return ResponseEntity.ok(authenticationService.verify(verifyDTO));
  }

  @PostMapping(path = "login")
  public ResponseEntity<Map<String, String>> login(@RequestBody @Valid LoginDTO dto) {
    return ResponseEntity.ok(authenticationService.login(dto));
  }

  @PostMapping(path = "forgot_password", params = "collection=send_otp")
  public ResponseEntity<Boolean> forgotPassword(@RequestBody @Valid ForgotPasswordDTO dto)
      throws IOException {
    authenticationService.forgotPassword(dto);
    return ResponseEntity.ok(true);
  }

  @PostMapping(path = "forgot_password", params = "collection=check_otp")
  public ResponseEntity<Boolean> checkOtpForgotPassword(@RequestBody @Valid CheckOtpDTO dto)
      throws IOException {
    return ResponseEntity.ok(authenticationService.checkOtpForgotPassword(dto));
  }

  @PutMapping(path = "forgot_password", params = "collection=update_password")
  public ResponseEntity<Boolean> updatedNewPassword(@RequestBody @Valid UpdateNewPasswordDTO dto)
      throws IOException {
    return ResponseEntity.ok(authenticationService.updatedNewPassword(dto));
  }

  @DeleteMapping(path = "logout")
  public ResponseEntity<Boolean> logout(@RequestBody @Valid LogoutDTO dto) {
    return ResponseEntity.ok(authenticationService.logout(dto));
  }

  @PostMapping(path = "resend_code")
  public ResponseEntity<Boolean> resendRegisterCode(
      @RequestBody @Valid SendCodeDTO dto,
      @RequestParam(name = "actionType") AccountActionType type) {
    return ResponseEntity.ok(authenticationService.resendCode(dto, type));
  }

  @PutMapping(path = "change_password", params = "collection=send_otp")
  public ResponseEntity<Boolean> sendOTPChangePassword(@RequestBody @Valid SendCodeDTO dto) {
    authenticationService.sendOTPChangePassword(dto);
    return ResponseEntity.ok(true);
  }

  @PostMapping("refresh_token")
  public ResponseEntity<Map<String, String>> refreshToken(@RequestBody @Valid RefreshTokenDTO dto) {
    return ResponseEntity.ok(authenticationService.refreshToken(dto));
  }

  //  @GetMapping("mail")
  //  public ResponseEntity<Boolean> testmail() throws MessagingException {
  //    return ResponseEntity.ok(authenticationService.sendMail());
  //  }
}
