package localhost.ppixeldemo.features.phone.rest;

import static localhost.ppixeldemo.config.OpenApiConfig.PPIXEL_SEC;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import localhost.ppixeldemo.common.validation.PPixelPhone;
import localhost.ppixeldemo.features.phone.dto.UpdatePhoneRequestDTO;
import localhost.ppixeldemo.features.phone.service.PhoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "User phones operations")
@SecurityRequirement(name = PPIXEL_SEC)
@RequiredArgsConstructor
@RestController
@RequestMapping(
    value = "/api/user/phone",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class PhoneController {

  private final PhoneService service;

  @Operation(description = "create new phone for user. Phone must be unique and valid")
  @PutMapping
  public ResponseEntity<Object> createPhone(@PPixelPhone String phone) {
    return ResponseEntity.ok().build();
  }

  @Operation(description = "update existing phone by id")
  @PostMapping
  public ResponseEntity<Object> updatePhone(@Valid UpdatePhoneRequestDTO updatePhoneRequestDTO) {
    return ResponseEntity.ok().build();
  }

  @Operation(description = "delete existing phone by id")
  @DeleteMapping
  public ResponseEntity<Object> deletePhone(@NotNull Long id) {
    return ResponseEntity.ok().build();
  }
}
