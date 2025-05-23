package localhost.ppixeldemo.features.users.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import localhost.ppixeldemo.features.atuhentication.entity.AuthProjection;
import localhost.ppixeldemo.features.users.dto.UserCoreDTO;
import localhost.ppixeldemo.features.users.entity.UserEntity;
import localhost.ppixeldemo.features.users.exception.UserNotFoundByNameException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository
    extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

  @Query(
      """
      SELECT new localhost.ppixeldemo.features.users.dto.UserCoreDTO(u.id, u.name, u.dateOfBirth, a.balance)
        FROM UserEntity u
        JOIN u.account a
        WHERE (:name = '' OR u.name LIKE CONCAT(:name, '%'))
          AND (u.dateOfBirth >= :dateOfBirth)
          AND (:email IS NULL OR EXISTS (
               SELECT 1 FROM EmailEntity e WHERE e.user = u AND e.email = :email))
          AND (:phone IS NULL OR EXISTS (
               SELECT 1 FROM PhoneEntity p WHERE p.user = u AND p.phone = :phone))
  """)
  Page<UserCoreDTO> searchUsersCore(
      @Param("name") String name,
      @Param("dateOfBirth") LocalDate dateOfBirth,
      @Param("email") String email,
      @Param("phone") String phone,
      Pageable pageable);

  @Query("SELECT e.user.id, e.email FROM EmailEntity e WHERE e.user.id IN :userIds")
  List<Object[]> findEmailsByUserIds(@Param("userIds") List<Long> userIds);

  @Query("SELECT p.user.id, p.phone FROM PhoneEntity p WHERE p.user.id IN :userIds")
  List<Object[]> findPhonesByUserIds(@Param("userIds") List<Long> userIds);

  @Query("SELECT u.name AS name, u.password AS password FROM UserEntity u WHERE u.name = :name")
  Optional<AuthProjection> findAuthProjectionByName(@Param("name") String name);

  default UserEntity findUserByName(String name) {
    return findByName(name).orElseThrow(UserNotFoundByNameException::new);
  }

  Optional<UserEntity> findByName(String name);
}
