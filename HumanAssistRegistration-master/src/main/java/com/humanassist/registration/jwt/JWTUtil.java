package com.humanassist.registration.jwt;

import com.humanassist.registration.domain.Registration;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTUtil {

    @Autowired
    private SecretKey secretKey;

    public String generateToken(Registration userDetails) {
        Claims claims = Jwts.claims().setSubject(userDetails.getLogin().getEmailId())
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000)) // 1 hour expiration
                        .setId("kk");
        claims.put("schema_name", userDetails.getSchemaName());
        claims.put("owner_email_id", userDetails.getLogin().getEmailId());
        return Jwts.builder()
                .setClaims(claims)
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {

        return getAllClaimsFromToken(token).getSubject().equals(userDetails.getUsername());
    }

    public String extractUsername(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public String getTenantId(String token) {
        return getAllClaimsFromToken(token).getId();
    }

    public Claims getClaims(String token) {
        return getAllClaimsFromToken(token);
    }
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token).getBody();
    }
}
