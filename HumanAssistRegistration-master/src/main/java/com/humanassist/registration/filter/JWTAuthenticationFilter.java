package com.humanassist.registration.filter;

import com.humanassist.registration.jwt.HumanAssistUserDetailsService;
import com.humanassist.registration.jwt.JWTUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;
    private final HumanAssistUserDetailsService userDetailsService;
    private Logger logger = Logger.getLogger(JWTAuthenticationFilter.class.getCanonicalName());

    @Autowired
    public JWTAuthenticationFilter(JWTUtil jwtUtil, HumanAssistUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *
     * @param request
     * @param response
     * @param filterChain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.log(Level.INFO, "In JWTAuthenticationFilter...");
        String authorizationHeader = request.getHeader("Authorization");
        String uri = request.getRequestURI();
        logger.log(Level.INFO, "Request URI ::: " + uri);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
            String username = jwtUtil.extractUsername(token);
            Claims claims = jwtUtil.getClaims(token);
            String tenantId = jwtUtil.getTenantId(token);
            logger.log(Level.INFO, "Tenant Id is ::: " + tenantId + ", Schema Name ::: " + claims.get("schema_name") + ", EmailId ::: " + claims.get("owner_email_id"));

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtUtil.validateToken(token, userDetails)) {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
