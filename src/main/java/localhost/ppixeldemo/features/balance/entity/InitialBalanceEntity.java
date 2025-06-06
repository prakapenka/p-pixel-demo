package localhost.ppixeldemo.features.balance.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import localhost.ppixeldemo.common.validation.PPixelBalance;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "INITIAL_ACCOUNT_BALANCE")
@Getter
@NoArgsConstructor
public class InitialBalanceEntity {

  @Id private Long userId;

  @PPixelBalance private BigDecimal initialBalance;

  @PPixelBalance private BigDecimal maxBalance;

  public InitialBalanceEntity(Long userId, BigDecimal initialBalance, BigDecimal maxBalance) {
    this.userId = userId;
    this.initialBalance = initialBalance;
    this.maxBalance = maxBalance;
  }
}
