package dev.ngb.domain.user.repository;

import dev.ngb.domain.Repository;
import dev.ngb.domain.user.model.SystemAdmin;

import java.util.Optional;
import java.util.UUID;

public interface SystemAdminRepository extends Repository<SystemAdmin, UUID> {
    Optional<SystemAdmin> findByLoginId(String loginId);
}
