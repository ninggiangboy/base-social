package dev.ngb.infrastructure.jwt.adapter;

import dev.ngb.application.dto.AccessToken;
import dev.ngb.application.port.AccessTokenGenerator;
import dev.ngb.infrastructure.jwt.config.JwtProperties;
import dev.ngb.util.JwtUtils;
import dev.ngb.util.RsaKeyLoaderUtils;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.time.Instant;
import java.util.Map;

@Component
public class JwtTokenGenerator implements AccessTokenGenerator {

    private final JwtProperties jwtProperties;
    private final PrivateKey privateKey;

    public JwtTokenGenerator(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        try {
            this.privateKey = RsaKeyLoaderUtils.loadPrivateKeyFromPem(
                    jwtProperties.getPrivateKey()
            );
        } catch (Exception e) {
            throw new IllegalStateException("Cannot load JWT private key", e);
        }
    }

    @Override
    public AccessToken generateToken(String username, Map<String, Object> claims) {
        String token = JwtUtils.generateToken(
                username,
                claims,
                privateKey,
                jwtProperties.getExpirationMs()
        );

        return AccessToken.builder()
                .token(token)
                .duration(jwtProperties.getExpirationMs())
                .expiresAt(Instant.now().plusMillis(jwtProperties.getExpirationMs()))
                .build();
    }
}
