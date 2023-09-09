package com.humanassist.registration.jwt;

import com.humanassist.registration.dao.RegistrationDAO;
import com.humanassist.registration.dao.RegistrationDAOImpl;
import com.humanassist.registration.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class HumanAssistUserDetailsService implements UserDetailsService {

    private RegistrationDAO registrationDAO;

    private final Logger LOGGER = Logger.getLogger(HumanAssistUserDetailsService.class.getCanonicalName());
    @Autowired
    public HumanAssistUserDetailsService(@Qualifier("registrationPostgres") RegistrationDAOImpl registrationDAO) {
        this.registrationDAO = registrationDAO;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        LOGGER.log(Level.INFO, "Fetching userDetails with username ::: " + username);
        return this.registrationDAO.fetchRegistrationDetails(username)
                .map(registration ->
                        User.builder()
                            .username(registration.getLogin().getEmailId())
                            .password(registration.getLogin().getPassword())
                            .roles("USER")
                            .build()
                ).
                orElseThrow(() -> {
                    throw new UsernameNotFoundException("No user with ::: " + username + " exists");
                });
    }
}
