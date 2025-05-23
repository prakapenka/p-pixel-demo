package localhost.ppixeldemo.common.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/** see db schema for actual constraint values */
@NotNull
@Pattern(regexp = "^\\d{3,13}$", message = "Phone number must be 3 up to 13 digits")
@Target({METHOD, FIELD, ANNOTATION_TYPE, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {})
public @interface PPixelPhone {
  String message() default "Phone number must be exactly 13 digits";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
