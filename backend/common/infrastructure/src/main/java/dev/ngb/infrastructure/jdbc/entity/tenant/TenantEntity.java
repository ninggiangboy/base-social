package dev.ngb.infrastructure.jdbc.entity.tenant;

import dev.ngb.domain.tenant.constant.TenantStatus;
import dev.ngb.infrastructure.jdbc.base.JdbcEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "tenants", schema = "public")
public class TenantEntity extends JdbcEntity<Long> {
    private String name;
    private String code;
    private String domain;
    private String contact;
    private String description;
    private TenantStatus status;
}
