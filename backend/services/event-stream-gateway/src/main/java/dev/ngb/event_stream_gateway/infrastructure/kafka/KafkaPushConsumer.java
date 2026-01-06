package dev.ngb.event_stream_gateway.infrastructure.kafka;

import dev.ngb.constant.TopicNames;
import dev.ngb.domain.event_stream.event.*;
import dev.ngb.event_stream_gateway.application.port.EventPublisherGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaPushConsumer {

    private final EventPublisherGateway eventPublisherGateway;

    @KafkaListener(topicPattern = TopicNames.PUSH_EVENTS_PATTERN)
    public void consume(ConsumerRecord<String, Object> record) {
        String topic = record.topic();
        Object payload = record.value();

        switch (topic) {
            case TopicNames.PUSH_APP_NOTIFICATION ->
                    eventPublisherGateway.publish((AppNotificationEvent) payload);
            case TopicNames.PUSH_SYSTEM_ADMIN_NOTIFICATION ->
                    eventPublisherGateway.publish((SystemAdminNotificationEvent) payload);
            case TopicNames.PUSH_TENANT_ADMIN_NOTIFICATION ->
                    eventPublisherGateway.publish((TenantAdminNotificationEvent) payload);
            case TopicNames.PUSH_PROCESS_UPDATE ->
                    eventPublisherGateway.publish((ProcessUpdateEvent) payload);
            default ->
                    log.warn("Unhandled topic: {}", topic);
        }
    }
}
