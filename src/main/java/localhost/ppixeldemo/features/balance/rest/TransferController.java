package localhost.ppixeldemo.features.balance.rest;

import static localhost.ppixeldemo.config.OpenApiConfig.PPIXEL_SEC;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import localhost.ppixeldemo.features.balance.dto.BalanceTransferDTO;
import localhost.ppixeldemo.features.balance.service.BalanceTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Balance", description = "User balance operations")
@SecurityRequirement(name = PPIXEL_SEC)
@RequiredArgsConstructor
@RestController
@RequestMapping(
    value = "/api/user/transfer",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class TransferController {

  private final BalanceTransferService service;

  @Operation(description = "transfer amount to one of the users defined by user id")
  @PostMapping
  public ResponseEntity<Object> transferBalance(@Valid BalanceTransferDTO transferDTO) {
    service.transfer(transferDTO);
    return ResponseEntity.ok().build();
  }
}
