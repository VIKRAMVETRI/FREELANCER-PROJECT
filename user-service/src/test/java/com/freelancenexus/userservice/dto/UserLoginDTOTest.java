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

class UserLoginDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldCreateValidUserLoginDTO() {
        UserLoginDTO dto = new UserLoginDTO("test@example.com", "password123");

        Set<ConstraintViolation<UserLoginDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldFailValidationWithBlankEmail() {
        UserLoginDTO dto = new UserLoginDTO("", "password123");

        Set<ConstraintViolation<UserLoginDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldFailValidationWithInvalidEmail() {
        UserLoginDTO dto = new UserLoginDTO("invalid-email", "password123");

        Set<ConstraintViolation<UserLoginDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldFailValidationWithBlankPassword() {
        UserLoginDTO dto = new UserLoginDTO("test@example.com", "");

        Set<ConstraintViolation<UserLoginDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldTestGettersAndSetters() {
        UserLoginDTO dto = new UserLoginDTO();
        dto.setEmail("test@example.com");
        dto.setPassword("password123");

        assertEquals("test@example.com", dto.getEmail());
        assertEquals("password123", dto.getPassword());
    }

    @Test
    void shouldTestEqualsAndHashCode() {
        UserLoginDTO dto1 = new UserLoginDTO("test@example.com", "password123");
        UserLoginDTO dto2 = new UserLoginDTO("test@example.com", "password123");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
}
