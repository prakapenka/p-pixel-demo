package localhost.ppixeldemo.features.phone.service;

import java.util.function.Function;
import localhost.ppixeldemo.common.dto.PagedResponse;
import localhost.ppixeldemo.features.phone.dto.PhoneResponseDTO;
import localhost.ppixeldemo.features.phone.dto.UpdatePhoneRequestDTO;
import localhost.ppixeldemo.features.phone.entity.PhoneEntity;
import localhost.ppixeldemo.features.phone.exception.PhoneIsAlreadyUsedException;
import localhost.ppixeldemo.features.phone.exception.PhoneNotFoundException;
import localhost.ppixeldemo.features.phone.repository.PhoneRepository;
import localhost.ppixeldemo.features.users.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PhoneService {

  private final Function<Authentication, UserEntity> userResolver;
  private final PhoneRepository phoneRepository;

  @Transactional
  public void createPhoneForUser(Authentication authentication, String phoneString) {
    if (phoneRepository.existsByPhone(phoneString)) {
      throw new PhoneIsAlreadyUsedException();
    }

    final var userRef = userResolver.apply(authentication);
    PhoneEntity newPhone = new PhoneEntity();
    newPhone.setPhone(phoneString);
    newPhone.setUser(userRef);

    phoneRepository.save(newPhone);
  }

  @Transactional
  public void updatePhone(
      Authentication authentication, UpdatePhoneRequestDTO updatePhoneRequestDTO) {
    if (phoneRepository.existsByPhone(updatePhoneRequestDTO.phone())) {
      throw new PhoneIsAlreadyUsedException();
    }

    final var userRef = userResolver.apply(authentication);
    final var phone =
        phoneRepository
            .findByIdAndUser(updatePhoneRequestDTO.poneId(), userRef)
            .orElseThrow(PhoneNotFoundException::new);
    phone.setPhone(updatePhoneRequestDTO.phone());
  }

  @Transactional
  public void deletePhone(Authentication authentication, Long id) {
    final UserEntity user = userResolver.apply(authentication);
    final int delCount = phoneRepository.deleteByIdAndUser(id, user);
    if (delCount == 0) {
      throw new PhoneNotFoundException();
    }
  }

  @Transactional(readOnly = true)
  public PagedResponse<PhoneResponseDTO> getPhones(
      Authentication authentication, Pageable pageable) {
    final var userRef = userResolver.apply(authentication);
    Page<PhoneResponseDTO> page =
        phoneRepository
            .findAllByUser(userRef, pageable)
            .map(e -> new PhoneResponseDTO(e.getId(), e.getPhone()));
    return new PagedResponse<>(
        page.get().toList(),
        page.getNumber(),
        page.getSize(),
        page.getTotalElements(),
        page.getTotalPages());
  }
}
