package localhost.ppixeldemo.features.users.common;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.function.Function;
import localhost.ppixeldemo.features.users.entity.UserEntity;
import localhost.ppixeldemo.features.users.exception.UserNotFoundByNameException;
import localhost.ppixeldemo.features.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthenticationToUserRefResolver implements Function<Authentication, UserEntity> {

  private final UserRepository repository;
  private final EntityManager entityManager;

  @Transactional(Transactional.TxType.MANDATORY)
  @Override
  public UserEntity apply(Authentication authentication) {
    final Long id =
        repository
            .findIdByName(authentication.getName())
            .orElseThrow(UserNotFoundByNameException::new);
    return entityManager.getReference(UserEntity.class, id);
  }
}
