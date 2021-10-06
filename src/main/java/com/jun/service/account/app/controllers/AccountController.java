package com.jun.service.account.app.controllers;

import com.jun.service.account.app.dtos.AccountUpdateDTO;
import com.jun.service.account.app.dtos.ChangePasswordDTO;
import com.jun.service.account.app.responses.AccountResponse;
import com.jun.service.account.domain.services.AccountService;
import com.jun.service.account.domain.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/accounts")
public class AccountController extends BaseController {

  @Autowired private AccountService accountService;
  @Autowired private AuthenticationService authenticationService;

  @GetMapping(path = "profile")
  public ResponseEntity<AccountResponse> getProfile(
      @RequestHeader(name = "x-loyalty-token") String token) {
    return ResponseEntity.ok(accountService.getProfile(validateToken(token).getAccountId()));
  }

  @PutMapping()
  public ResponseEntity<AccountResponse> update(
      @RequestHeader(name = "x-loyalty-token") String token,
      @RequestBody @Valid AccountUpdateDTO dto) {
    return ResponseEntity.ok(accountService.update(validateToken(token).getAccountId(), dto));
  }

  @PutMapping(path = "change_password", params = "collection=update_password")
  public ResponseEntity<Boolean> changePassword(
      @RequestHeader(name = "x-loyalty-token") String token,
      @RequestBody @Valid ChangePasswordDTO dto) {
    return ResponseEntity.ok(
        authenticationService.changePassword(dto, validateToken(token).getAccountId()));
  }

  //  @PostMapping(path = "enter_referral_code")
  //  public ResponseEntity<EnterReferralResponse> enterReferralCode(
  //      @RequestHeader(name = "x-loyalty-account-id") String accountId,
  //      @RequestBody @Valid EnterReferralCodeDTO dto) {
  //    return ResponseEntity.ok(accountService.enterReferralCode(dto, accountId));
  //  }
}
