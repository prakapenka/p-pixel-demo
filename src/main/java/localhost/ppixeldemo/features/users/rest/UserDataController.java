package localhost.ppixeldemo.features.users.rest;

import static localhost.ppixeldemo.config.OpenApiConfig.PPIXEL_SEC;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import localhost.ppixeldemo.common.dto.PagedResponse;
import localhost.ppixeldemo.common.validation.PPixelBirthDate;
import localhost.ppixeldemo.features.users.dto.UserResponseDTO;
import localhost.ppixeldemo.features.users.service.UserDataServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "User search operations")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/user")
public class UserDataController {

  private final UserDataServiceV2 service;

  @Operation(description = "search user by fields")
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public PagedResponse<UserResponseDTO> searchUsers(
      @RequestParam(required = false) @Size(min = 1, max = 500) String name,
      @RequestParam(required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") @PPixelBirthDate
          LocalDate dateOfBirth,
      @RequestParam(required = false) @Size(max = 200) @Email String email,
      @RequestParam(required = false) @Size(max = 15, min = 1) String phone,
      @RequestParam(defaultValue = "0") @Min(0) int page,
      @RequestParam(defaultValue = "10") @Min(1) @Max(1000) int size) {
    final Pageable pageable = PageRequest.of(page, size);
    return service.searchUsers(name, dateOfBirth, email, phone, pageable);
  }
}
