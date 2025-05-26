package localhost.ppixeldemo.features.users.service.impl;

import jakarta.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import localhost.ppixeldemo.config.cache.UserEmailChangedEvent;
import localhost.ppixeldemo.features.users.entity.UserEmailProjection;
import localhost.ppixeldemo.features.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Service
public class CachingUserEmailsService {

  public static final String USER_EMAIL_CACHE = "userEmailCache";

  private final UserRepository userRepository;
  private final CacheManager cacheManager;

  @Transactional(Transactional.TxType.MANDATORY)
  public List<UserEmailProjection> findEmailsByUserIds(List<Long> userIds) {
    Map<Long, UserEmailProjection> result = new HashMap<>();
    List<Long> missingIds = new ArrayList<>();

    Cache cache = getCache();

    for (Long userId : userIds) {
      Cache.ValueWrapper wrapper = cache.get(userId);
      if (wrapper != null) {
        result.put(userId, (UserEmailProjection) wrapper.get());
      } else {
        missingIds.add(userId);
      }
    }

    if (!missingIds.isEmpty()) {
      List<UserEmailProjection> fetched = userRepository.findEmailsByUserIds(missingIds);
      for (UserEmailProjection p : fetched) {
        cache.put(p.getUserId(), p);
        result.put(p.getUserId(), p);
      }
    }

    return userIds.stream().map(result::get).collect(Collectors.toList());
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void onUserEmailChanged(UserEmailChangedEvent event) {
    Cache cache = getCache();
    cache.evict(event.userId());
  }

  private Cache getCache() {
    return Optional.ofNullable(cacheManager.getCache(USER_EMAIL_CACHE))
        .orElseThrow(
            () -> new IllegalStateException("Cache " + USER_EMAIL_CACHE + " not configured"));
  }
}
