package dev.ngb.infrastructure.multi_tenancy.lock;

import dev.ngb.application.port.DistributedLock;
import dev.ngb.application.port.TenantContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@Primary
public class MultiTenantDistributedLock implements DistributedLock {

    private final DistributedLock delegate;
    private final TenantContext tenantContext;

    public MultiTenantDistributedLock(@Qualifier("redisLock") DistributedLock delegate,
                                     TenantContext tenantContext) {
        this.delegate = delegate;
        this.tenantContext = tenantContext;
    }

    private String buildTenantKey(String key) {
        Long tenantId = tenantContext.getTenantId();
        if (tenantId == null) {
            return "g:" + key;
        }
        return "t:" + tenantId + ":" + key;
    }

    @Override
    public boolean tryAcquire(String key, Duration wait, Duration lease) {
        return delegate.tryAcquire(buildTenantKey(key), wait, lease);
    }

    @Override
    public void unlock(String key) {
        delegate.unlock(buildTenantKey(key));
    }
}
