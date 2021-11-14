package com.jun.service.domain.validators;

import com.jun.service.domain.custom_annotations.UrlConstraint;
import com.jun.service.domain.utils.Helper;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UrlValidator implements ConstraintValidator<UrlConstraint, String> {
  @Override
  public void initialize(UrlConstraint url) {}

  @Override
  public boolean isValid(String urlField, ConstraintValidatorContext context) {
    if (urlField == null || urlField.equals("")) {
      return true;
    }
    return Helper.regexUrl(urlField);
  }
}
