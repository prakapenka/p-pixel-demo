package localhost.ppixeldemo.features.email.entity;

import jakarta.persistence.*;
import localhost.ppixeldemo.common.validation.PPixelEmail;
import localhost.ppixeldemo.features.users.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "EMAIL_DATA")
@Getter
@NoArgsConstructor
public class EmailEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "email_id_seq")
  @SequenceGenerator(name = "email_id_seq", sequenceName = "email_id_seq")
  private Long id;

  @Setter
  @ManyToOne(fetch = FetchType.LAZY)
  private UserEntity user;

  @Setter @PPixelEmail private String email;

  @Version private int version;
}
