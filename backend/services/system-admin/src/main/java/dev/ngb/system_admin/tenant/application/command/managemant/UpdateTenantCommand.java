package dev.ngb.system_admin.tenant.application.command.managemant;

import dev.ngb.application.command.Command;

public record UpdateTenantCommand(
        Long id,
        String name,
        String description,
        String domain,
        String contact
) implements Command<Void> {
}
