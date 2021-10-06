package com.jun.service.account.domain.validators;

import com.jun.service.account.domain.custom_annotations.GenderConstraint;
import com.jun.service.account.domain.entities.types.Gender;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GenderValidator implements ConstraintValidator<GenderConstraint, Gender> {
  @Override
  public void initialize(GenderConstraint gender) {}

  @Override
  public boolean isValid(Gender genderField, ConstraintValidatorContext context) {
    if (genderField == null) {
      return true;
    }
    return genderField.equals(Gender.FEMALE) || genderField.equals(Gender.MALE);
  }
}
