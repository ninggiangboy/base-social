package dev.ngb.event_stream_gateway.infrastructure.redis;

import dev.ngb.domain.event_stream.constant.PushEventType;
import dev.ngb.event_stream_gateway.application.port.EventStreamGateway;
import dev.ngb.event_stream_gateway.application.port.EventPublisherGateway;
import dev.ngb.domain.event_stream.event.PushEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisPubSubEventGateway implements EventPublisherGateway, EventStreamGateway {

    private static final String USER_TOPIC_PREFIX = "eg:u:";
    private static final String GROUP_TOPIC_PREFIX = "eg:g:";
    
    private final RedissonClient redissonClient;
    private final Map<String, Integer> subscriptionIds = new ConcurrentHashMap<>();

    @Override
    public void publish(PushEvent event) {
        String eventType = event.type().getCode();
        if (event.groupId() != null) {
            String topicName = GROUP_TOPIC_PREFIX + eventType + ":" + event.groupId();
            publishTo(topicName, event);
        }

        if (event.userIds() != null) {
            for (String userId : event.userIds()) {
                String topicName = USER_TOPIC_PREFIX + eventType + ":" + userId;
                publishTo(topicName, event);
            }
        }
    }

    private void publishTo(String topicName, PushEvent event) {
        RTopic topic = redissonClient.getTopic(topicName);
        log.debug("Publishing event {} to topic {}", event.id(), topicName);
        topic.publish(event);
    }

    @Override
    public void registerListener(PushEventType eventType, String userId, Consumer<PushEvent> callback) {
        String topicName = USER_TOPIC_PREFIX + eventType.name() + ":" + userId;
        RTopic topic = redissonClient.getTopic(topicName);
        int subscriptionId = topic.addListener(PushEvent.class, (channel, msg) -> {
            log.debug("Received event {} from topic {}", msg.id(), channel);
            callback.accept(msg);
        });
        subscriptionIds.put(userId + ":" + eventType.name(), subscriptionId);
        log.info("Subscribed user {} to topic {}", userId, topicName);
    }

    @Override
    public void removeListener(PushEventType eventType, String userId) {
        String key = userId + ":" + eventType.name();
        Integer subscriptionId = subscriptionIds.remove(key);
        if (subscriptionId != null) {
            String topicName = USER_TOPIC_PREFIX + eventType.name() + ":" + userId;
            RTopic topic = redissonClient.getTopic(topicName);
            topic.removeListener(subscriptionId);
            log.info("Unsubscribed user {} from topic {}", userId, topicName);
        }
    }
}
