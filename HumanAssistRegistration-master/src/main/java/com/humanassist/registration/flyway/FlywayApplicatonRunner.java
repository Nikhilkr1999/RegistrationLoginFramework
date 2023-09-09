package com.humanassist.registration.flyway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class FlywayApplicatonRunner implements ApplicationRunner {

    @Autowired
    private FlywayMigrationService flywayMigrationService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        flywayMigrationService.applyMigrationsToAllSchemas();
    }
}
