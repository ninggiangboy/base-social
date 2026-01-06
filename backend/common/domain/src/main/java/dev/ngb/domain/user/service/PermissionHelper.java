package dev.ngb.domain.user.service;

import dev.ngb.domain.user.constant.Permission;
import dev.ngb.util.BitmaskOperationUtils;

public class PermissionHelper {

    private static boolean isSuperSystemAdmin(long permissions) {
        return BitmaskOperationUtils.isSet(
                permissions,
                Permission.SUPER_SYSTEM_ADMIN.getMask()
        );
    }

    public static boolean hasPermission(long currentPermissions, Permission permission) {
        if (isSuperSystemAdmin(currentPermissions)) {
            return true;
        }
        return BitmaskOperationUtils.hasAll(currentPermissions, permission.getMask());
    }

    public static boolean hasPermissions(long currentPermissions, Permission... permissions) {
        if (isSuperSystemAdmin(currentPermissions)) {
            return true;
        }

        for (Permission permission : permissions) {
            if (!BitmaskOperationUtils.hasAll(currentPermissions, permission.getMask())) {
                return false;
            }
        }
        return true;
    }

    public static long addPermission(long currentPermissions, Permission permission) {
        return BitmaskOperationUtils.set(currentPermissions, permission.getMask());
    }

    public static long removePermission(long currentPermissions, Permission permission) {
        return BitmaskOperationUtils.clear(currentPermissions, permission.getMask());
    }
}
