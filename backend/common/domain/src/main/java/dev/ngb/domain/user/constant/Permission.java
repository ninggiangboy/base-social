package dev.ngb.domain.user.constant;

import dev.ngb.util.BitmaskOperationUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public enum Permission {
    // User Management
    USER_READ(1L << 0, "View users"),
    USER_WRITE(1L << 1, "Create/Edit users"),
    USER_DELETE(1L << 2, "Delete users"),

    // Role Management
    ROLE_READ(1L << 3, "View roles"),
    ROLE_WRITE(1L << 4, "Create/Edit roles"),
    ROLE_DELETE(1L << 5, "Delete roles"),

    // System
    SUPER_SYSTEM_ADMIN(1L << 60, "Super System Administrator");

    private final long mask;
    private final String description;

    public static long all() {
        long all = 0L;
        for (Permission p : values()) {
            all = BitmaskOperationUtils.set(all, p.mask);
        }
        return all;
    }

    public static Optional<Permission> fromMask(long mask) {
        for (Permission p : values()) {
            if (p.mask == mask) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    public static EnumSet<Permission> fromPermissions(long mask) {
        EnumSet<Permission> result = EnumSet.noneOf(Permission.class);
        for (Permission p : values()) {
            if (BitmaskOperationUtils.isSet(mask, p.mask)) {
                result.add(p);
            }
        }
        return result;
    }
}
