package dev.ngb.domain;

import java.util.Map;

public interface DomainError {
    String name();

    String getMessage();

    default DomainException exception() {
        return new DomainException(this);
    }

    default DomainException exception(Map<String, Object> details) {
        return new DomainException(this, details);
    }
}

