package dev.ngb.domain.event_stream.event;

import dev.ngb.application.event.Topic;
import dev.ngb.constant.TopicNames;
import dev.ngb.domain.event_stream.constant.PushEventType;

import java.time.Instant;
import java.util.Set;

@Topic(TopicNames.PUSH_SYSTEM_ADMIN_NOTIFICATION)
public record SystemAdminNotificationEvent(
        String id,
        Set<String> userIds,
        String groupId,
        Instant occurredAt,
        String alertLevel,
        String message
) implements PushEvent {
    @Override
    public PushEventType type() {
        return PushEventType.SYSTEM_ADMIN_NOTIFICATION;
    }
}
