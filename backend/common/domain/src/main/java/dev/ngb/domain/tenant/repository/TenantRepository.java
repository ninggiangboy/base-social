package dev.ngb.domain.tenant.repository;

import dev.ngb.domain.Repository;
import dev.ngb.domain.tenant.model.Tenant;

import java.util.Optional;

public interface TenantRepository extends Repository<Tenant, Long> {
    Optional<Tenant> findByCode(String code);
}
