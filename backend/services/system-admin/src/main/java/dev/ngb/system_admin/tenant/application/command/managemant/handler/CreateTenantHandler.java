package dev.ngb.system_admin.tenant.application.command.managemant.handler;

import dev.ngb.application.annonation.TransactionalScope;
import dev.ngb.application.command.CommandHandler;
import dev.ngb.application.command.IdResult;
import dev.ngb.domain.tenant.error.TenantError;
import dev.ngb.domain.tenant.model.Tenant;
import dev.ngb.domain.tenant.repository.TenantRepository;
import dev.ngb.system_admin.tenant.application.command.managemant.CreateTenantCommand;
import dev.ngb.system_admin.tenant.application.port.TenantSchemaMigration;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@TransactionalScope
public class CreateTenantHandler implements CommandHandler<CreateTenantCommand, IdResult<Long>> {

    private final TenantRepository tenantRepository;
    private final TenantSchemaMigration tenantSchemaMigration;

    @Override
    public IdResult<Long> handle(CreateTenantCommand command) {

        Optional<Tenant> tenantOptByCode = tenantRepository.findByCode(command.code());

        if (tenantOptByCode.isPresent()) {
            throw TenantError.DUPLICATE_CODE.exception(Map.of("existedId", tenantOptByCode.get().getId()));
        }

        Tenant tenant = Tenant.create(
                command.name(),
                command.code(),
                command.domain(),
                command.contact(),
                command.description()
        );

        tenant = tenantRepository.save(tenant);
        tenantSchemaMigration.createAndMigrateSchema(tenant.getId());
        return IdResult.from(tenant);
    }
}
