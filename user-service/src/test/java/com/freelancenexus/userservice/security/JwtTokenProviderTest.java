package com.freelancenexus.userservice.security;

import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;
    
    private static final String JWT_SECRET = "mySecretKeyForJWTTokenGenerationAndValidationThatIsLongEnough";
    private static final long JWT_EXPIRATION = 86400000; // 24 hours

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider();
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtSecret", JWT_SECRET);
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtExpiration", JWT_EXPIRATION);
    }

    @Test
    void generateToken_Success() {
        // Arrange
        String email = "test@example.com";
        Long userId = 1L;
        String role = "CLIENT";

        // Act
        String token = jwtTokenProvider.generateToken(email, userId, role);

        // Assert
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.split("\\.").length == 3); // JWT has 3 parts
    }

    @Test
    void getEmailFromToken_Success() {
        // Arrange
        String email = "test@example.com";
        Long userId = 1L;
        String role = "CLIENT";
        String token = jwtTokenProvider.generateToken(email, userId, role);

        // Act
        String extractedEmail = jwtTokenProvider.getEmailFromToken(token);

        // Assert
        assertEquals(email, extractedEmail);
    }

    @Test
    void getUserIdFromToken_Success() {
        // Arrange
        String email = "test@example.com";
        Long userId = 1L;
        String role = "CLIENT";
        String token = jwtTokenProvider.generateToken(email, userId, role);

        // Act
        Long extractedUserId = jwtTokenProvider.getUserIdFromToken(token);

        // Assert
        assertEquals(userId, extractedUserId);
    }

    @Test
    void getRoleFromToken_Success() {
        // Arrange
        String email = "test@example.com";
        Long userId = 1L;
        String role = "CLIENT";
        String token = jwtTokenProvider.generateToken(email, userId, role);

        // Act
        String extractedRole = jwtTokenProvider.getRoleFromToken(token);

        // Assert
        assertEquals(role, extractedRole);
    }

    @Test
    void validateToken_ValidToken_ReturnsTrue() {
        // Arrange
        String email = "test@example.com";
        Long userId = 1L;
        String role = "CLIENT";
        String token = jwtTokenProvider.generateToken(email, userId, role);

        // Act
        boolean isValid = jwtTokenProvider.validateToken(token);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void validateToken_InvalidToken_ReturnsFalse() {
        // Arrange
        String invalidToken = "invalid.jwt.token";

        // Act
        boolean isValid = jwtTokenProvider.validateToken(invalidToken);

        // Assert
        assertFalse(isValid);
    }

    @Test
    void validateToken_MalformedToken_ReturnsFalse() {
        // Arrange
        String malformedToken = "malformed-token";

        // Act
        boolean isValid = jwtTokenProvider.validateToken(malformedToken);

        // Assert
        assertFalse(isValid);
    }

    @Test
    void validateToken_EmptyToken_ReturnsFalse() {
        // Arrange
        String emptyToken = "";

        // Act
        boolean isValid = jwtTokenProvider.validateToken(emptyToken);

        // Assert
        assertFalse(isValid);
    }

    @Test
    void generateToken_WithDifferentRoles() {
        // Test with ADMIN role
        String token1 = jwtTokenProvider.generateToken("admin@example.com", 1L, "ADMIN");
        assertEquals("ADMIN", jwtTokenProvider.getRoleFromToken(token1));

        // Test with CLIENT role
        String token2 = jwtTokenProvider.generateToken("client@example.com", 2L, "CLIENT");
        assertEquals("CLIENT", jwtTokenProvider.getRoleFromToken(token2));

        // Test with FREELANCER role
        String token3 = jwtTokenProvider.generateToken("freelancer@example.com", 3L, "FREELANCER");
        assertEquals("FREELANCER", jwtTokenProvider.getRoleFromToken(token3));
    }

    @Test
    void generateToken_WithDifferentUserIds() {
        // Arrange & Act
        String token1 = jwtTokenProvider.generateToken("user1@example.com", 100L, "CLIENT");
        String token2 = jwtTokenProvider.generateToken("user2@example.com", 200L, "CLIENT");

        // Assert
        assertEquals(100L, jwtTokenProvider.getUserIdFromToken(token1));
        assertEquals(200L, jwtTokenProvider.getUserIdFromToken(token2));
        assertNotEquals(token1, token2);
    }

    @Test
    void validateToken_TamperedToken_ReturnsFalse() {
        // Arrange
        String token = jwtTokenProvider.generateToken("test@example.com", 1L, "CLIENT");
        String tamperedToken = token.substring(0, token.length() - 5) + "aaaaa";

        // Act
        boolean isValid = jwtTokenProvider.validateToken(tamperedToken);

        // Assert
        assertFalse(isValid);
    }
}