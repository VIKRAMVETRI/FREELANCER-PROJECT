package com.freelancenexus.userservice.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {
    
    /**
     * Secret used to sign JWTs. Loaded from application configuration (property: jwt.secret).
     * Must be sufficiently long and random for HMAC-SHA algorithms.
     */
    @Value("${jwt.secret}")
    private String jwtSecret;
    
    /**
     * Token expiration duration in milliseconds. Loaded from application configuration
     * (property: jwt.expiration). Used to compute the token's expiry timestamp.
     */
    @Value("${jwt.expiration}")
    private long jwtExpiration;
    
    /**
     * Build and return the SecretKey used to sign and validate JWTs using HMAC-SHA.
     *
     * @return SecretKey derived from configured jwtSecret
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    /**
     * Generate a signed JWT containing the user's email as subject and additional
     * claims for userId and role.
     *
     * @param email the user's email to set as the JWT subject
     * @param userId the user's unique identifier to include as a claim
     * @param role the user's role to include as a claim
     * @return a signed JWT compact string
     */
    public String generateToken(String email, Long userId, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);
        
        return Jwts.builder()
                .setSubject(email)
                .claim("userId", userId)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    
    /**
     * Parse the token and return the subject (email).
     *
     * @param token the JWT compact string
     * @return the email stored as the subject in the token
     * @throws JwtException if the token is invalid or cannot be parsed
     */
    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        
        return claims.getSubject();
    }
    
    /**
     * Parse the token and extract the userId claim.
     *
     * @param token the JWT compact string
     * @return the userId claim as Long
     * @throws JwtException if the token is invalid or cannot be parsed
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        
        return claims.get("userId", Long.class);
    }
    
    /**
     * Parse the token and extract the role claim.
     *
     * @param token the JWT compact string
     * @return the role claim as String
     * @throws JwtException if the token is invalid or cannot be parsed
     */
    public String getRoleFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        
        return claims.get("role", String.class);
    }
    
    /**
     * Validate the provided JWT. Returns true when token is well-formed, signed correctly,
     * and not expired; otherwise logs the reason and returns false.
     *
     * @param token the JWT compact string to validate
     * @return true if the token is valid, false otherwise
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }
}