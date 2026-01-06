package dev.ngb.domain.event_stream.event;

import dev.ngb.application.event.Topic;
import dev.ngb.constant.TopicNames;
import dev.ngb.domain.event_stream.constant.PushEventType;

import java.time.Instant;
import java.util.Set;

@Topic(TopicNames.PUSH_PROCESS_UPDATE)
public record ProcessUpdateEvent(
        String id,
        Set<String> userIds,
        String groupId,
        Instant occurredAt,
        String processId,
        String status,
        int progress
) implements PushEvent {
    @Override
    public PushEventType type() {
        return PushEventType.PROCESS_UPDATE;
    }
}
