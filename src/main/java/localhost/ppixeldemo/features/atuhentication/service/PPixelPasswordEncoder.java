package localhost.ppixeldemo.features.atuhentication.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PPixelPasswordEncoder implements PasswordEncoder {
  @Override
  public String encode(CharSequence rawPassword) {
    return rawPassword.toString();
  }

  @Override
  public boolean matches(CharSequence rawPassword, String encodedPassword) {
    return rawPassword.toString().equals(encodedPassword);
  }
}
