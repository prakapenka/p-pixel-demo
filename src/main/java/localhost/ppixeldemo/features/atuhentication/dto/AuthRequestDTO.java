package localhost.ppixeldemo.features.atuhentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthRequestDTO(
    @Schema(example = "user1", description = "Username for login") String username,
    @Schema(example = "password", description = "Password for login") String password) {}
