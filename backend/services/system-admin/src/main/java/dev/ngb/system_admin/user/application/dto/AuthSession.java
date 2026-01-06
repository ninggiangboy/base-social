package dev.ngb.system_admin.user.application.dto;

import lombok.Builder;

@Builder
public record AuthSession(
        String accessToken,
        long duration
) {
}
