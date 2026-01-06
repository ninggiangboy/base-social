package dev.ngb.infrastructure.multi_tenancy.datasource;

import dev.ngb.application.port.TenantContext;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class MultiTenantDataSourceConfig {

    @Bean
    public DataSource dataSource(DataSourceProperties props, TenantContext tenantContext) {
        DataSource ds = props.initializeDataSourceBuilder().build();
        return new MultiTenantSchemaAwareDataSource(ds, tenantContext);
    }
}
