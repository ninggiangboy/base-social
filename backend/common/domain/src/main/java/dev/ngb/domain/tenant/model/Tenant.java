package dev.ngb.domain.tenant.model;

import dev.ngb.domain.DomainEntity;
import dev.ngb.domain.tenant.constant.TenantStatus;
import dev.ngb.domain.tenant.error.TenantError;
import dev.ngb.util.validate.ValidationErrors;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;
import java.util.regex.Pattern;

@Getter
public class Tenant extends DomainEntity<Long> {

    private static final int NAME_MAX_LENGTH = 255;
    private static final int CODE_MAX_LENGTH = 50;
    private static final Pattern CODE_PATTERN = Pattern.compile("^[A-Z0-9_]+$");

    private String name;
    private String code;
    private String domain;
    private String contact;
    private String description;
    private TenantStatus status;

    private Tenant() {
    }

    public static Tenant reconstruct(
            Long id,
            String name,
            String code,
            String domain,
            String contact,
            String description,
            TenantStatus status,
            Integer lockVersion,
            UUID createdBy,
            UUID updatedBy,
            Instant createdAt,
            Instant updatedAt
    ) {
        Tenant tenant = new Tenant();
        tenant.id = id;
        tenant.lockVersion = lockVersion;
        tenant.createdBy = createdBy;
        tenant.updatedBy = updatedBy;
        tenant.createdAt = createdAt;
        tenant.updatedAt = updatedAt;
        tenant.name = name;
        tenant.code = code;
        tenant.domain = domain;
        tenant.contact = contact;
        tenant.description = description;
        tenant.status = status;
        return tenant;
    }

    public static Tenant create(
            String name,
            String code,
            String domain,
            String contact,
            String description
    ) {
        Tenant tenant = new Tenant();
        tenant.name = name;
        tenant.code = code;
        tenant.domain = domain;
        tenant.contact = contact;
        tenant.description = description;
        tenant.status = TenantStatus.ACTIVE;
        tenant.validate();
        return tenant;
    }

    public void update(
            String name,
            String domain,
            String contact,
            String description
    ) {
        this.name = name;
        this.domain = domain;
        this.contact = contact;
        this.description = description;
        this.validate();
    }

    public void deactivate() {
        if (this.status == TenantStatus.INACTIVE) {
            throw TenantError.INVALID_STATUS.exception();
        }
        this.status = TenantStatus.INACTIVE;
    }

    public void activate() {
        if (this.status == TenantStatus.ACTIVE) {
            throw TenantError.INVALID_STATUS.exception();
        }
        this.status = TenantStatus.ACTIVE;
    }

    private void validate() {
        ValidationErrors errors = new ValidationErrors();

        errors.checkNotBlank(name, "name");
        errors.checkMaxLength(name, NAME_MAX_LENGTH, "name");
        errors.checkNotBlank(code, "code");
        errors.checkMaxLength(code, CODE_MAX_LENGTH, "code");
        errors.checkPattern(code, CODE_PATTERN, "code");
        errors.checkNotBlank(domain, "domain");
        errors.checkNotBlank(contact, "contact");
        errors.checkNotNull(status, "status");

        if (errors.hasErrors()) {
            throw TenantError.INVALID_DATA.exception(errors.getExDetails());
        }
    }
}
