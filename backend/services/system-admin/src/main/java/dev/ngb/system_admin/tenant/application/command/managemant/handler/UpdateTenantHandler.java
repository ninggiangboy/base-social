package dev.ngb.system_admin.tenant.application.command.managemant.handler;

import dev.ngb.application.annonation.TransactionalScope;
import dev.ngb.application.command.CommandHandler;
import dev.ngb.domain.tenant.error.TenantError;
import dev.ngb.domain.tenant.model.Tenant;
import dev.ngb.domain.tenant.repository.TenantRepository;
import dev.ngb.system_admin.tenant.application.command.managemant.UpdateTenantCommand;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@TransactionalScope
public class UpdateTenantHandler implements CommandHandler<UpdateTenantCommand, Void> {

    private final TenantRepository tenantRepository;

    @Override
    public Void handle(UpdateTenantCommand command) {

        Tenant tenant = tenantRepository.findById(command.id())
                .orElseThrow(TenantError.NOT_FOUND::exception);

        tenant.update(
                command.name(),
                command.domain(),
                command.contact(),
                command.description()
        );

        tenantRepository.save(tenant);
        return null;
    }
}
