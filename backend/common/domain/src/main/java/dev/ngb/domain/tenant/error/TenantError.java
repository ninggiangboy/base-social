package dev.ngb.domain.tenant.error;

import dev.ngb.domain.DomainError;
import dev.ngb.domain.DomainException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
@Getter
public enum TenantError implements DomainError {

    DUPLICATE_CODE("Tenant code already exists") {
        public DomainException exception(Long existedId) {
            return new DomainException(this, Map.of("existedId", existedId));
        }
    },
    NOT_FOUND("Tenant not found"),
    INVALID_DATA("Invalid tenant data"),
    INVALID_STATUS("Invalid status code");

    private final String message;
}
