package localhost.ppixeldemo.features.phone.entity;

import jakarta.persistence.*;
import localhost.ppixeldemo.common.validation.PPixelPhone;
import localhost.ppixeldemo.features.users.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PHONE_DATA")
@Getter
@NoArgsConstructor
public class PhoneEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne private UserEntity user;

  @PPixelPhone private String phone;
}
