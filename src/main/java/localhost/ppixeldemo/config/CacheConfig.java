package localhost.ppixeldemo.config;

import static localhost.ppixeldemo.features.users.service.impl.CachingUserBalanceService.USER_BALANCE_CACHE;
import static localhost.ppixeldemo.features.users.service.impl.CachingUserEmailsService.USER_EMAIL_CACHE;
import static localhost.ppixeldemo.features.users.service.impl.CachingUserPhoneService.USER_PHONE_CACHE;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

  @Bean
  public CacheManager cacheManager() {
    SimpleCacheManager manager = new SimpleCacheManager();

    CaffeineCache userPhoneCache =
        new CaffeineCache(
            USER_PHONE_CACHE,
            Caffeine.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).maximumSize(1000).build());

    CaffeineCache userEmailCache =
        new CaffeineCache(
            USER_EMAIL_CACHE,
            Caffeine.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).maximumSize(1000).build());

    CaffeineCache userBalanceCache =
        new CaffeineCache(
            USER_BALANCE_CACHE,
            Caffeine.newBuilder().expireAfterWrite(30, TimeUnit.SECONDS).maximumSize(5000).build());

    manager.setCaches(List.of(userPhoneCache, userEmailCache, userBalanceCache));
    return manager;
  }
}
