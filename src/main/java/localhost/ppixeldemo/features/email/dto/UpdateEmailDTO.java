package localhost.ppixeldemo.features.email.dto;

import localhost.ppixeldemo.common.validation.PPixelEmail;

public record UpdateEmailDTO(@PPixelEmail String email) {}
