package localhost.ppixeldemo.features.users.dto.mapper;

import jakarta.transaction.Transactional;
import java.util.function.Function;
import localhost.ppixeldemo.features.email.entity.EmailEntity;
import localhost.ppixeldemo.features.phone.entity.PhoneEntity;
import localhost.ppixeldemo.features.users.dto.UserResponseDTO;
import localhost.ppixeldemo.features.users.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserViewMapper implements Function<UserEntity, UserResponseDTO> {

  @Transactional(Transactional.TxType.MANDATORY)
  @Override
  public UserResponseDTO apply(UserEntity user) {
    return new UserResponseDTO(
        user.getId(),
        user.getName(),
        user.getDateOfBirth(),
        user.getEmails().stream().map(EmailEntity::getEmail).toList(),
        user.getPhones().stream().map(PhoneEntity::getPhone).toList(),
        user.getAccount().getBalance());
  }
}
