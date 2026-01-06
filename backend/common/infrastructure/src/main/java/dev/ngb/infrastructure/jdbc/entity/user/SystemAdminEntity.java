package dev.ngb.infrastructure.jdbc.entity.user;

import dev.ngb.domain.user.constant.SystemAdminStatus;
import dev.ngb.infrastructure.jdbc.base.JdbcEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@FieldNameConstants
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "system_admins", schema = "public")
public class SystemAdminEntity extends JdbcEntity<UUID> {
    private String loginId;
    private String email;
    private String username;
    private String displayName;
    private String hashedPassword;
    private Instant lastLoginAt;
    private Instant firstLoginAt;
    private SystemAdminStatus status;
}
