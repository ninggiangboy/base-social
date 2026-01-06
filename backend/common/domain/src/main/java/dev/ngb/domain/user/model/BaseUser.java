package dev.ngb.domain.user.model;

import dev.ngb.domain.DomainEntity;
import dev.ngb.domain.DomainException;
import dev.ngb.domain.user.error.UserError;
import dev.ngb.util.validate.ValidationErrors;
import lombok.Getter;

import java.time.Instant;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Getter
public abstract class BaseUser extends DomainEntity<UUID> {

    private static final int LOGIN_ID_MAX_LENGTH = 50;
    private static final int EMAIL_MAX_LENGTH = 255;
    private static final int DISPLAY_NAME_MAX_LENGTH = 255;

    protected String loginId;
    protected String email;
    protected String username;
    protected String displayName;
    protected String hashedPassword;
    protected Set<UUID> roleIds = new HashSet<>();
    protected Instant lastLoginAt;
    protected Instant firstLoginAt;

    // Default permissions for specific user type
    public abstract long getDefaultPermissions();

    protected void recordLoginTime() {
        Instant now = Instant.now(clock);
        if (this.firstLoginAt == null) {
            this.firstLoginAt = now;
        }
        this.lastLoginAt = now;
    }

    public Map<String, Object> getAuthClaims() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", this.email);
        claims.put("username", this.username);
        claims.put("displayName", this.displayName);
        return claims;
    }

    public void addRole(Role role) {
        this.roleIds.add(role.getId());
    }

    public void removeRole(Role role) {
        this.roleIds.remove(role.getId());
    }

    public void setRoles(Set<UUID> roleIds) {
        this.roleIds = roleIds != null ? roleIds : new HashSet<>();
    }

    public long calculateEffectivePermissions(List<Role> roles) {
        long permissions = getDefaultPermissions();
        if (roles == null) return permissions;

        for (Role role : roles) {
            if (this.roleIds.contains(role.getId())) {
                permissions |= role.getPermissions();
            }
        }
        return permissions;
    }

    protected void validateBase() {
        ValidationErrors errors = new ValidationErrors();

        errors.checkNotBlank(loginId, "loginId");
        errors.checkMaxLength(loginId, LOGIN_ID_MAX_LENGTH, "loginId");
        errors.checkNotBlank(email, "email");
        errors.checkMaxLength(email, EMAIL_MAX_LENGTH, "email");
        errors.checkEmail(email, "email");
        errors.checkNotBlank(displayName, "displayName");
        errors.checkMaxLength(displayName, DISPLAY_NAME_MAX_LENGTH, "displayName");

        if (hashedPassword != null) {
            errors.checkNotBlank(hashedPassword, "hashedPassword");
        }

        if (errors.hasErrors()) {
            throw new DomainException(UserError.INVALID_DATA, Map.of("errors", errors.getErrors()));
        }
    }
}
