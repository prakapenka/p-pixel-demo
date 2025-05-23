package localhost.ppixeldemo.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  public static final String PPIXEL_SEC = "SecureMeMaybeSecurityScheme";

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .components(new Components().addSecuritySchemes(PPIXEL_SEC, createSecurityScheme()))
        .info(new Info().title("PPixel server demo"));
  }

  private SecurityScheme createSecurityScheme() {
    return new SecurityScheme()
        .name(PPIXEL_SEC)
        .type(SecurityScheme.Type.HTTP)
        .scheme("bearer")
        .bearerFormat("JWT")
        .in(SecurityScheme.In.HEADER);
  }
}
