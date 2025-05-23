package localhost.ppixeldemo.features.phone.service;

import jakarta.transaction.Transactional;
import localhost.ppixeldemo.features.phone.entity.PhoneEntity;
import localhost.ppixeldemo.features.users.entity.UserEntity;
import localhost.ppixeldemo.features.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PhoneService {

  private final UserRepository users;

  @Transactional
  public void createPhoneForUser(String userName, String phone) {
    UserEntity user = users.findUserByName(userName);

    // no unique checks
    PhoneEntity newPhone = new PhoneEntity();
    newPhone.setPhone(phone);

    newPhone.setUser(user);
    user.getPhones().add(newPhone);
  }
}
