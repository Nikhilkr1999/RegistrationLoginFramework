package com.humanassist.registration.service;

import com.humanassist.registration.dao.RegistrationDAO;
import com.humanassist.registration.dao.RegistrationDAOImpl;
import com.humanassist.registration.domain.Login;
import com.humanassist.registration.domain.Registration;
import com.humanassist.registration.exceptions.AlreadyRegisteredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class RegistrationService {

    private final RegistrationDAO registrationDAO;

    private final Logger LOGGER = Logger.getLogger(RegistrationService.class.getCanonicalName());

    @Autowired
    public RegistrationService(@Qualifier("registrationPostgres")RegistrationDAOImpl registrationDAO) {
        this.registrationDAO = registrationDAO;
    }

    @Transactional
    public String registerUser(Registration registrationDetails) throws AlreadyRegisteredException {
        return this.registrationDAO.registerUser(registrationDetails);
    }

    public String updatePassword(Login loginDetails) {
        return this.registrationDAO.updatePassword(loginDetails);
    }

    public String login(Login loginDetails) {
        return this.registrationDAO.login(loginDetails);
    }

    public Optional<Registration> fetchRegistrationDetails(String emailId) {
        return this.registrationDAO.fetchRegistrationDetails(emailId);
    }
}
