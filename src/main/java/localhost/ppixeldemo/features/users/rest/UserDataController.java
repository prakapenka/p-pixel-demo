package localhost.ppixeldemo.features.users.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import localhost.ppixeldemo.features.users.dto.UserResponseDTO;
import localhost.ppixeldemo.features.users.service.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "User search operations")
@RequiredArgsConstructor
@RestController
@RequestMapping(
    value = "/api/user",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class UserDataController {

  private final UserDataService service;

  @Operation(description = "search user by fields")
  @GetMapping("/users")
  public Page<UserResponseDTO> searchUsers(
      @RequestParam(required = false) @Size(min = 1, max = 500) String name,
      @RequestParam(required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate dateOfBirth,
      @RequestParam(required = false) @Size(max = 200) @Email String email,
      @RequestParam(required = false) @Size(max = 15, min = 1) String phone,
      @RequestParam(defaultValue = "0") @Min(0) int page,
      @RequestParam(defaultValue = "10") @Min(1) int size) {
    final Pageable pageable = PageRequest.of(page, size);
    return service.searchUsers(name, dateOfBirth, email, phone, pageable);
  }
}
