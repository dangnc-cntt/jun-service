package com.jun.service.account.domain.validators;

import com.jun.service.account.domain.custom_annotations.DateConstraint;
import com.jun.service.account.domain.utils.Helper;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

public class DateValidator implements ConstraintValidator<DateConstraint, Date> {

  @Override
  public void initialize(DateConstraint date) {}

  @Override
  public boolean isValid(Date dateField, ConstraintValidatorContext context) {
    if (dateField == null) {
      return true;
    }
    return Helper.compareDate(dateField);
  }
}
