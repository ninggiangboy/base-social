package dev.ngb.infrastructure.jdbc.adapter.tenant;

import dev.ngb.domain.tenant.model.Tenant;
import dev.ngb.domain.tenant.repository.TenantRepository;
import dev.ngb.infrastructure.jdbc.base.JdbcRepository;
import dev.ngb.infrastructure.jdbc.entity.tenant.TenantEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TenantJdbcRepository
        extends JdbcRepository<Tenant, TenantEntity, Long>
        implements TenantRepository {

    @Override
    public Optional<Tenant> findByCode(String code) {
        return findOneByField("code", code);
    }

    @Override
    protected Tenant toDomain(TenantEntity entity) {
        return Tenant.reconstruct(
                entity.getId(),
                entity.getName(),
                entity.getCode(),
                entity.getDomain(),
                entity.getContact(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getLockVersion(),
                entity.getCreatedBy(),
                entity.getUpdatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    @Override
    protected TenantEntity toJdbc(Tenant entity) {
        return TenantEntity.builder()
                .id(entity.getId())
                .name(entity.getName())
                .code(entity.getCode())
                .domain(entity.getDomain())
                .contact(entity.getContact())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .lockVersion(entity.getLockVersion())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
