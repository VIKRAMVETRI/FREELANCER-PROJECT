package com.freelancenexus.userservice.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SecurityConfigTest {

    @Autowired
    private SecurityFilterChain securityFilterChain;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void shouldCreatePasswordEncoder() {
        assertNotNull(passwordEncoder);
        assertTrue(passwordEncoder.encode("password").startsWith("$2a$"));
    }

    @Test
    void shouldEncodePasswordDifferentlyEachTime() {
        String encoded1 = passwordEncoder.encode("password");
        String encoded2 = passwordEncoder.encode("password");

        assertNotEquals(encoded1, encoded2);
    }

    @Test
    void shouldMatchEncodedPassword() {
        String rawPassword = "mySecurePassword";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
    }

    @Test
    void shouldNotMatchIncorrectPassword() {
        String rawPassword = "mySecurePassword";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        assertFalse(passwordEncoder.matches("wrongPassword", encodedPassword));
    }

    @Test
    void shouldLoadSecurityFilterChain() {
        assertNotNull(securityFilterChain);
    }
}
