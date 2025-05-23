package localhost.ppixeldemo.common.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import localhost.ppixeldemo.common.validation.PPixelBirthDate;

public class MinLocalDateValidator implements ConstraintValidator<PPixelBirthDate, LocalDate> {

  public static final LocalDate MIN = LocalDate.of(1, 1, 1);

  @Override
  public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
    return value == null || !value.isBefore(MIN);
  }
}
