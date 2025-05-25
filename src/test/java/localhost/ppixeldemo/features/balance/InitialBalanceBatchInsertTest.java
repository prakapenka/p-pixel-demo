package localhost.ppixeldemo.features.balance;

import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import localhost.ppixeldemo.features.balance.entity.InitialBalanceEntity;
import localhost.ppixeldemo.features.balance.repository.InitialBalanceRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles(profiles = {"default", "dev"})
@SpringBootTest
@RequiredArgsConstructor
public class InitialBalanceBatchInsertTest {

  @Autowired private InitialBalanceRepository initialBalanceRepository;
  @Autowired private EntityManager entityManager;

  @Test
  @Transactional
  public void testBatchInsert() {
    List<InitialBalanceEntity> batch = new ArrayList<>();

    for (long i = 1; i <= 100; i++) {
      batch.add(
          new InitialBalanceEntity(i, BigDecimal.valueOf(100.00), BigDecimal.valueOf(207.00)));
    }

    initialBalanceRepository.saveAll(batch);
    entityManager.flush(); // Force batch execution
    entityManager.clear(); // Detach for good measure
  }
}
