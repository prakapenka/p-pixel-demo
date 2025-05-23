package localhost.ppixeldemo.features.atuhentication.service;

import java.util.List;
import localhost.ppixeldemo.features.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PPixelUserDetailsService implements UserDetailsService {

  private final UserRepository repo;

  @Override
  public UserDetails loadUserByUsername(String name) {
    var user =
        repo.findAuthProjectionByName(name)
            .orElseThrow(() -> new UsernameNotFoundException("Not found"));
    return new User(
        user.getName(), user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER"))) {};
  }
}
