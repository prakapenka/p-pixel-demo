package localhost.ppixeldemo.features.balance.service;

import jakarta.transaction.Transactional;
import localhost.ppixeldemo.features.balance.dto.BalanceTransferDTO;
import org.springframework.stereotype.Service;

@Service
public class BalanceTransferService {

  @Transactional
  public void transfer(BalanceTransferDTO transferDTO) {}
}
