package com.jun.service.domain.custom_annotations;

import com.jun.service.domain.validators.EmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailConstraint {
  String message() default "Sai định dạng email !";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
