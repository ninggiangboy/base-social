package dev.ngb.application.port;

public interface TenantContext {
    void setTenantId(Long tenantId);

    Long getTenantId();

    void clear();
}
