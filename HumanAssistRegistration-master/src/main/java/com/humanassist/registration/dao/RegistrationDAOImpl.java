package com.humanassist.registration.dao;

import com.humanassist.registration.exceptions.AlreadyRegisteredException;
import com.humanassist.registration.domain.Login;
import com.humanassist.registration.domain.Registration;
import com.humanassist.registration.exceptions.NotFoundException;
import com.humanassist.registration.flyway.FlywayMigrationService;
import com.humanassist.registration.mapper.RegistrationRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository("registrationPostgres")
public class RegistrationDAOImpl implements RegistrationDAO{

    private final Logger LOGGER = Logger.getLogger(RegistrationDAOImpl.class.getCanonicalName());
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private FlywayMigrationService flywayMigrationService;

    public RegistrationDAOImpl(JdbcTemplate jdbcTemplate, FlywayMigrationService flywayMigrationService) {
        this.jdbcTemplate = jdbcTemplate;
        this.flywayMigrationService = flywayMigrationService;
    }
    @Override
    public String registerUser(Registration registrationDetails) throws AlreadyRegisteredException {
        try{
            if(doesEmailIdExists(registrationDetails.getLogin().getEmailId())) {
                throw new AlreadyRegisteredException("User with emailId ::: "+ registrationDetails.getLogin().getEmailId() +" already exists...");
            }
            String schemaName = "ha_" + registrationDetails.getOrgName();
            flywayMigrationService.createSchemaAndMigrateTables(schemaName);
            String query = """
                    Insert Into REGISTRATION_DETAILS (emailId, password, userType, registeredOn, schemaName, orgName)
                    Values(?,?,?,?,?,?);
                """;
            jdbcTemplate.update(query, registrationDetails.getLogin().getEmailId(),
                    new BCryptPasswordEncoder().encode(registrationDetails.getLogin().getPassword()), 0, LocalDateTime.now(), schemaName,
                    registrationDetails.getOrgName());
            return "Successfully Registered";
        } catch (AlreadyRegisteredException ae) {
            throw ae;
        }catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception while registering new user ::: " + e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @Override
    public String updatePassword(Login loginDetails) {
        if(!doesEmailIdExists(loginDetails.getEmailId())) {
            throw new NotFoundException("User does not exists with emailId ::: " + loginDetails.getEmailId());
        }
        String query = """
                   Update REGISTRATION_DETAILS set password = ?, jwtToken = ? where emailId = ?
                """;
        jdbcTemplate.update(query, loginDetails.getPassword(), null, loginDetails.getEmailId());
        return "Successfully updated password with emailId ::: " + loginDetails.getEmailId();
    }

    @Override
    public String login(Login loginDetails) {
        return null;
    }

    @Override
    public Optional<Registration> fetchRegistrationDetails(String emailId) {
        if(!doesEmailIdExists(emailId)) {
            throw new NotFoundException("User does not exists with emailId ::: " + emailId);
        }
        String query = "Select * from REGISTRATION_DETAILS where emailId = ?;";
        try {
            return jdbcTemplate.query(query, new RegistrationRowMapper(), emailId).stream().findFirst();
        } catch (Exception e) {
            String message = e.getMessage();
            e.printStackTrace();
            throw e;
        }

    }

    private Boolean doesEmailIdExists(String emailId) {
        String query = """
                    Select count(*) from REGISTRATION_DETAILS where emailId = ?;
                """;
        Integer count = jdbcTemplate.queryForObject(query, Integer.class, emailId);
        return count >= 1;
    }

}
