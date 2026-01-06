package dev.ngb.application.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record AccessToken(String token, long duration, Instant expiresAt) {
}
