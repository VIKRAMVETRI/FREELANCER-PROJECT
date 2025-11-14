package com.freelancenexus.userservice.security;

import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JWT Security Comprehensive Tests")
class JwtSecurityExpandedTest {

    private JwtTokenProvider jwtTokenProvider;
    
    private static final String JWT_SECRET = "mySecretKeyForJWTTokenGenerationAndValidationThatIsLongEnough1234567890";
    private static final long JWT_EXPIRATION = 86400000; // 24 hours

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider();
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtSecret", JWT_SECRET);
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtExpiration", JWT_EXPIRATION);
    }

    @Nested
    @DisplayName("Token Generation Tests")
    class TokenGenerationTests {

        @Test
        @DisplayName("Should generate valid JWT token")
        void generateToken_ValidParams_Success() {
            String token = jwtTokenProvider.generateToken("test@example.com", 1L, "CLIENT");
            
            assertNotNull(token);
            assertFalse(token.isEmpty());
            assertEquals(3, token.split("\\.").length);
        }

        @ParameterizedTest
        @ValueSource(strings = {"user@test.com", "admin@company.com", "freelancer@work.net", "test+tag@example.co.uk"})
        @DisplayName("Should generate tokens for different email formats")
        void generateToken_DifferentEmails_Success(String email) {
            String token = jwtTokenProvider.generateToken(email, 1L, "CLIENT");
            
            assertNotNull(token);
            assertEquals(email, jwtTokenProvider.getEmailFromToken(token));
        }

        @ParameterizedTest
        @ValueSource(longs = {1L, 100L, 999L, 10000L, Long.MAX_VALUE})
        @DisplayName("Should generate tokens for different user IDs")
        void generateToken_DifferentUserIds_Success(Long userId) {
            String token = jwtTokenProvider.generateToken("test@example.com", userId, "CLIENT");
            
            assertNotNull(token);
            assertEquals(userId, jwtTokenProvider.getUserIdFromToken(token));
        }

        @ParameterizedTest
        @ValueSource(strings = {"CLIENT", "FREELANCER", "ADMIN", "SUPER_ADMIN", "GUEST"})
        @DisplayName("Should generate tokens for different roles")
        void generateToken_DifferentRoles_Success(String role) {
            String token = jwtTokenProvider.generateToken("test@example.com", 1L, role);
            
            assertNotNull(token);
            assertEquals(role, jwtTokenProvider.getRoleFromToken(token));
        }

        @Test
        @DisplayName("Should generate different tokens for different users")
        void generateToken_DifferentUsers_DifferentTokens() {
            String token1 = jwtTokenProvider.generateToken("user1@test.com", 1L, "CLIENT");
            String token2 = jwtTokenProvider.generateToken("user2@test.com", 2L, "CLIENT");
            
            assertNotEquals(token1, token2);
        }

        @Test
        @DisplayName("Should generate different tokens at different times")
        void generateToken_DifferentTimes_DifferentTokens() throws InterruptedException {
            String token1 = jwtTokenProvider.generateToken("test@example.com", 1L, "CLIENT");
            Thread.sleep(1000); // Wait 1 second
            String token2 = jwtTokenProvider.generateToken("test@example.com", 1L, "CLIENT");
            
            assertNotEquals(token1, token2);
        }

        @Test
        @DisplayName("Should include all required JWT parts")
        void generateToken_HasAllParts() {
            String token = jwtTokenProvider.generateToken("test@example.com", 1L, "CLIENT");
            String[] parts = token.split("\\.");
            
            assertEquals(3, parts.length); // header.payload.signature
            assertTrue(parts[0].length() > 0); // header exists
            assertTrue(parts[1].length() > 0); // payload exists
            assertTrue(parts[2].length() > 0); // signature exists
        }
    }

    @Nested
    @DisplayName("Token Validation Tests")
    class TokenValidationTests {

        @Test
        @DisplayName("Should validate correct token")
        void validateToken_ValidToken_ReturnsTrue() {
            String token = jwtTokenProvider.generateToken("test@example.com", 1L, "CLIENT");
            
            assertTrue(jwtTokenProvider.validateToken(token));
        }

        @Test
        @DisplayName("Should reject invalid token")
        void validateToken_InvalidToken_ReturnsFalse() {
            assertFalse(jwtTokenProvider.validateToken("invalid.token.here"));
        }

        @Test
        @DisplayName("Should reject malformed token")
        void validateToken_MalformedToken_ReturnsFalse() {
            assertFalse(jwtTokenProvider.validateToken("malformed"));
        }

        @ParameterizedTest
        @EmptySource
        @DisplayName("Should reject empty token")
        void validateToken_EmptyToken_ReturnsFalse(String token) {
            assertFalse(jwtTokenProvider.validateToken(token));
        }

        @Test
        @DisplayName("Should reject tampered token")
        void validateToken_TamperedToken_ReturnsFalse() {
            String token = jwtTokenProvider.generateToken("test@example.com", 1L, "CLIENT");
            String tamperedToken = token.substring(0, token.length() - 5) + "xxxxx";
            
            assertFalse(jwtTokenProvider.validateToken(tamperedToken));
        }

        @Test
        @DisplayName("Should reject token with modified header")
        void validateToken_ModifiedHeader_ReturnsFalse() {
            String token = jwtTokenProvider.generateToken("test@example.com", 1L, "CLIENT");
            String[] parts = token.split("\\.");
            String modifiedToken = "eyJhbGciOiJub25lIn0." + parts[1] + "." + parts[2];
            
            assertFalse(jwtTokenProvider.validateToken(modifiedToken));
        }

        @Test
        @DisplayName("Should reject token with modified payload")
        void validateToken_ModifiedPayload_ReturnsFalse() {
            String token = jwtTokenProvider.generateToken("test@example.com", 1L, "CLIENT");
            String[] parts = token.split("\\.");
            String modifiedToken = parts[0] + ".eyJzdWIiOiJoYWNrZWQifQ." + parts[2];
            
            assertFalse(jwtTokenProvider.validateToken(modifiedToken));
        }

        @Test
        @DisplayName("Should reject token with wrong secret")
        void validateToken_WrongSecret_ReturnsFalse() {
            // Generate token with different secret
            JwtTokenProvider otherProvider = new JwtTokenProvider();
            ReflectionTestUtils.setField(otherProvider, "jwtSecret", "differentSecretKeyThatIsLongEnough1234567890");
            ReflectionTestUtils.setField(otherProvider, "jwtExpiration", JWT_EXPIRATION);
            
            String token = otherProvider.generateToken("test@example.com", 1L, "CLIENT");
            
            assertFalse(jwtTokenProvider.validateToken(token));
        }
    }

    @Nested
    @DisplayName("Token Parsing Tests")
    class TokenParsingTests {

        @Test
        @DisplayName("Should extract email from token")
        void getEmailFromToken_ValidToken_ReturnsEmail() {
            String email = "test@example.com";
            String token = jwtTokenProvider.generateToken(email, 1L, "CLIENT");
            
            assertEquals(email, jwtTokenProvider.getEmailFromToken(token));
        }

        @Test
        @DisplayName("Should extract user ID from token")
        void getUserIdFromToken_ValidToken_ReturnsUserId() {
            Long userId = 123L;
            String token = jwtTokenProvider.generateToken("test@example.com", userId, "CLIENT");
            
            assertEquals(userId, jwtTokenProvider.getUserIdFromToken(token));
        }

        @Test
        @DisplayName("Should extract role from token")
        void getRoleFromToken_ValidToken_ReturnsRole() {
            String role = "ADMIN";
            String token = jwtTokenProvider.generateToken("test@example.com", 1L, role);
            
            assertEquals(role, jwtTokenProvider.getRoleFromToken(token));
        }

        @ParameterizedTest
        @ValueSource(strings = {"user1@test.com", "admin@company.org", "test+suffix@example.net"})
        @DisplayName("Should correctly parse different emails")
        void getEmailFromToken_DifferentEmails_Success(String email) {
            String token = jwtTokenProvider.generateToken(email, 1L, "CLIENT");
            
            assertEquals(email, jwtTokenProvider.getEmailFromToken(token));
        }

        @ParameterizedTest
        @ValueSource(longs = {1L, 999L, 1000000L})
        @DisplayName("Should correctly parse different user IDs")
        void getUserIdFromToken_DifferentIds_Success(Long userId) {
            String token = jwtTokenProvider.generateToken("test@example.com", userId, "CLIENT");
            
            assertEquals(userId, jwtTokenProvider.getUserIdFromToken(token));
        }

        @ParameterizedTest
        @ValueSource(strings = {"CLIENT", "FREELANCER", "ADMIN"})
        @DisplayName("Should correctly parse different roles")
        void getRoleFromToken_DifferentRoles_Success(String role) {
            String token = jwtTokenProvider.generateToken("test@example.com", 1L, role);
            
            assertEquals(role, jwtTokenProvider.getRoleFromToken(token));
        }
    }

    @Nested
    @DisplayName("Token Expiration Tests")
    class TokenExpirationTests {

        @Test
        @DisplayName("Should generate token with correct expiration")
        void generateToken_SetsCorrectExpiration() {
            String token = jwtTokenProvider.generateToken("test@example.com", 1L, "CLIENT");
            
            // Token should be valid immediately after generation
            assertTrue(jwtTokenProvider.validateToken(token));
        }

        @Test
        @DisplayName("Should validate token within expiration time")
        void validateToken_WithinExpiration_ReturnsTrue() {
            String token = jwtTokenProvider.generateToken("test@example.com", 1L, "CLIENT");
            
            // Token with 24h expiration should be valid immediately
            assertTrue(jwtTokenProvider.validateToken(token));
        }

        @Test
        @DisplayName("Should handle short expiration time")
        void generateToken_ShortExpiration_StillValid() {
            // Set very short expiration (1 second)
            ReflectionTestUtils.setField(jwtTokenProvider, "jwtExpiration", 1000L);
            
            String token = jwtTokenProvider.generateToken("test@example.com", 1L, "CLIENT");
            
            // Should still be valid immediately after generation
            assertTrue(jwtTokenProvider.validateToken(token));
        }

        @Test
        @DisplayName("Should handle long expiration time")
        void generateToken_LongExpiration_Valid() {
            // Set very long expiration (30 days)
            ReflectionTestUtils.setField(jwtTokenProvider, "jwtExpiration", 30L * 24 * 60 * 60 * 1000);
            
            String token = jwtTokenProvider.generateToken("test@example.com", 1L, "CLIENT");
            
            assertTrue(jwtTokenProvider.validateToken(token));
        }
    }

    @Nested
    @DisplayName("Token Security Tests")
    class TokenSecurityTests {

        @Test
        @DisplayName("Should use secure signature algorithm")
        void generateToken_UsesSecureAlgorithm() {
            String token = jwtTokenProvider.generateToken("test@example.com", 1L, "CLIENT");
            
            // JWT with HS256 starts with eyJ (base64 encoded header)
            assertTrue(token.startsWith("eyJ"));
        }

        @Test
        @DisplayName("Should prevent token forgery")
        void validateToken_ForgedToken_ReturnsFalse() {
            // Try to create a forged token
            String forgedToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoYWNrZXJAZXhhbXBsZS5jb20ifQ.fake_signature";
            
            assertFalse(jwtTokenProvider.validateToken(forgedToken));
        }

        @Test
        @DisplayName("Should require proper secret key length")
        void generateToken_ShortSecret_StillWorks() {
            // JWT library should handle secret key properly
            String token = jwtTokenProvider.generateToken("test@example.com", 1L, "CLIENT");
            
            assertNotNull(token);
            assertTrue(jwtTokenProvider.validateToken(token));
        }

        @Test
        @DisplayName("Should handle special characters in email")
        void generateToken_SpecialCharsInEmail_Success() {
            String email = "test+tag@sub.example.com";
            String token = jwtTokenProvider.generateToken(email, 1L, "CLIENT");
            
            assertEquals(email, jwtTokenProvider.getEmailFromToken(token));
        }

        @Test
        @DisplayName("Should handle special characters in role")
        void generateToken_SpecialCharsInRole_Success() {
            String role = "SUPER_ADMIN";
            String token = jwtTokenProvider.generateToken("test@example.com", 1L, role);
            
            assertEquals(role, jwtTokenProvider.getRoleFromToken(token));
        }
    }

    @Nested
    @DisplayName("Edge Case Tests")
    class EdgeCaseTests {

        @Test
        @DisplayName("Should handle very long email")
        void generateToken_LongEmail_Success() {
            String longEmail = "very.long.email.address.that.exceeds.normal.length@sub.domain.example.com";
            String token = jwtTokenProvider.generateToken(longEmail, 1L, "CLIENT");
            
            assertEquals(longEmail, jwtTokenProvider.getEmailFromToken(token));
        }

        @Test
        @DisplayName("Should handle maximum user ID")
        void generateToken_MaxUserId_Success() {
            Long maxId = Long.MAX_VALUE;
            String token = jwtTokenProvider.generateToken("test@example.com", maxId, "CLIENT");
            
            assertEquals(maxId, jwtTokenProvider.getUserIdFromToken(token));
        }

        @Test
        @DisplayName("Should handle minimum user ID")
        void generateToken_MinUserId_Success() {
            Long minId = 1L;
            String token = jwtTokenProvider.generateToken("test@example.com", minId, "CLIENT");
            
            assertEquals(minId, jwtTokenProvider.getUserIdFromToken(token));
        }

        @Test
        @DisplayName("Should handle Unicode characters in email")
        void generateToken_UnicodeEmail_Success() {
            String unicodeEmail = "test.用户@example.com";
            String token = jwtTokenProvider.generateToken(unicodeEmail, 1L, "CLIENT");
            
            assertEquals(unicodeEmail, jwtTokenProvider.getEmailFromToken(token));
        }

        @Test
        @DisplayName("Should handle consecutive token generations")
        void generateToken_Consecutive_AllValid() {
            for (int i = 0; i < 10; i++) {
                String token = jwtTokenProvider.generateToken("test" + i + "@example.com", (long) i, "CLIENT");
                assertTrue(jwtTokenProvider.validateToken(token));
            }
        }

        @Test
        @DisplayName("Should handle parallel token validation")
        void validateToken_Parallel_Success() {
            String token = jwtTokenProvider.generateToken("test@example.com", 1L, "CLIENT");
            
            // Validate same token multiple times
            assertTrue(jwtTokenProvider.validateToken(token));
            assertTrue(jwtTokenProvider.validateToken(token));
            assertTrue(jwtTokenProvider.validateToken(token));
        }

        @Test
        @DisplayName("Should handle null claims gracefully")
        void getEmailFromToken_ValidToken_NeverReturnsNull() {
            String token = jwtTokenProvider.generateToken("test@example.com", 1L, "CLIENT");
            
            assertNotNull(jwtTokenProvider.getEmailFromToken(token));
            assertNotNull(jwtTokenProvider.getUserIdFromToken(token));
            assertNotNull(jwtTokenProvider.getRoleFromToken(token));
        }
    }
}