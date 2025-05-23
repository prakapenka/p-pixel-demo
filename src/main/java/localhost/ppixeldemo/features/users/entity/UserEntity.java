package localhost.ppixeldemo.features.users.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;
import localhost.ppixeldemo.features.balance.entity.AccountEntity;
import localhost.ppixeldemo.features.email.entity.EmailEntity;
import localhost.ppixeldemo.features.phone.entity.PhoneEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "\"user\"")
@Entity
@Getter
@NoArgsConstructor
public class UserEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Name is required")
  @Size(max = 500, message = "Name must be at most 500 characters")
  private String name;

  @NotBlank(message = "Password is required")
  @Size(min = 8, max = 500, message = "Name must be at most 500 characters")
  private String password;

  private LocalDate dateOfBirth;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<EmailEntity> emails;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<PhoneEntity> phones;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, optional = false)
  private AccountEntity account;
}
