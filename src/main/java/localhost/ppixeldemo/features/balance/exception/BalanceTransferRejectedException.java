package localhost.ppixeldemo.features.balance.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Balance transfer forbidden")
public class BalanceTransferRejectedException extends IllegalArgumentException {}
