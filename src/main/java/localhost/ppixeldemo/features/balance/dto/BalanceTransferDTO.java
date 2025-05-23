package localhost.ppixeldemo.features.balance.dto;

import java.math.BigDecimal;
import localhost.ppixeldemo.common.validation.PPixelBalance;

public record BalanceTransferDTO(Long toUser, @PPixelBalance BigDecimal transferBalance) {}
