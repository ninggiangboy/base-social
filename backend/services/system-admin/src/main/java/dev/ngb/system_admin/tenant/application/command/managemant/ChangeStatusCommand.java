package dev.ngb.system_admin.tenant.application.command.managemant;

import dev.ngb.application.command.Command;
import dev.ngb.domain.tenant.constant.TenantStatus;

public record ChangeStatusCommand(
        Long id,
        TenantStatus status
) implements Command<Void> {
}
