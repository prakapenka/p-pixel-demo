package localhost.ppixeldemo.features.phone.repository;

import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import localhost.ppixeldemo.features.phone.entity.PhoneEntity;
import localhost.ppixeldemo.features.phone.entity.PhoneView;
import localhost.ppixeldemo.features.users.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends JpaRepository<PhoneEntity, Long> {

  boolean existsByPhone(String phone);

  Page<PhoneView> findAllByUser(UserEntity user, Pageable pageable);

  Optional<PhoneEntity> findByIdAndUser(@NotNull Long id, UserEntity user);

  int deleteByIdAndUser(Long id, UserEntity user);
}
