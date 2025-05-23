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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Setter @ManyToOne private UserEntity user;

  @Setter @PPixelPhone private String phone;
}
