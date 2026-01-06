package dev.ngb.domain.event_stream.event;

import dev.ngb.application.event.Event;
import dev.ngb.domain.event_stream.constant.PushEventType;
import java.util.Set;

public interface PushEvent extends Event {
    String id();
    PushEventType type();
    /**
     * Target specific users.
     * @return Set of user IDs, or empty/null if targeting group or all.
     */
    Set<String> userIds();
    /**
     * Target a specific group.
     * @return Group ID, or null if targeting users.
     */
    String groupId();
}
