package localhost.ppixeldemo.features.email.repository;

import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import localhost.ppixeldemo.features.email.entity.EmailEntity;
import localhost.ppixeldemo.features.email.entity.EmailView;
import localhost.ppixeldemo.features.users.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<EmailEntity, Long> {

  Page<EmailView> findAllByUser(UserEntity user, Pageable pageable);

  boolean existsByEmail(String phone);

  Optional<EmailEntity> findByIdAndUser(@NotNull Long id, UserEntity user);

  int deleteByIdAndUser(Long id, UserEntity user);
}
