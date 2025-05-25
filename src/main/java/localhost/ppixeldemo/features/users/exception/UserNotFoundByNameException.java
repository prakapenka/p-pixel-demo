package localhost.ppixeldemo.features.users.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "User is not found by name")
public class UserNotFoundByNameException extends EntityNotFoundException {}
