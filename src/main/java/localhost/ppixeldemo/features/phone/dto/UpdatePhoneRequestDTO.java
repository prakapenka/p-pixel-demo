package localhost.ppixeldemo.features.phone.dto;

import jakarta.validation.constraints.NotNull;
import localhost.ppixeldemo.common.validation.PPixelPhone;

public record UpdatePhoneRequestDTO(@NotNull Long poneId, @PPixelPhone String phone) {}
