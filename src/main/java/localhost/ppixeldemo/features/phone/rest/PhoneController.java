package localhost.ppixeldemo.features.phone.rest;

import static localhost.ppixeldemo.config.OpenApiConfig.PPIXEL_SEC;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import localhost.ppixeldemo.common.dto.PagedResponse;
import localhost.ppixeldemo.common.validation.PPixelPhone;
import localhost.ppixeldemo.common.validation.ValidNotNull;
import localhost.ppixeldemo.features.phone.dto.PhoneResponseDTO;
import localhost.ppixeldemo.features.phone.dto.UpdatePhoneRequestDTO;
import localhost.ppixeldemo.features.phone.service.PhoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "User phones operations")
@SecurityRequirement(name = PPIXEL_SEC)
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/user/phone")
public class PhoneController {

  private final PhoneService service;

  @GetMapping
  public PagedResponse<PhoneResponseDTO> getPhones(
      @RequestParam(defaultValue = "0") @Min(0) int page,
      @RequestParam(defaultValue = "10") @Min(1) @Max(1000) int size,
      Authentication authentication) {
    final Pageable pageable = PageRequest.of(page, size);
    return service.getPhones(authentication, pageable);
  }

  @Operation(description = "create new phone for user. Phone must be unique and valid")
  @PutMapping
  public ResponseEntity<Object> createPhone(
      @RequestParam(name = "phone") @PPixelPhone String phone, Authentication authentication) {
    service.createPhoneForUser(authentication, phone);
    return ResponseEntity.ok().build();
  }

  @Operation(description = "update existing phone by id")
  @PostMapping
  public ResponseEntity<Object> updatePhone(
      @RequestBody @ValidNotNull UpdatePhoneRequestDTO updatePhoneRequestDTO,
      Authentication authentication) {
    service.updatePhone(authentication, updatePhoneRequestDTO);
    return ResponseEntity.ok().build();
  }

  @Operation(description = "delete existing phone by id")
  @DeleteMapping
  public ResponseEntity<Object> deletePhone(
      @RequestParam(name = "id") @NotNull Long id, Authentication authentication) {
    service.deletePhone(authentication, id);
    return ResponseEntity.ok().build();
  }
}
