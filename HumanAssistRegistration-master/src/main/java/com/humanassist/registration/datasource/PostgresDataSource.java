package com.humanassist.registration.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.logging.Logger;

@Configuration
public class PostgresDataSource {

    private Logger logger = Logger.getLogger(PostgresDataSource.class.getCanonicalName());

    @Bean
    @ConfigurationProperties("app.flyway-datasource")
    public HikariDataSource hikariDataSource() {
        logger.info("Getting HikariDataSource");
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    //@Bean
   // @ConfigurationProperties("spring.r2dbc")
    public HikariDataSource r2dbcHikariDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }
    @Bean
    public JdbcTemplate jdbcTemplate(HikariDataSource hikariDataSource) {
        logger.info("Getting JdbcTemplate");
        return new JdbcTemplate(hikariDataSource);
    }
}
