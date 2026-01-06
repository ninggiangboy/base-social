package dev.ngb.domain.user.repository;

import dev.ngb.domain.Repository;
import dev.ngb.domain.user.model.Role;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends Repository<Role, UUID> {
    Optional<Role> findByName(String name);
    boolean existsByName(String name);
}
