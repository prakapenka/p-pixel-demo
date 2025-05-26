package localhost.ppixeldemo.config.cache;

import java.util.List;

public record UserBalancesChangedEvent(List<Long> userIds) {
  public UserBalancesChangedEvent {
    if (userIds == null || userIds.isEmpty()) {
      throw new IllegalArgumentException("userIds must not be null or empty");
    }
  }
}
