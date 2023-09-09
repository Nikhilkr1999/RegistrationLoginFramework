package com.humanassist.registration.dao;

import com.humanassist.registration.domain.Login;
import com.humanassist.registration.domain.Registration;
import com.humanassist.registration.exceptions.AlreadyRegisteredException;

import java.util.Optional;

public interface RegistrationDAO {

    public String registerUser(Registration registrationDetails) throws AlreadyRegisteredException;
    public String updatePassword(Login loginDetails);
    public String login(Login loginDetails);
    public Optional<Registration> fetchRegistrationDetails(String emailId);
}
