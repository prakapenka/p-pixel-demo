package localhost.ppixeldemo.features.phone.entity;

import jakarta.persistence.*;
import localhost.ppixeldemo.common.validation.PPixelPhone;
import localhost.ppixeldemo.features.users.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PHONE_DATA")
@Getter
@NoArgsConstructor
public class PhoneEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phone_id_seq")
  @SequenceGenerator(name = "phone_id_seq", sequenceName = "phone_id_seq")
  private Long id;

  @Setter
  @ManyToOne(fetch = FetchType.LAZY)
  private UserEntity user;

  @Setter @PPixelPhone private String phone;

  @Version private int version;
}
