package dev.ngb.application.port;

import dev.ngb.application.dto.AccessToken;

import java.util.Map;

public interface AccessTokenGenerator {
    AccessToken generateToken(String username, Map<String, Object> claims);
}
