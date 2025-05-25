package localhost.ppixeldemo.features.email.rest;

import static localhost.ppixeldemo.config.OpenApiConfig.PPIXEL_SEC;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import localhost.ppixeldemo.common.dto.PagedResponse;
import localhost.ppixeldemo.common.validation.PPixelEmail;
import localhost.ppixeldemo.common.validation.ValidNotNull;
import localhost.ppixeldemo.features.email.dto.EmailResponseDTO;
import localhost.ppixeldemo.features.email.dto.UpdateEmailDTO;
import localhost.ppixeldemo.features.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "User emails operations")
@SecurityRequirement(name = PPIXEL_SEC)
@RequiredArgsConstructor
@RestController
@RequestMapping(
    value = "/api/user/email",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class EmailController {

  private final EmailService service;

  @GetMapping
  public PagedResponse<EmailResponseDTO> getEmails(
      Authentication authentication, Pageable pageable) {
    return service.getEmails(authentication, pageable);
  }

  @Operation(description = "Create new email. Email must be valid and unique")
  @PutMapping
  public ResponseEntity<Object> createEmail(
      Authentication authentication, @PPixelEmail String email) {
    service.createEmail(authentication, email);
    return ResponseEntity.ok().build();
  }

  @Operation(description = "update existing email by id")
  @PostMapping
  public ResponseEntity<Object> updateEmail(
      Authentication authentication, @ValidNotNull UpdateEmailDTO updateEmailRequest) {
    service.updateEmail(authentication, updateEmailRequest);
    return ResponseEntity.ok().build();
  }

  @Operation(description = "delete existing email by id")
  @DeleteMapping
  public ResponseEntity<Object> deleteEmail(Authentication authentication, @NotNull Long id) {
    service.deleteEmail(authentication, id);
    return ResponseEntity.ok().build();
  }
}
