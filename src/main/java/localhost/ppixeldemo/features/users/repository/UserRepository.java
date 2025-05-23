package localhost.ppixeldemo.features.users.repository;

import java.util.Optional;
import localhost.ppixeldemo.features.atuhentication.entity.AuthProjection;
import localhost.ppixeldemo.features.users.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository
    extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

  @EntityGraph(attributePaths = {"emails", "phones", "account"})
  @NonNull
  Page<UserEntity> findAll(Specification<UserEntity> spec, @NonNull Pageable pageable);

  @Query("SELECT u.name AS name, u.password AS password FROM UserEntity u WHERE u.name = :name")
  Optional<AuthProjection> findAuthProjectionByName(@Param("name") String name);
}
