package localhost.ppixeldemo.features.balance.repository;

import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import localhost.ppixeldemo.features.balance.entity.AccountEntity;
import localhost.ppixeldemo.features.users.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT a FROM AccountEntity a WHERE a.user = :user")
  Optional<AccountEntity> findByUserForUpdate(UserEntity user);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT a FROM AccountEntity a JOIN FETCH a.user WHERE a.user.id IN :userIds")
  List<AccountEntity> findByUserIdsForUpdate(Iterable<Long> userIds);
}
