package localhost.ppixeldemo.features.email.dto;

import jakarta.validation.constraints.NotNull;
import localhost.ppixeldemo.common.validation.PPixelEmail;

public record UpdateEmailDTO(@NotNull Long id, @PPixelEmail String email) {}
