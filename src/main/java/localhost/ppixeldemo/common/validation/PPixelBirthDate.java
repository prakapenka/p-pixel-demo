package localhost.ppixeldemo.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;
import localhost.ppixeldemo.common.validation.impl.MinLocalDateValidator;

/**
 * We have to limit birthdate to effectively uses searches. Technically Postgres have min value for
 * DATE equals to 4713 BC, but let stop at 1 year AC.
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MinLocalDateValidator.class)
@Documented
public @interface PPixelBirthDate {

  String message() default "Minimal birth date is 1.1.0001";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
