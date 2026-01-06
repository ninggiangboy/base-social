package dev.ngb.system_admin.tenant.application.port;

public interface TenantSchemaMigration {
    void createAndMigrateSchema(Long tenantId);
}
