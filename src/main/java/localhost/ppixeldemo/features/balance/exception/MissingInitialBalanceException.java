package localhost.ppixeldemo.features.balance.exception;

public class MissingInitialBalanceException extends IllegalArgumentException {
  public MissingInitialBalanceException(Long userId) {
    super("Missing initial balance for user " + userId);
  }
}
