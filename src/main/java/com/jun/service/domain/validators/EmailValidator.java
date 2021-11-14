package com.jun.service.domain.validators;

import com.jun.service.domain.custom_annotations.EmailConstraint;
import com.jun.service.domain.utils.Helper;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<EmailConstraint, String> {

  @Override
  public void initialize(EmailConstraint email) {}

  @Override
  public boolean isValid(String emailField, ConstraintValidatorContext context) {
    if (emailField == null || emailField.equals("")) {
      return true;
    }

    return Helper.regexEmail(emailField);
  }
}
