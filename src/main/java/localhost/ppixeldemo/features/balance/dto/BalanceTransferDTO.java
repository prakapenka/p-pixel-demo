package localhost.ppixeldemo.features.balance.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import localhost.ppixeldemo.common.validation.PPixelBalance;

public record BalanceTransferDTO(@NotNull Long toUser, @PPixelBalance BigDecimal transferBalance) {}
