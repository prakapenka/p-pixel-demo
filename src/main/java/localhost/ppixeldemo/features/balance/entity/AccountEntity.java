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

  // specifically for batch updates
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_id_seq")
  @SequenceGenerator(name = "account_id_seq", sequenceName = "account_id_seq")
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private UserEntity user;

  @Setter @PPixelBalance private BigDecimal balance;

  @Version private int version;
}
