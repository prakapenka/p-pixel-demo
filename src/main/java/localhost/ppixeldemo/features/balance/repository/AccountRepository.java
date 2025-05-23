package localhost.ppixeldemo.features.balance.repository;

import localhost.ppixeldemo.features.balance.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {}
