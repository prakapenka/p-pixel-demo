package localhost.ppixeldemo.common.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@NotNull
@Email(message = "Email should be valid")
@Size(max = 200, message = "Email must be at most 200 characters")
@Target({METHOD, FIELD, ANNOTATION_TYPE, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {})
public @interface PPixelEmail {
  String message() default "Email should be valid and contains at most 200 symbols";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
