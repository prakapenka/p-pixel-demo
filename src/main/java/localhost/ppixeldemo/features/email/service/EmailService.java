package localhost.ppixeldemo.features.email.service;

import jakarta.validation.constraints.NotNull;
import java.util.function.Function;
import localhost.ppixeldemo.common.dto.PagedResponse;
import localhost.ppixeldemo.config.cache.UserEmailChangedEvent;
import localhost.ppixeldemo.features.email.dto.EmailResponseDTO;
import localhost.ppixeldemo.features.email.dto.UpdateEmailDTO;
import localhost.ppixeldemo.features.email.entity.EmailEntity;
import localhost.ppixeldemo.features.email.exception.EmailIsAlreadyUsedException;
import localhost.ppixeldemo.features.email.exception.EmailNotFoundException;
import localhost.ppixeldemo.features.email.repository.EmailRepository;
import localhost.ppixeldemo.features.users.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class EmailService {

  private final Function<Authentication, UserEntity> userResolver;
  private final EmailRepository repository;
  private final ApplicationEventPublisher publisher;

  @Transactional(readOnly = true)
  public PagedResponse<EmailResponseDTO> getEmails(
      Authentication authentication, Pageable pageable) {
    final var userRef = userResolver.apply(authentication);
    Page<EmailResponseDTO> page =
        repository
            .findAllByUser(userRef, pageable)
            .map(e -> new EmailResponseDTO(e.getId(), e.getEmail()));
    return new PagedResponse<>(
        page.get().toList(),
        page.getNumber(),
        page.getSize(),
        page.getTotalElements(),
        page.getTotalPages());
  }

  @Transactional
  public void createEmail(Authentication authentication, String email) {
    if (repository.existsByEmail(email)) {
      throw new EmailIsAlreadyUsedException();
    }

    final var userRef = userResolver.apply(authentication);

    EmailEntity newEmail = new EmailEntity();
    newEmail.setEmail(email);
    newEmail.setUser(userRef);

    repository.save(newEmail);
    publisher.publishEvent(new UserEmailChangedEvent(userRef.getId()));
  }

  @Transactional
  public void updateEmail(Authentication authentication, UpdateEmailDTO updateEmailRequest) {
    if (repository.existsByEmail(updateEmailRequest.email())) {
      throw new EmailIsAlreadyUsedException();
    }

    final var userRef = userResolver.apply(authentication);
    final var existedEmail =
        repository
            .findByIdAndUser(updateEmailRequest.id(), userRef)
            .orElseThrow(EmailNotFoundException::new);
    existedEmail.setEmail(updateEmailRequest.email());
    publisher.publishEvent(new UserEmailChangedEvent(userRef.getId()));
  }

  @Transactional
  public void deleteEmail(Authentication authentication, @NotNull Long id) {
    final var userRef = userResolver.apply(authentication);

    final int delCount = repository.deleteByIdAndUser(id, userRef);
    if (delCount == 0) {
      throw new EmailNotFoundException();
    }
    publisher.publishEvent(new UserEmailChangedEvent(userRef.getId()));
  }
}
