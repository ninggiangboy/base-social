package dev.ngb.infrastructure.jwt.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class JwtProperties {

    @Value("${app.jwt.private-key}")
    private String privateKey;

    @Value("${app.jwt.expiration-ms}")
    private long expirationMs;
}
