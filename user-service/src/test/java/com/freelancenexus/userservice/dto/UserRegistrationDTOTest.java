package com.freelancenexus.userservice.dto;

import com.freelancenexus.userservice.model.UserRole;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserRegistrationDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldCreateValidUserRegistrationDTO() {
        UserRegistrationDTO dto = new UserRegistrationDTO(
                "test@example.com",
                "password123",
                "Test User",
                "1234567890",
                UserRole.CLIENT,
                "http://image.url"
        );

        Set<ConstraintViolation<UserRegistrationDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldFailValidationWithBlankEmail() {
        UserRegistrationDTO dto = new UserRegistrationDTO(
                "",
                "password123",
                "Test User",
                "1234567890",
                UserRole.CLIENT,
                null
        );

        Set<ConstraintViolation<UserRegistrationDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldFailValidationWithInvalidEmail() {
        UserRegistrationDTO dto = new UserRegistrationDTO(
                "invalid-email",
                "password123",
                "Test User",
                "1234567890",
                UserRole.CLIENT,
                null
        );

        Set<ConstraintViolation<UserRegistrationDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldFailValidationWithShortPassword() {
        UserRegistrationDTO dto = new UserRegistrationDTO(
                "test@example.com",
                "short",
                "Test User",
                "1234567890",
                UserRole.CLIENT,
                null
        );

        Set<ConstraintViolation<UserRegistrationDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldFailValidationWithBlankFullName() {
        UserRegistrationDTO dto = new UserRegistrationDTO(
                "test@example.com",
                "password123",
                "",
                "1234567890",
                UserRole.CLIENT,
                null
        );

        Set<ConstraintViolation<UserRegistrationDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldFailValidationWithNullRole() {
        UserRegistrationDTO dto = new UserRegistrationDTO(
                "test@example.com",
                "password123",
                "Test User",
                "1234567890",
                null,
                null
        );

        Set<ConstraintViolation<UserRegistrationDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldAllowNullProfileImageUrl() {
        UserRegistrationDTO dto = new UserRegistrationDTO(
                "test@example.com",
                "password123",
                "Test User",
                "1234567890",
                UserRole.CLIENT,
                null
        );

        Set<ConstraintViolation<UserRegistrationDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldTestGettersAndSetters() {
        UserRegistrationDTO dto = new UserRegistrationDTO();
        dto.setEmail("test@example.com");
        dto.setPassword("password123");
        dto.setFullName("Test User");
        dto.setPhone("1234567890");
        dto.setRole(UserRole.ADMIN);
        dto.setProfileImageUrl("http://image.url");

        assertEquals("test@example.com", dto.getEmail());
        assertEquals("password123", dto.getPassword());
        assertEquals("Test User", dto.getFullName());
        assertEquals("1234567890", dto.getPhone());
        assertEquals(UserRole.ADMIN, dto.getRole());
        assertEquals("http://image.url", dto.getProfileImageUrl());
    }
}
