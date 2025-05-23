package localhost.ppixeldemo.features.users.service;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.function.Function;
import localhost.ppixeldemo.features.users.dto.UserResponseDTO;
import localhost.ppixeldemo.features.users.entity.UserEntity;
import localhost.ppixeldemo.features.users.repository.UserRepository;
import localhost.ppixeldemo.features.users.repository.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDataService {

  private final UserRepository userRepository;
  private final Function<UserEntity, UserResponseDTO> mapper;

  @Transactional
  public Page<UserResponseDTO> searchUsers(
      String name, LocalDate dateOfBirth, String email, String phone, Pageable pageable) {
    final Specification<UserEntity> spec =
        Specification.where(UserSpecification.nameLike(name))
            .and(UserSpecification.dateOfBirthAfter(dateOfBirth))
            .and(UserSpecification.hasEmail(email))
            .and(UserSpecification.hasPhone(phone));
    return userRepository.findAll(spec, pageable).map(mapper);
  }
}
