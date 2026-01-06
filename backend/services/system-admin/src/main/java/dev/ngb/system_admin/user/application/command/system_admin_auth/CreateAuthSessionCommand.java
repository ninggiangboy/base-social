package dev.ngb.system_admin.user.application.command.system_admin_auth;

import dev.ngb.application.command.Command;
import dev.ngb.system_admin.user.application.dto.AuthSession;

public record CreateAuthSessionCommand(
        String loginId,
        String password
) implements Command<AuthSession> {
}
