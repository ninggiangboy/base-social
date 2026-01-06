package dev.ngb.domain.event_stream.event;

import dev.ngb.domain.event_stream.constant.PushEventType;

import java.time.Instant;
import java.util.Set;

public record TenantAdminNotificationEvent(
        String id,
        Set<String> userIds,
        String groupId,
        Instant occurredAt,
        String tenantId,
        String message
) implements PushEvent {
    @Override
    public PushEventType type() {
        return PushEventType.TENANT_ADMIN_NOTIFICATION;
    }
}
