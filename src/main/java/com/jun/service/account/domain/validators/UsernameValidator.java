package com.jun.service.account.domain.validators;

import com.jun.service.account.domain.custom_annotations.UsernameConstraint;
import com.jun.service.account.domain.utils.Helper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class UsernameValidator implements ConstraintValidator<UsernameConstraint, String> {

  @Override
  public void initialize(UsernameConstraint username) {}

  @Override
  public boolean isValid(String usernameField, ConstraintValidatorContext context) {
    if (usernameField == null || usernameField.equals("") || usernameField.length() < 6) {
      return true;
    }

    String afterLast = StringUtils.substringAfterLast(usernameField, "@");
    if (!Helper.regexPhoneNumber(usernameField)) {

      return Helper.regexEmail(usernameField);

    } else {
      return Helper.regexPhoneNumber(usernameField);
    }
  }
}
