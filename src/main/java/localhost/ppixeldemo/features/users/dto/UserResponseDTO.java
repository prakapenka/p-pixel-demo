package localhost.ppixeldemo.features.users.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record UserResponseDTO(
    Long id,
    String name,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy") LocalDate dateOfBirth,
    List<String> emails,
    List<String> phones,
    BigDecimal balance) {}
