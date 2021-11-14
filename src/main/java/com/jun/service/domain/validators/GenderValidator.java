package com.jun.service.domain.validators;

import com.jun.service.domain.custom_annotations.GenderConstraint;
import com.jun.service.domain.entities.types.Gender;

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
