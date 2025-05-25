package localhost.ppixeldemo.features.users.common;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import java.util.function.Function;
import localhost.ppixeldemo.features.users.entity.UserEntity;
import localhost.ppixeldemo.features.users.exception.UserNotFoundByIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class IdToUserRefResolver implements Function<Long, UserEntity> {

  private final EntityManager em;

  @Override
  public UserEntity apply(Long id) {
    try {
      return em.getReference(UserEntity.class, id);
    } catch (EntityNotFoundException notFoundException) {
      throw new UserNotFoundByIdException(notFoundException);
    }
  }
}
