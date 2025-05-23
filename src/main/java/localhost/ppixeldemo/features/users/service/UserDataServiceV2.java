package localhost.ppixeldemo.features.users.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import localhost.ppixeldemo.common.dto.PagedResponse;
import localhost.ppixeldemo.common.validation.impl.MinLocalDateValidator;
import localhost.ppixeldemo.features.users.dto.UserCoreDTO;
import localhost.ppixeldemo.features.users.dto.UserResponseDTO;
import localhost.ppixeldemo.features.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserDataServiceV2 {

  private final UserRepository userRepository;

  @Transactional(readOnly = true)
  public PagedResponse<UserResponseDTO> searchUsers(
      String name, LocalDate dateOfBirth, String email, String phone, Pageable pageable) {

    name = name != null ? name : "";
    dateOfBirth = dateOfBirth != null ? dateOfBirth : MinLocalDateValidator.MIN.minusDays(1);

    Page<UserCoreDTO> corePage =
        userRepository.searchUsersCore(name, dateOfBirth, email, phone, pageable);

    List<Long> userIds = corePage.getContent().stream().map(UserCoreDTO::id).toList();

    if (userIds.isEmpty()) {
      return new PagedResponse<>(List.of(), pageable.getPageNumber(), pageable.getPageSize(), 0, 0);
    }

    Map<Long, List<String>> emails =
        userRepository.findEmailsByUserIds(userIds).stream()
            .collect(
                Collectors.groupingBy(
                    row -> (Long) row[0],
                    Collectors.mapping(row -> (String) row[1], Collectors.toList())));

    Map<Long, List<String>> phones =
        userRepository.findPhonesByUserIds(userIds).stream()
            .collect(
                Collectors.groupingBy(
                    row -> (Long) row[0],
                    Collectors.mapping(row -> (String) row[1], Collectors.toList())));

    List<UserResponseDTO> dtos =
        corePage.getContent().stream()
            .map(
                core ->
                    new UserResponseDTO(
                        core.id(),
                        core.name(),
                        core.dateOfBirth(),
                        emails.getOrDefault(core.id(), List.of()),
                        phones.getOrDefault(core.id(), List.of()),
                        core.balance()))
            .toList();

    return new PagedResponse<UserResponseDTO>(
        dtos,
        pageable.getPageNumber(),
        pageable.getPageSize(),
        corePage.getTotalElements(),
        corePage.getTotalPages());
  }
}
