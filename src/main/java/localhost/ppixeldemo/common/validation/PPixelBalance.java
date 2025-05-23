package localhost.ppixeldemo.common.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@NotNull
@DecimalMin("0.00")
@DecimalMax("9999999999999.99")
@Digits(integer = 15, fraction = 2)
@Target({METHOD, FIELD, ANNOTATION_TYPE, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {})
public @interface PPixelBalance {
  String message() default "Balance should be positive and at most 9999999999999.99";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
