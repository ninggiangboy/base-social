package dev.ngb.event_stream_gateway.web;

import dev.ngb.event_stream_gateway.application.port.EventStreamGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.Duration;

import dev.ngb.domain.event_stream.constant.PushEventType;

@Slf4j
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventsStreamResource {

    private final EventStreamGateway eventStreamGateway;

    @GetMapping(value = "/app-notifications", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribeAppNotification(String userId) {
        return subscribe(PushEventType.APP_NOTIFICATION, userId);
    }

    @GetMapping(value = "/system-admin-notifications", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribeSystemAdmin(String userId) {
        return subscribe(PushEventType.SYSTEM_ADMIN_NOTIFICATION, userId);
    }

    @GetMapping(value = "/tenant-admin-notifications", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribeTenantAdmin(String userId) {
        return subscribe(PushEventType.TENANT_ADMIN_NOTIFICATION, userId);
    }

    @GetMapping(value = "/process-update", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribeProcessUpdate(String userId) {
        return subscribe(PushEventType.PROCESS_UPDATE, userId);
    }

    private SseEmitter subscribe(PushEventType eventType, String userId) {
        log.info("New SSE subscription request for user: {} eventType: {}", userId, eventType);

        SseEmitter emitter = new SseEmitter(Duration.ofHours(1).toMillis());

        eventStreamGateway.registerListener(eventType, userId, event -> {
            try {
                emitter.send(SseEmitter.event()
                        .id(event.id())
                        .name(event.type().name())
                        .data(event)
                );
            } catch (IOException e) {
                log.error("Failed to send SSE to user {}: {}", userId, e.getMessage());
                emitter.completeWithError(e);
            }
        });

        emitter.onCompletion(() -> unsubscribe(eventType, userId));
        emitter.onTimeout(() -> unsubscribe(eventType, userId));
        emitter.onError(ex -> unsubscribe(eventType, userId));

        return emitter;
    }

    private void unsubscribe(PushEventType eventType, String userId) {
        log.info("SSE subscription closed for user: {} eventType: {}", userId, eventType);
        eventStreamGateway.removeListener(eventType, userId);
    }
}
