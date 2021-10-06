package com.jun.service.account.domain.custom_annotations;

import com.jun.service.account.domain.validators.GenderValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = GenderValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GenderConstraint {
  String message() default "Sai định dạng giới tính !";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
