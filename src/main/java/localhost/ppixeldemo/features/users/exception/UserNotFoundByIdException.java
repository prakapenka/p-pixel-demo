package localhost.ppixeldemo.features.users.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "User is not found by id")
public class UserNotFoundByIdException extends EntityNotFoundException {
  public UserNotFoundByIdException(EntityNotFoundException notFoundException) {
    super(notFoundException);
  }
}
