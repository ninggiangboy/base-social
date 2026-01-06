package dev.ngb.system_admin.tenant.application.command.managemant;

import dev.ngb.application.command.Command;
import dev.ngb.application.command.IdResult;

public record CreateTenantCommand(
        String code,
        String name,
        String description,
        String domain,
        String contact
) implements Command<IdResult<Long>> {
}