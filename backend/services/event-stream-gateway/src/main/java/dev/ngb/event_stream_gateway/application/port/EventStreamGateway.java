package dev.ngb.event_stream_gateway.application.port;

import dev.ngb.domain.event_stream.event.PushEvent;

import java.util.function.Consumer;

import dev.ngb.domain.event_stream.constant.PushEventType;

public interface EventStreamGateway {
    void registerListener(PushEventType eventType, String userId, Consumer<PushEvent> callback);
    void removeListener(PushEventType eventType, String userId);
}
