package localhost.ppixeldemo.features.balance.service;

import jakarta.transaction.Transactional;
import localhost.ppixeldemo.features.balance.dto.BalanceTransferDTO;
import localhost.ppixeldemo.features.balance.repository.AccountRepository;
import localhost.ppixeldemo.features.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BalanceTransferService {

  private final AccountRepository accountRepository;
  private final UserRepository userRepository;

  @Transactional
  public void transfer(BalanceTransferDTO transferDTO) {
    var userTo = userRepository.findById(transferDTO.toUser());
  }
}
