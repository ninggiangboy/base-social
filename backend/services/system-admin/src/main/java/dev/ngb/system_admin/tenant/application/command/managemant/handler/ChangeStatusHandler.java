package dev.ngb.system_admin.tenant.application.command.managemant.handler;

import dev.ngb.application.annonation.TransactionalScope;
import dev.ngb.application.command.CommandHandler;
import dev.ngb.domain.tenant.error.TenantError;
import dev.ngb.domain.tenant.model.Tenant;
import dev.ngb.domain.tenant.repository.TenantRepository;
import dev.ngb.system_admin.tenant.application.command.managemant.ChangeStatusCommand;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@TransactionalScope
public class ChangeStatusHandler implements CommandHandler<ChangeStatusCommand, Void> {

    private final TenantRepository tenantRepository;

    @Override
    public Void handle(ChangeStatusCommand command) {
        Tenant tenant = tenantRepository.findById(command.id())
                .orElseThrow(TenantError.NOT_FOUND::exception);
        switch (command.status()) {
            case ACTIVE -> tenant.activate();
            case INACTIVE -> tenant.deactivate();
        }
        tenantRepository.save(tenant);
        return null;
    }
}
