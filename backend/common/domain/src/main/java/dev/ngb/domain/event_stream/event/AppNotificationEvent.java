package dev.ngb.domain.event_stream.event;

import dev.ngb.application.event.Topic;
import dev.ngb.constant.TopicNames;
import dev.ngb.domain.event_stream.constant.PushEventType;

import java.time.Instant;
import java.util.Set;

@Topic(TopicNames.PUSH_APP_NOTIFICATION)
public record AppNotificationEvent(
        String id,
        Set<String> userIds,
        String groupId,
        Instant occurredAt,
        String title,
        String message,
        String deepLink
) implements PushEvent {
    @Override
    public PushEventType type() {
        return PushEventType.APP_NOTIFICATION;
    }
}
