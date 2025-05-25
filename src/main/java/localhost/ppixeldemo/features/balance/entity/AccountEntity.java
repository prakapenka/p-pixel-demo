package localhost.ppixeldemo.features.balance.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import localhost.ppixeldemo.common.validation.PPixelBalance;
import localhost.ppixeldemo.features.users.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Table(name = "ACCOUNT")
@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AccountEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne private UserEntity user;

  @Setter @PPixelBalance private BigDecimal balance;

  @Version private int version;
}
