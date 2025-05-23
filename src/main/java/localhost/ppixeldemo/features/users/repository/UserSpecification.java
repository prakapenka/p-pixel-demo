package localhost.ppixeldemo.features.users.repository;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import java.time.LocalDate;
import localhost.ppixeldemo.features.email.entity.EmailEntity;
import localhost.ppixeldemo.features.phone.entity.PhoneEntity;
import localhost.ppixeldemo.features.users.entity.UserEntity;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
  public static Specification<UserEntity> nameLike(String name) {
    return (root, query, cb) ->
        name == null ? null : cb.like(cb.lower(root.get("name")), name.toLowerCase() + "%");
  }

  public static Specification<UserEntity> dateOfBirthAfter(LocalDate date) {
    return (root, query, cb) -> date == null ? null : cb.greaterThan(root.get("dateOfBirth"), date);
  }

  public static Specification<UserEntity> hasEmail(String email) {
    return (root, query, cb) -> {
      if (email == null) return null;
      Join<UserEntity, EmailEntity> emailJoin = root.join("emails", JoinType.LEFT);
      return cb.equal(emailJoin.get("email"), email);
    };
  }

  public static Specification<UserEntity> hasPhone(String phone) {
    return (root, query, cb) -> {
      if (phone == null) return null;
      Join<UserEntity, PhoneEntity> phoneJoin = root.join("phones", JoinType.LEFT);
      return cb.equal(phoneJoin.get("phone"), phone);
    };
  }
}
