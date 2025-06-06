package localhost.ppixeldemo.features.balance.repository;

import localhost.ppixeldemo.features.balance.entity.InitialBalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InitialBalanceRepository extends JpaRepository<InitialBalanceEntity, Long> {}
