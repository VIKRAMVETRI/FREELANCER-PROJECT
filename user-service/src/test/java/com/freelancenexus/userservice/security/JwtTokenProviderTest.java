package com.freelancenexus.userservice.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;
    private String jwtSecret = "test-secret-key-for-testing-purposes-must-be-at-least-256-bits-long";
    private long jwtExpiration = 86400000L; // 24 hours

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider();
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtSecret", jwtSecret);
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtExpiration", jwtExpiration);
    }

    @Test
    void shouldGenerateTokenSuccessfully() {
        String email = "test@example.com";
        Long userId = 1L;
        String role = "CLIENT";

        String token = jwtTokenProvider.generateToken(email, userId, role);

        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    void shouldExtractEmailFromToken() {
        String email = "test@example.com";
        Long userId = 1L;
        String role = "CLIENT";

        String token = jwtTokenProvider.generateToken(email, userId, role);
        String extractedEmail = jwtTokenProvider.getEmailFromToken(token);

        assertEquals(email, extractedEmail);
    }

    @Test
    void shouldExtractUserIdFromToken() {
        String email = "test@example.com";
        Long userId = 123L;
        String role = "CLIENT";

        String token = jwtTokenProvider.generateToken(email, userId, role);
        Long extractedUserId = jwtTokenProvider.getUserIdFromToken(token);

        assertEquals(userId, extractedUserId);
    }

    @Test
    void shouldExtractRoleFromToken() {
        String email = "test@example.com";
        Long userId = 1L;
        String role = "ADMIN";

        String token = jwtTokenProvider.generateToken(email, userId, role);
        String extractedRole = jwtTokenProvider.getRoleFromToken(token);

        assertEquals(role, extractedRole);
    }

    @Test
    void shouldValidateValidToken() {
        String email = "test@example.com";
        Long userId = 1L;
        String role = "CLIENT";

        String token = jwtTokenProvider.generateToken(email, userId, role);
        boolean isValid = jwtTokenProvider.validateToken(token);

        assertTrue(isValid);
    }

    @Test
    void shouldRejectInvalidToken() {
        String invalidToken = "invalid.token.here";

        boolean isValid = jwtTokenProvider.validateToken(invalidToken);

        assertFalse(isValid);
    }

    @Test
    void shouldRejectExpiredToken() {
        // Create an expired token
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        String expiredToken = Jwts.builder()
                .setSubject("test@example.com")
                .claim("userId", 1L)
                .claim("role", "CLIENT")
                .setIssuedAt(new Date(System.currentTimeMillis() - 100000))
                .setExpiration(new Date(System.currentTimeMillis() - 10000))
                .signWith(key)
                .compact();

        boolean isValid = jwtTokenProvider.validateToken(expiredToken);

        assertFalse(isValid);
    }

    @Test
    void shouldRejectTamperedToken() {
        String email = "test@example.com";
        Long userId = 1L;
        String role = "CLIENT";

        String token = jwtTokenProvider.generateToken(email, userId, role);
        String tamperedToken = token.substring(0, token.length() - 5) + "XXXXX";

        boolean isValid = jwtTokenProvider.validateToken(tamperedToken);

        assertFalse(isValid);
    }

    @Test
    void shouldRejectNullToken() {
        boolean isValid = jwtTokenProvider.validateToken(null);

        assertFalse(isValid);
    }

    @Test
    void shouldRejectEmptyToken() {
        boolean isValid = jwtTokenProvider.validateToken("");

        assertFalse(isValid);
    }

    @Test
    void shouldGenerateTokenWithDifferentRoles() {
        String[] roles = {"ADMIN", "CLIENT", "FREELANCER"};

        for (String role : roles) {
            String token = jwtTokenProvider.generateToken("user@example.com", 1L, role);
            String extractedRole = jwtTokenProvider.getRoleFromToken(token);
            assertEquals(role, extractedRole);
        }
    }

    @Test
    void shouldGenerateTokenWithDifferentUserIds() {
        Long[] userIds = {1L, 100L, 999L, 123456L};

        for (Long userId : userIds) {
            String token = jwtTokenProvider.generateToken("user@example.com", userId, "CLIENT");
            Long extractedUserId = jwtTokenProvider.getUserIdFromToken(token);
            assertEquals(userId, extractedUserId);
        }
    }

    @Test
    void shouldGenerateTokenWithDifferentEmails() {
        String[] emails = {"test@example.com", "admin@company.com", "user123@domain.org"};

        for (String email : emails) {
            String token = jwtTokenProvider.generateToken(email, 1L, "CLIENT");
            String extractedEmail = jwtTokenProvider.getEmailFromToken(token);
            assertEquals(email, extractedEmail);
        }
    }
}