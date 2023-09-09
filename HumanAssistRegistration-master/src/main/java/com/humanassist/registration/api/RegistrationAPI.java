package com.humanassist.registration.api;

import com.humanassist.registration.domain.Login;
import com.humanassist.registration.domain.Registration;
import com.humanassist.registration.exceptions.AlreadyRegisteredException;
import com.humanassist.registration.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@RequestMapping("api/v1/registration")
@RestController
public class RegistrationAPI {

    private RegistrationService registrationService;
    private final Logger LOGGER = Logger.getLogger(RegistrationAPI.class.getCanonicalName());
    @Autowired
    public RegistrationAPI(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public String registerUser(@RequestBody Registration registration) {
        LOGGER.log(Level.INFO, "Going to register user with details ::: " + registration);
        try {
            return this.registrationService.registerUser(registration);
        } catch (AlreadyRegisteredException e) {
            return e.getMessage();
        }
    }

    @PutMapping
    public String updatePassword(@RequestBody Login loginDetails) {
        return this.registrationService.updatePassword(loginDetails);
    }
}
