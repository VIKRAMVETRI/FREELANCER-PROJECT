package com.freelancenexus.userservice.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * JwtAuthenticationFilter
 *
 * <p>Spring Security filter that runs once per request to extract and validate a JWT from
 * the Authorization header. When a valid token is present, the filter sets an
 * authenticated {@link UsernamePasswordAuthenticationToken} into the {@link SecurityContextHolder},
 * allowing downstream security checks to recognize the authenticated user and its role.</p>
 *
 * <p>This filter delegates token parsing and validation to {@link JwtTokenProvider} and is
 * intended for stateless JWT-based authentication in REST APIs.</p>
 *
 * @see OncePerRequestFilter
 * @see JwtTokenProvider
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    /**
     * Provider responsible for parsing, validating and extracting claims from JWTs.
     * Injected by constructor (Lombok {@code @RequiredArgsConstructor}).
     */
    private final JwtTokenProvider jwtTokenProvider;
    
    /**
     * Extracts a JWT from the incoming request, validates it and, if valid,
     * populates the SecurityContext with an authentication token representing
     * the user id and role.
     *
     * <p>On authentication failure this method logs the error but does not short-circuit
     * the filter chain (allowing other handlers to respond).</p>
     *
     * @param request the HTTP servlet request
     * @param response the HTTP servlet response
     * @param filterChain the filter chain
     * @throws ServletException on servlet errors
     * @throws IOException on I/O errors
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);
            
            if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
                Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
                String role = jwtTokenProvider.getRoleFromToken(jwt);
                
                UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(
                        userId, 
                        null, 
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role))
                    );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("Set authentication for user ID: {}", userId);
            }
        } catch (Exception ex) {
            log.error("Could not set user authentication in security context", ex);
        }
        
        filterChain.doFilter(request, response);
    }
    
    /**
     * Retrieves the JWT token string from the Authorization header if present.
     *
     * @param request the HTTP servlet request
     * @return the JWT token without the "Bearer " prefix, or {@code null} if none present
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}