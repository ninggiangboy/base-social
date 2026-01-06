package dev.ngb.infrastructure.multi_tenancy.cache;

import dev.ngb.application.port.Cache;
import dev.ngb.application.port.TenantContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.function.Supplier;

@Component
@Primary
public class MultiTenantCache implements Cache {

    private final Cache delegate;
    private final TenantContext tenantContext;

    public MultiTenantCache(@Qualifier("redisCache") Cache delegate,
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
    public <V> void put(String key, V value) {
        delegate.put(buildTenantKey(key), value);
    }

    @Override
    public <V> void put(String key, V value, Duration timeout) {
        delegate.put(buildTenantKey(key), value, timeout);
    }

    @Override
    public <V> V get(String key, Supplier<V> callbackIfNull) {
        return delegate.get(buildTenantKey(key), callbackIfNull);
    }

    @Override
    public <V> V get(String key, Supplier<V> callbackIfNull, Duration timeout) {
        return delegate.get(buildTenantKey(key), callbackIfNull, timeout);
    }

    @Override
    public <V> V get(String key) {
        return delegate.get(buildTenantKey(key));
    }

    @Override
    public void evict(String key) {
        delegate.evict(buildTenantKey(key));
    }

    @Override
    public void evictAll(String prefix) {
        delegate.evictAll(buildTenantKey(prefix));
    }
}
