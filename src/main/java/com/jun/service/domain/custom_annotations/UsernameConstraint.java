package com.jun.service.domain.custom_annotations;

import com.jun.service.domain.validators.UsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameConstraint {
  String message() default "Sai định dạng tài khoản !";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
