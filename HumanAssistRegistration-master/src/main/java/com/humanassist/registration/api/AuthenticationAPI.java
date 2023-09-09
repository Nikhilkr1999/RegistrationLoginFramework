package com.humanassist.registration.api;

import com.humanassist.registration.domain.JWTClaims;
import com.humanassist.registration.domain.Login;
import com.humanassist.registration.domain.Registration;
import com.humanassist.registration.exceptions.NotFoundException;
import com.humanassist.registration.jwt.HumanAssistUserDetailsService;
import com.humanassist.registration.jwt.JWTUtil;
import com.humanassist.registration.service.RegistrationService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping
public class AuthenticationAPI {

    private final Logger LOGGER = Logger.getLogger(AuthenticationAPI.class.getCanonicalName());
    private AuthenticationManager authenticationManager;
    private JWTUtil jwtUtil;
    private RegistrationService registrationService;
    @Autowired
    public AuthenticationAPI(AuthenticationManager authenticationManager, JWTUtil jwtUtil, RegistrationService registrationService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.registrationService = registrationService;
    }

    @PostMapping("/login")
    public String authenticate(@RequestBody Login loginDetails) throws Exception {
        authenticate(loginDetails.getEmailId(), loginDetails.getPassword());
         Registration userDetails = registrationService.fetchRegistrationDetails(loginDetails.getEmailId()).orElseThrow(() ->{
             throw new NotFoundException("Could not find RegistrationDetails with emailId ::: " + loginDetails.getEmailId());
         });
        return jwtUtil.generateToken(userDetails);
    }

    @PostMapping("/validate")
    public JWTClaims validateJWT(@RequestBody String jwt) {
        LOGGER.log(Level.INFO, "Going to validate JWT....");
        Claims claims = jwtUtil.getClaims(jwt);
        LOGGER.log(Level.INFO, "Claim details ::: " + claims.get("schema_name").toString() +", " + claims.get("owner_email_id").toString());
        return new JWTClaims(claims.get("schema_name").toString(), claims.get("owner_email_id").toString());
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            LOGGER.log(Level.SEVERE, "Invalid userName/password ::: " + username + ",  " + password + " " + e.getMessage());
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
