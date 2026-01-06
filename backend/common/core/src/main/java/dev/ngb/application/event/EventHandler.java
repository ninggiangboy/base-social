package dev.ngb.application.event;

import dev.ngb.application.ApplicationService;

public interface EventHandler<E extends Event> extends ApplicationService {
    void on(E event);
}
