package localhost.ppixeldemo.features.users.dto;

import java.time.LocalDate;

public record UserCoreDTO(Long id, String name, LocalDate dateOfBirth) {}
