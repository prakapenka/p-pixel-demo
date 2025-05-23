package localhost.ppixeldemo.features.email.entity;

import jakarta.persistence.*;
import localhost.ppixeldemo.common.validation.PPixelEmail;
import localhost.ppixeldemo.features.users.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "EMAIL_DATA")
@Getter
@NoArgsConstructor
public class EmailEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne private UserEntity user;

  @PPixelEmail private String email;
}
