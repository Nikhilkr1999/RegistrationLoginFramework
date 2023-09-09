package com.humanassist.registration.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
public class HumanAssistJWTConfig {
    private Logger logger = Logger.getLogger(HumanAssistJWTConfig.class.getCanonicalName());
    @Bean
    public SecretKey secretKey() {
        logger.log(Level.INFO, "Generating secretKey for JWT generation...");
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }
}
