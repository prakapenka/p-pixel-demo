package localhost.ppixeldemo.features.balance.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import localhost.ppixeldemo.common.validation.PPixelBalance;
import localhost.ppixeldemo.features.users.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class AccountEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne private UserEntity user;

  @PPixelBalance private BigDecimal balance;
}
