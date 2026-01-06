package dev.ngb.constant;

public class TopicNames {

    private TopicNames() {
        throw new IllegalStateException("Constant class");
    }

    public static final String PUSH_EVENTS_PATTERN = "push\\.events\\.*";
    public static final String PUSH_APP_NOTIFICATION = "push.events.app_notification";
    public static final String PUSH_SYSTEM_ADMIN_NOTIFICATION = "push.events.system_admin";
    public static final String PUSH_TENANT_ADMIN_NOTIFICATION = "push.events.tenant_admin";
    public static final String PUSH_PROCESS_UPDATE = "push.events.process_update";
    
    public static final String OUTBOX_EVENTS = "cdc.public.event_publications";
}
