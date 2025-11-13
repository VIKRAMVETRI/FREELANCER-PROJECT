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

class UserUpdateDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldCreateValidUserUpdateDTO() {
        UserUpdateDTO dto = new UserUpdateDTO("Updated Name", "0987654321", "http://newimage.url");

        Set<ConstraintViolation<UserUpdateDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldAllowAllNullFields() {
        UserUpdateDTO dto = new UserUpdateDTO(null, null, null);

        Set<ConstraintViolation<UserUpdateDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldFailValidationWithTooShortFullName() {
        UserUpdateDTO dto = new UserUpdateDTO("A", "1234567890", null);

        Set<ConstraintViolation<UserUpdateDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldFailValidationWithTooLongPhone() {
        UserUpdateDTO dto = new UserUpdateDTO("Test User", "123456789012345678901", null);

        Set<ConstraintViolation<UserUpdateDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldTestGettersAndSetters() {
        UserUpdateDTO dto = new UserUpdateDTO();
        dto.setFullName("New Name");
        dto.setPhone("9876543210");
        dto.setProfileImageUrl("http://image.url");

        assertEquals("New Name", dto.getFullName());
        assertEquals("9876543210", dto.getPhone());
        assertEquals("http://image.url", dto.getProfileImageUrl());
    }
}
