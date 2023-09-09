package com.humanassist.registration.mapper;

import com.humanassist.registration.domain.Login;
import com.humanassist.registration.domain.Registration;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegistrationRowMapper implements RowMapper<Registration> {

    @Override
    public Registration mapRow(ResultSet rs, int rowNum) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        return new Registration(
                new Login(
                        rs.getString("emailId"),
                        rs.getString("password")
                ),
                LocalDateTime.parse(rs.getString("registeredOn"), formatter),
                rs.getInt("userType"),
                rs.getString("schemaName"),
                rs.getString("orgName")
        );
    }
}
