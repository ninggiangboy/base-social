package dev.ngb.domain.user.model;

import dev.ngb.domain.user.constant.Permission;
import dev.ngb.domain.user.constant.SystemAdminStatus;
import dev.ngb.domain.user.error.UserError;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class SystemAdmin extends BaseUser {

    private SystemAdminStatus status;

    @Override
    public long getDefaultPermissions() {
        return Permission.SUPER_SYSTEM_ADMIN.getMask();
    }

    public static SystemAdmin reconstruct(
            UUID id,
            String loginId,
            String email,
            String username,
            String displayName,
            String hashedPassword,
            Instant lastLoginAt,
            Instant firstLoginAt,
            SystemAdminStatus status,
            Integer lockVersion,
            UUID createdBy,
            UUID updatedBy,
            Instant createdAt,
            Instant updatedAt
    ) {
        SystemAdmin user = new SystemAdmin();
        user.id = id;
        user.lockVersion = lockVersion;
        user.createdBy = createdBy;
        user.updatedBy = updatedBy;
        user.createdAt = createdAt;
        user.updatedAt = updatedAt;
        user.loginId = loginId;
        user.email = email;
        user.username = username;
        user.displayName = displayName;
        user.hashedPassword = hashedPassword;
        user.lastLoginAt = lastLoginAt;
        user.firstLoginAt = firstLoginAt;
        user.status = status;
        return user;
    }

    public void tryLogin() {
        if (status == SystemAdminStatus.DISABLED) {
            throw UserError.USER_DISABLED.exception();
        }
        if (status == SystemAdminStatus.NEED_CHANGE_PASSWORD) {
            throw UserError.NEED_CHANGE_PASSWORD.exception();
        }
        recordLoginTime();
    }

    public static SystemAdmin create(String email, String displayName) {
        SystemAdmin user = new SystemAdmin();
        user.loginId = email.toUpperCase();
        user.email = email;
        user.displayName = displayName;
        user.status = SystemAdminStatus.NEED_CHANGE_PASSWORD;
        return user;
    }
}
