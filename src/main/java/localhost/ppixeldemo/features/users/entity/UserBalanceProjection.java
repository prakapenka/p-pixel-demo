package localhost.ppixeldemo.features.users.entity;

import java.math.BigDecimal;

public interface UserBalanceProjection {
  Long getUserId();

  BigDecimal getBalance();
}
