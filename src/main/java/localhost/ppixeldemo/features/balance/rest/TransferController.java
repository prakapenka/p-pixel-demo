package localhost.ppixeldemo.features.balance.rest;

import jakarta.validation.Valid;
import localhost.ppixeldemo.features.balance.dto.BalanceTransferDTO;
import localhost.ppixeldemo.features.balance.service.BalanceTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(
    value = "/api/user/transfer",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class TransferController {

  private final BalanceTransferService service;

  @PostMapping
  public ResponseEntity<Object> transferBalance(@Valid BalanceTransferDTO transferDTO) {
    service.transfer(transferDTO);
    return ResponseEntity.ok().build();
  }
}
