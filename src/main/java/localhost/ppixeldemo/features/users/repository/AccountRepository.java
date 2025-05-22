package localhost.ppixeldemo.features.users.repository;

import localhost.ppixeldemo.features.users.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
