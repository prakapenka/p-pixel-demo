package localhost.ppixeldemo.features.users.service.impl;

import jakarta.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import localhost.ppixeldemo.config.cache.UserPhoneChangedEvent;
import localhost.ppixeldemo.features.users.entity.UserPhoneProjection;
import localhost.ppixeldemo.features.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Service
public class CachingUserPhoneService {

  public static final String USER_PHONE_CACHE = "userPhoneCache";

  private final UserRepository userRepository;
  private final CacheManager cacheManager;

  @Transactional(Transactional.TxType.MANDATORY)
  public List<UserPhoneProjection> findPhonesByUserIds(List<Long> userIds) {
    Map<Long, UserPhoneProjection> result = new HashMap<>();
    List<Long> missingIds = new ArrayList<>();

    Cache cache = getCache();

    for (Long userId : userIds) {
      Cache.ValueWrapper wrapper = cache.get(userId);
      if (wrapper != null) {
        result.put(userId, (UserPhoneProjection) wrapper.get());
      } else {
        missingIds.add(userId);
      }
    }

    if (!missingIds.isEmpty()) {
      List<UserPhoneProjection> fetched = userRepository.findPhonesByUserIds(missingIds);
      for (UserPhoneProjection p : fetched) {
        cache.put(p.getUserId(), p);
        result.put(p.getUserId(), p);
      }
    }

    return userIds.stream().map(result::get).collect(Collectors.toList());
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void onUserPhoneChanged(UserPhoneChangedEvent event) {
    Cache cache = getCache();
    cache.evict(event.userId());
  }

  private Cache getCache() {
    return Optional.ofNullable(cacheManager.getCache(USER_PHONE_CACHE))
        .orElseThrow(
            () -> new IllegalStateException("Cache " + USER_PHONE_CACHE + " not configured"));
  }
}
