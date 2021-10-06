package com.jun.service.account.domain.validators;

import com.jun.service.account.domain.custom_annotations.PhoneNumberConstraint;
import com.jun.service.account.domain.utils.Helper;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberConstraint, String> {

  @Override
  public void initialize(PhoneNumberConstraint phoneNumber) {}

  @Override
  public boolean isValid(String phoneNumberField, ConstraintValidatorContext context) {
    if (phoneNumberField == null || phoneNumberField.equals("") || phoneNumberField.length() < 10) {
      return true;
    }
    return Helper.regexPhoneNumber(phoneNumberField);
  }
}
