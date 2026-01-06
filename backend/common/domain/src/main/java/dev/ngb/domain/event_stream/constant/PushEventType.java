package dev.ngb.domain.event_stream.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PushEventType {
    APP_NOTIFICATION("an"),
    SYSTEM_ADMIN_NOTIFICATION("san"),
    TENANT_ADMIN_NOTIFICATION("tan"),
    PROCESS_UPDATE("pu");

    private final String code;
}
