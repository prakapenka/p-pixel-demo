package localhost.ppixeldemo.features.phone.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Phone not found")
public class PhoneNotFoundException extends RuntimeException {}
