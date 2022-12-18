package com.mjpancheri.authorizer.common.validation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = NumbersOnlyValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface NumbersOnly {

  String message() default "Somente números";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
