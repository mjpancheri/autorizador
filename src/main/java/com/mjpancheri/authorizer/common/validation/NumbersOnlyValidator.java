package com.mjpancheri.authorizer.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class NumbersOnlyValidator implements ConstraintValidator<NumbersOnly, String> {

  private static final Pattern NUMBERS_ONLY_PATTERN = Pattern.compile("^\\d*$");

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }
    return NUMBERS_ONLY_PATTERN.matcher(value).matches();
  }
}
