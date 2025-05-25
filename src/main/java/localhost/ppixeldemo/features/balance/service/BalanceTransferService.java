package localhost.ppixeldemo.features.balance.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.function.Function;
import localhost.ppixeldemo.features.balance.dto.BalanceTransferDTO;
import localhost.ppixeldemo.features.balance.exception.BalanceTransferRejectedException;
import localhost.ppixeldemo.features.balance.repository.AccountRepository;
import localhost.ppixeldemo.features.users.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class BalanceTransferService {

  private static final BigDecimal MAX = new BigDecimal("9999999999999.99");

  private final AccountRepository accountRepository;
  private final Function<Authentication, UserEntity> userResolver;
  private final Function<Long, UserEntity> byIdResolver;

  @Transactional
  public void transfer(Authentication authentication, BalanceTransferDTO transferDTO) {

    final BigDecimal amount = transferDTO.transferBalance();
    final var from = userResolver.apply(authentication);
    final var to = byIdResolver.apply(transferDTO.toUser());

    final var fromAccount =
        accountRepository
            .findByUserForUpdate(from)
            .orElseThrow(() -> new EntityNotFoundException("Sender account not found"));
    final var toAccount =
        accountRepository
            .findByUserForUpdate(to)
            .orElseThrow(() -> new EntityNotFoundException("Receiver account not found"));

    if (fromAccount.getBalance().compareTo(amount) < 0) {
      log.warn(
          "Unable to transfer balance from [{}], actual balance: [{}], attempt to transfer: [{}]",
          from.getId(),
          fromAccount.getBalance(),
          amount);
      throw new BalanceTransferRejectedException();
    }

    BigDecimal newToBalance = toAccount.getBalance().add(amount);

    if (newToBalance.compareTo(MAX) > 0) {
      log.warn(
          "Receiver's [{}] balance [{}] would exceed allowed maximum when transfer [{}].",
          to.getId(),
          toAccount.getBalance(),
          newToBalance);
      throw new BalanceTransferRejectedException();
    }

    fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
    toAccount.setBalance(newToBalance);

    log.info("Transferred [{}] from [{}] to [{}]", amount, from.getId(), to.getId());
  }
}
