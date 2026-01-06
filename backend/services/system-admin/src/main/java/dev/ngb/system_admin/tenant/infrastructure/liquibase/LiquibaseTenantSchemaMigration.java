package dev.ngb.system_admin.tenant.infrastructure.liquibase;

import dev.ngb.system_admin.tenant.application.port.TenantSchemaMigration;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
@Component
@RequiredArgsConstructor
public class LiquibaseTenantSchemaMigration implements TenantSchemaMigration {

    private static final String TENANT_CHANGELOG_PATH = "db/changelog/tenant/db.changelog-master.xml";

    private final DataSource dataSource;

    @Override
    public void createAndMigrateSchema(Long tenantId) {
        String schemaName = tenantId.toString();
        
        log.info("Creating and migrating schema for tenant: {}", tenantId);
        
        try (Connection connection = dataSource.getConnection()) {
            // Create schema if it doesn't exist
            createSchema(connection, schemaName);
            
            // Run Liquibase migrations on the tenant schema
            runMigrations(connection, schemaName);
            
            log.info("Successfully created and migrated schema for tenant: {}", tenantId);
        } catch (SQLException e) {
            log.error("Failed to create and migrate schema for tenant: {}", tenantId, e);
            throw new RuntimeException("Failed to create and migrate schema for tenant: " + tenantId, e);
        }
    }

    private void createSchema(Connection connection, String schemaName) throws SQLException {
        log.debug("Creating schema: {}", schemaName);
        
        try (Statement statement = connection.createStatement()) {
            // Create schema if not exists
            statement.execute(String.format("CREATE SCHEMA IF NOT EXISTS \"%s\"", schemaName));
            log.debug("Schema created or already exists: {}", schemaName);
        }
    }

    private void runMigrations(Connection connection, String schemaName) {
        log.debug("Running Liquibase migrations for schema: {}", schemaName);
        
        try {
            // Set the default schema for this connection
            connection.setSchema(schemaName);
            
            // Create Liquibase database instance
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));
            
            // Set the default schema for Liquibase
            database.setDefaultSchemaName(schemaName);
            database.setLiquibaseSchemaName(schemaName);
            
            // Create Liquibase instance with the tenant changelog
            try (Liquibase liquibase = new Liquibase(
                    TENANT_CHANGELOG_PATH,
                    new ClassLoaderResourceAccessor(),
                    database)) {
                
                // Run the migrations
                liquibase.update(new Contexts(), new LabelExpression());
                
                log.debug("Liquibase migrations completed for schema: {}", schemaName);
            }
        } catch (LiquibaseException | SQLException e) {
            log.error("Failed to run Liquibase migrations for schema: {}", schemaName, e);
            throw new RuntimeException("Failed to run Liquibase migrations for schema: " + schemaName, e);
        }
    }
}
