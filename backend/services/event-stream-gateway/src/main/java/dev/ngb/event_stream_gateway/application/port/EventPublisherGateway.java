package dev.ngb.event_stream_gateway.application.port;

import dev.ngb.domain.event_stream.event.PushEvent;

public interface EventPublisherGateway {
    void publish(PushEvent event);
}
