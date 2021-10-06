package com.jun.service.account.domain.custom_annotations;

import com.jun.service.account.domain.validators.DateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateConstraint {
  String message() default "Ngày sinh lớn hơn ngày hiện tại !";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
