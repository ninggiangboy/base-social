package dev.ngb.infrastructure.multi_tenancy.context;

import dev.ngb.application.port.TenantContext;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ThreadLocalTenantContext implements TenantContext {

    private static final ThreadLocal<Long> CURRENT_TENANT_ID = new ThreadLocal<>();

    @Override
    public void setTenantId(@Nullable Long tenantId) {
        if (tenantId == null) {
            CURRENT_TENANT_ID.remove();
        } else {
            CURRENT_TENANT_ID.set(tenantId);
        }
    }

    @Override
    public @Nullable Long getTenantId() {
        return CURRENT_TENANT_ID.get();
    }

    @Override
    public void clear() {
        CURRENT_TENANT_ID.remove();
    }
}
