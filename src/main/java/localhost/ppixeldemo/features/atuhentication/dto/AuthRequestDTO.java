package localhost.ppixeldemo.features.atuhentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

public record AuthRequestDTO(
    @Schema(example = "user1", description = "Username for login") @Size(min = 1, max = 500)
        String username,
    @Schema(example = "password", description = "Password for login") @Size(min = 8, max = 500)
        String password) {}
