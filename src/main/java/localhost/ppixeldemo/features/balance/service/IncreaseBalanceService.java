package localhost.ppixeldemo.features.balance.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import localhost.ppixeldemo.features.balance.entity.AccountEntity;
import localhost.ppixeldemo.features.balance.entity.InitialBalanceEntity;
import localhost.ppixeldemo.features.balance.exception.MissingInitialBalanceException;
import localhost.ppixeldemo.features.balance.repository.AccountRepository;
import localhost.ppixeldemo.features.balance.repository.InitialBalanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class IncreaseBalanceService {

  private final AccountRepository accountRepository;
  private final InitialBalanceRepository initialBalanceRepository;

  private static final BigDecimal GROWTH_RATE = new BigDecimal("1.10"); // 10%
  private static final BigDecimal MAX_MULTIPLIER = new BigDecimal("2.07"); // 207%

  @Transactional
  @Scheduled(timeUnit = TimeUnit.SECONDS, fixedRate = 30, initialDelay = 5)
  public void increaseBalances() {

    List<AccountEntity> accounts = accountRepository.findAll();

    // Ensure initial balances are recorded
    for (AccountEntity account : accounts) {
      Long userId = account.getUser().getId();
      initialBalanceRepository
          .findById(userId)
          .orElseGet(
              () -> {
                final var initialBalance = account.getBalance();
                final var maxBalance =
                    initialBalance.multiply(MAX_MULTIPLIER).setScale(2, RoundingMode.HALF_EVEN);
                final var newInitial = new InitialBalanceEntity(userId, initialBalance, maxBalance);
                return initialBalanceRepository.save(newInitial);
              });
    }

    BigDecimal totalGiveAway = BigDecimal.ZERO;
    List<Long> luckyAccounts = new ArrayList<>();

    for (AccountEntity account : accounts) {
      Long userId = account.getUser().getId();
      BigDecimal currentBalance = account.getBalance();

      InitialBalanceEntity initial =
          initialBalanceRepository
              .findById(userId)
              .orElseThrow(() -> new MissingInitialBalanceException(userId));

      final BigDecimal maxBalance = initial.getMaxBalance();
      if (currentBalance.compareTo(maxBalance) >= 0) {
        continue; // Skip, already at or over 207%
      }

      BigDecimal newBalance =
          currentBalance
              .multiply(GROWTH_RATE)
              .setScale(2, RoundingMode.HALF_EVEN); // banking rounding

      if (newBalance.compareTo(maxBalance) > 0) {
        newBalance = maxBalance;
      }

      totalGiveAway = totalGiveAway.add(newBalance.subtract(currentBalance));
      luckyAccounts.add(userId);

      account.setBalance(newBalance);
    }

    log.info(
        "Total give away: [{}], accounts were increased: {}", totalGiveAway, luckyAccounts.size());

    accountRepository.saveAll(accounts);
  }
}
