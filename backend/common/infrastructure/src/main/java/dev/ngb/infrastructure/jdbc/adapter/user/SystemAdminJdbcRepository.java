package dev.ngb.infrastructure.jdbc.adapter.user;

import dev.ngb.domain.user.model.SystemAdmin;
import dev.ngb.domain.user.repository.SystemAdminRepository;
import dev.ngb.infrastructure.jdbc.base.JdbcRepository;
import dev.ngb.infrastructure.jdbc.entity.user.SystemAdminEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class SystemAdminJdbcRepository
        extends JdbcRepository<SystemAdmin, SystemAdminEntity, UUID>
        implements SystemAdminRepository {

    @Override
    public Optional<SystemAdmin> findByLoginId(String loginId) {
        return findOneByField("login_id", loginId);
    }

    @Override
    protected SystemAdmin toDomain(SystemAdminEntity entity) {
        return SystemAdmin.reconstruct(
                entity.getId(),
                entity.getLoginId(),
                entity.getEmail(),
                entity.getUsername(),
                entity.getDisplayName(),
                entity.getHashedPassword(),
                entity.getLastLoginAt(),
                entity.getFirstLoginAt(),
                entity.getStatus(),
                entity.getLockVersion(),
                entity.getCreatedBy(),
                entity.getUpdatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    @Override
    protected SystemAdminEntity toJdbc(SystemAdmin entity) {
        return SystemAdminEntity.builder()
                .id(entity.getId())
                .loginId(entity.getLoginId())
                .email(entity.getEmail())
                .username(entity.getUsername())
                .displayName(entity.getDisplayName())
                .hashedPassword(entity.getHashedPassword())
                .lastLoginAt(entity.getLastLoginAt())
                .firstLoginAt(entity.getFirstLoginAt())
                .status(entity.getStatus())
                .lockVersion(entity.getLockVersion())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
