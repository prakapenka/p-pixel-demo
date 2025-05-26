package localhost.ppixeldemo.features.balance.service;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.TimeUnit;
import localhost.ppixeldemo.config.cache.UserBalancesChangedEvent;
import localhost.ppixeldemo.features.balance.entity.AccountEntity;
import localhost.ppixeldemo.features.balance.entity.InitialBalanceEntity;
import localhost.ppixeldemo.features.balance.exception.MissingInitialBalanceException;
import localhost.ppixeldemo.features.balance.repository.AccountRepository;
import localhost.ppixeldemo.features.balance.repository.InitialBalanceRepository;
import localhost.ppixeldemo.features.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class IncreaseBalanceService {

  private final UserRepository userRepository;
  private final AccountRepository accountRepository;
  private final InitialBalanceRepository initialBalanceRepository;
  private final ApplicationEventPublisher publisher;

  private static final BigDecimal GROWTH_RATE = BigDecimal.valueOf(1.10); // 10%
  private static final BigDecimal MAX_MULTIPLIER = BigDecimal.valueOf(2.07); // 207%
  private static final int PAGE_SIZE = 30;

  @Transactional
  @Scheduled(timeUnit = TimeUnit.SECONDS, fixedRate = 30, initialDelay = 5)
  public void increaseBalances() {
    int page = 0;

    BigDecimal totalGiveaway = BigDecimal.ZERO;
    List<Long> updatedUserIds = new ArrayList<>();

    // iterative by pages to avoid loading all accounts into memory
    Page<Long> userIdsPage;
    do {
      userIdsPage = userRepository.findAllUserIds(PageRequest.of(page, PAGE_SIZE));
      List<Long> userIds = userIdsPage.getContent();

      List<AccountEntity> accounts = accountRepository.findByUserIdsForUpdate(userIds);

      List<InitialBalanceEntity> initialBalances = initialBalanceRepository.findAllById(userIds);

      Map<Long, InitialBalanceEntity> initialMap =
          initialBalances.stream().collect(toMap(InitialBalanceEntity::getUserId, identity()));

      // Insert missing initial balances
      List<InitialBalanceEntity> toInsert =
          accounts.stream()
              .filter(account -> !initialMap.containsKey(account.getUser().getId()))
              .map(
                  account -> {
                    BigDecimal initialBalance = account.getBalance();
                    BigDecimal maxBalance =
                        initialBalance.multiply(MAX_MULTIPLIER).setScale(2, RoundingMode.HALF_EVEN);
                    return new InitialBalanceEntity(
                        account.getUser().getId(), initialBalance, maxBalance);
                  })
              .toList();

      if (!toInsert.isEmpty()) {
        log.info("New initial balances will be added: {}", toInsert.size());
        initialBalanceRepository.saveAll(toInsert);
        // Add them to the map to avoid additional lookups
        toInsert.forEach(i -> initialMap.put(i.getUserId(), i));
      }

      List<AccountEntity> updatedAccounts = new ArrayList<>();

      for (AccountEntity account : accounts) {
        Long userId = account.getUser().getId();
        BigDecimal currentBalance = account.getBalance();

        InitialBalanceEntity initial = initialMap.get(userId);
        if (initial == null) {
          throw new MissingInitialBalanceException(userId);
        }

        BigDecimal maxBalance = initial.getMaxBalance();
        if (currentBalance.compareTo(maxBalance) >= 0) {
          continue; // Skip, already maxed
        }

        BigDecimal newBalance =
            currentBalance.multiply(GROWTH_RATE).setScale(2, RoundingMode.HALF_EVEN);

        if (newBalance.compareTo(maxBalance) > 0) {
          newBalance = maxBalance;
        }

        BigDecimal increase = newBalance.subtract(currentBalance);
        if (increase.compareTo(BigDecimal.ZERO) > 0) {
          account.setBalance(newBalance);
          totalGiveaway = totalGiveaway.add(increase);
          updatedUserIds.add(userId);
          updatedAccounts.add(account);
        }
      }

      if (!updatedAccounts.isEmpty()) {
        accountRepository.saveAll(updatedAccounts);
      }

      page++;
    } while (userIdsPage.hasNext());

    // publish events
    publisher.publishEvent(new UserBalancesChangedEvent(updatedUserIds));

    log.info("Total giveaway: [{}], accounts increased: {}", totalGiveaway, updatedUserIds.size());
  }
}
