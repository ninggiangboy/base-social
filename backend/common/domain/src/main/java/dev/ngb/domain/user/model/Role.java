package dev.ngb.domain.user.model;

import dev.ngb.domain.DomainEntity;
import dev.ngb.domain.user.constant.Permission;
import lombok.Getter;

import java.util.EnumSet;
import java.util.Set;
import java.util.UUID;

@Getter
public class Role extends DomainEntity<UUID> {

    private String name;
    private String description;
    private long permissions; // Bitmask representation of permissions

    public Role(String name, String description) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.permissions = 0;
    }

    // Constructor for reconstruction from persistence layer
    public Role(
            UUID id,
            String name,
            String description,
            long permissions,
            Integer lockVersion,
            UUID createdBy,
            UUID updatedBy,
            java.time.Instant createdAt,
            java.time.Instant updatedAt
    ) {
        reconstruct(id, lockVersion, createdBy, updatedBy, createdAt, updatedAt);
        this.name = name;
        this.description = description;
        this.permissions = permissions;
    }

    protected Role() {
        // For frameworks/persistence
    }

    public void updateInfo(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addPermission(Permission permission) {
        this.permissions |= permission.getMask();
    }

    public void removePermission(Permission permission) {
        this.permissions &= ~permission.getMask();
    }

    public boolean hasPermission(Permission permission) {
        if ((this.permissions & Permission.SUPER_SYSTEM_ADMIN.getMask()) != 0) {
            return true;
        }
        return (this.permissions & permission.getMask()) == permission.getMask();
    }

    public Set<Permission> getPermissionSet() {
        Set<Permission> set = EnumSet.noneOf(Permission.class);
        for (Permission p : Permission.values()) {
            if (hasPermission(p)) { // logic depends on if we want to expand SUPER_SYSTEM_ADMIN or not.
                // If we want exact match:
                if ((this.permissions & p.getMask()) == p.getMask()) {
                    set.add(p);
                }
            }
        }
        return set;
    }
    
    public void setPermissions(Set<Permission> permissions) {
        long mask = 0;
        for (Permission p : permissions) {
            mask |= p.getMask();
        }
        this.permissions = mask;
    }
}
