package localhost.ppixeldemo.config;

import localhost.ppixeldemo.common.jwt.PPixelJwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

  private final PPixelJwtFilter jwtFilter;

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth.requestMatchers("/v3/api-docs/**").permitAll())
        .authorizeHttpRequests(auth -> auth.requestMatchers("/swagger-ui/**").permitAll())
        .authorizeHttpRequests(auth -> auth.requestMatchers("/auth/login").permitAll())
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers(
                        GET, "/actuator/health/liveness", "/actuator/health/readiness")
                    .permitAll())
        // allow all searches
            .authorizeHttpRequests(auth -> auth.requestMatchers(GET, "/api/user").permitAll())
        .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}
