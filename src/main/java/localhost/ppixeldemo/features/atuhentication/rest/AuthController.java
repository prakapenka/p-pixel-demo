package localhost.ppixeldemo.features.atuhentication.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import localhost.ppixeldemo.common.jwt.PPixelJwtService;
import localhost.ppixeldemo.features.atuhentication.dto.AuthRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Security", description = "Token retrieval endpoint")
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthenticationManager authManager;
  private final PPixelJwtService jwtService;

  @Operation(description = "check username password, and return token if success")
  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody AuthRequestDTO requestDTO) {
    try {
      Authentication auth =
          authManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                  requestDTO.username(), requestDTO.password()));
      String token = jwtService.createToken(requestDTO.username());
      return ResponseEntity.ok(token);
    } catch (AuthenticationException ex) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
  }
}
