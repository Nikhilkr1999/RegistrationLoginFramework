package com.humanassist.registration.flyway;

import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class FlywayMigrationService {
    private final Logger LOGGER = Logger.getLogger(FlywayMigrationService.class.getCanonicalName());
    private HikariDataSource hikariDataSource;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public FlywayMigrationService(HikariDataSource hikariDataSource, JdbcTemplate jdbcTemplate) {
        this.hikariDataSource = hikariDataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createSchemaAndMigrateTables(String schemaName) {
        LOGGER.log(Level.INFO, "Going to create new Schema {0} and migrate tables", schemaName);
        Flyway flyway = Flyway.configure()
                .dataSource(hikariDataSource)
                .schemas(schemaName)
                .locations("filesystem:/Volumes/Personal/Repo/HumanAssistSQLScript")
                .load();

        // Migrate the new schema
        flyway.migrate();
        LOGGER.log(Level.INFO, "Successfully migrated tables to schema {0}", schemaName);
    }

    public void applyMigrationsToAllSchemas() {
        LOGGER.log(Level.INFO, "Flyway migration starting for all the schemas...");
        List<String> existingSchemas = jdbcTemplate.queryForList(
                "SELECT schema_name FROM information_schema.schemata where schema_name like '%ha%'",
                String.class
        );

        for (String schema : existingSchemas) {
            Flyway flyway = Flyway.configure()
                    .dataSource(hikariDataSource)
                    //.dataSource("jdbc:postgresql://localhost:5432/humanassist?currentSchema=" + schema, "postgres", "Radhem@2023")
                    .schemas(schema)
                    .locations("filesystem:/Volumes/Personal/Repo/HumanAssistSQLScript")
                    .load();

            flyway.migrate();
        }
        LOGGER.log(Level.INFO, "Flyway migration completed for all the schemas...");
    }
}
