package localhost.ppixeldemo.features.users.service.impl;

import static java.util.Optional.ofNullable;

import jakarta.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import localhost.ppixeldemo.config.cache.UserBalancesChangedEvent;
import localhost.ppixeldemo.features.users.entity.UserBalanceProjection;
import localhost.ppixeldemo.features.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Service
public class CachingUserBalanceService {

  public static final String USER_BALANCE_CACHE = "userBalanceCache";

  private final UserRepository userRepository;
  private final CacheManager cacheManager;

  @Transactional(Transactional.TxType.MANDATORY)
  public List<UserBalanceProjection> findBalancesByUserIds(List<Long> userIds) {
    Map<Long, UserBalanceProjection> result = new HashMap<>();
    List<Long> missingIds = new ArrayList<>();

    Cache cache = getCache();

    for (Long userId : userIds) {
      Cache.ValueWrapper wrapper = cache.get(userId);
      if (wrapper != null) {
        result.put(userId, (UserBalanceProjection) wrapper.get());
      } else {
        missingIds.add(userId);
      }
    }

    if (!missingIds.isEmpty()) {
      List<UserBalanceProjection> fetched = userRepository.findBalancesByUserIds(missingIds);
      for (UserBalanceProjection p : fetched) {
        cache.put(p.getUserId(), p);
        result.put(p.getUserId(), p);
      }
    }

    return userIds.stream().map(result::get).collect(Collectors.toList());
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void onUserPhoneChanged(UserBalancesChangedEvent event) {
    Cache cache = getCache();
    ofNullable(event.userIds()).ifPresent(userIds -> userIds.forEach(cache::evict));
  }

  private Cache getCache() {
    return ofNullable(cacheManager.getCache(USER_BALANCE_CACHE))
        .orElseThrow(
            () -> new IllegalStateException("Cache " + USER_BALANCE_CACHE + " not configured"));
  }
}
