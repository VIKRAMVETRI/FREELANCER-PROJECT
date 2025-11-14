package com.freelancenexus.gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

/**
 * Utility class for handling JSON Web Tokens (JWT).
 * Provides methods for generating, validating, and extracting information from JWT tokens.
 */
@Slf4j
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    /**
     * Generates the signing key used for JWT token signing and verification.
     *
     * @return the signing key
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Extracts all claims from the given JWT token.
     *
     * @param token the JWT token
     * @return the claims extracted from the token
     * @throws RuntimeException if the token is invalid
     */
    public Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()                       // ✅ parser() instead of parserBuilder()
                    .verifyWith(getSigningKey())        // ✅ verifyWith() instead of setSigningKey()
                    .build()
                    .parseSignedClaims(token)           // ✅ parseSignedClaims() instead of parseClaimsJws()
                    .getPayload();                      // ✅ getPayload() instead of getBody()
        } catch (Exception e) {
            log.error("Error extracting claims from token: {}", e.getMessage());
            throw new RuntimeException("Invalid JWT token", e);
        }
    }

    /**
     * Extracts a specific claim from the JWT token using the provided resolver function.
     *
     * @param token the JWT token
     * @param claimsResolver the function to resolve the claim
     * @param <T> the type of the claim
     * @return the resolved claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts the username (subject) from the JWT token.
     *
     * @param token the JWT token
     * @return the username
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts the user ID from the JWT token.
     *
     * @param token the JWT token
     * @return the user ID
     */
    public String extractUserId(String token) {
        return extractClaim(token, claims -> {
            Object userId = claims.get("userId");
            return userId != null ? userId.toString() : null;
        });
    }

    /**
     * Extracts the email from the JWT token.
     *
     * @param token the JWT token
     * @return the email
     */
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts the roles from the JWT token.
     *
     * @param token the JWT token
     * @return a list of roles
     */
    public List<String> extractRoles(String token) {
        return extractClaim(token, claims -> {
            String role = claims.get("role", String.class);
            return role != null ? List.of(role) : List.of();
        });
    }

    /**
     * Extracts the expiration date from the JWT token.
     *
     * @param token the JWT token
     * @return the expiration date
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Checks if the JWT token is expired.
     *
     * @param token the JWT token
     * @return true if the token is expired, false otherwise
     */
    public boolean isTokenExpired(String token) {
        try {
            Date expiration = extractExpiration(token);
            return expiration.before(new Date());
        } catch (Exception e) {
            log.error("Error checking token expiration: {}", e.getMessage());
            return true;
        }
    }

    /**
     * Validates the JWT token by checking its claims and expiration.
     *
     * @param token the JWT token
     * @return true if the token is valid, false otherwise
     */
    public boolean validateToken(String token) {
        try {
            extractAllClaims(token);

            if (isTokenExpired(token)) {
                log.warn("Token is expired");
                return false;
            }

            log.debug("Token validated successfully");
            return true;

        } catch (Exception e) {
            log.error("Token validation failed: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Checks if the JWT token contains the specified role.
     *
     * @param token the JWT token
     * @param role the role to check
     * @return true if the token contains the role, false otherwise
     */
    public boolean hasRole(String token, String role) {
        List<String> roles = extractRoles(token);
        return roles.contains(role);
    }

    /**
     * Checks if the JWT token contains any of the specified roles.
     *
     * @param token the JWT token
     * @param requiredRoles the list of roles to check
     * @return true if the token contains any of the roles, false otherwise
     */
    public boolean hasAnyRole(String token, List<String> requiredRoles) {
        List<String> userRoles = extractRoles(token);
        return requiredRoles.stream().anyMatch(userRoles::contains);
    }
}
