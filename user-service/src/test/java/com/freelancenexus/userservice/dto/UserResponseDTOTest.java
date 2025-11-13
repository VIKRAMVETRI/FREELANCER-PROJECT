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

class UserResponseDTOTest {

    @Test
    void shouldCreateUserResponseDTO() {
        LocalDateTime now = LocalDateTime.now();
        UserResponseDTO dto = new UserResponseDTO(
                1L,
                "test@example.com",
                "Test User",
                "1234567890",
                UserRole.CLIENT,
                true,
                "http://image.url",
                now,
                now
        );

        assertEquals(1L, dto.getId());
        assertEquals("test@example.com", dto.getEmail());
        assertEquals("Test User", dto.getFullName());
        assertEquals("1234567890", dto.getPhone());
        assertEquals(UserRole.CLIENT, dto.getRole());
        assertTrue(dto.getIsActive());
        assertEquals("http://image.url", dto.getProfileImageUrl());
        assertEquals(now, dto.getCreatedAt());
        assertEquals(now, dto.getUpdatedAt());
    }

    @Test
    void shouldTestGettersAndSetters() {
        UserResponseDTO dto = new UserResponseDTO();
        LocalDateTime now = LocalDateTime.now();

        dto.setId(1L);
        dto.setEmail("test@example.com");
        dto.setFullName("Test User");
        dto.setPhone("1234567890");
        dto.setRole(UserRole.FREELANCER);
        dto.setIsActive(false);
        dto.setProfileImageUrl("http://image.url");
        dto.setCreatedAt(now);
        dto.setUpdatedAt(now);

        assertEquals(1L, dto.getId());
        assertEquals("test@example.com", dto.getEmail());
        assertEquals("Test User", dto.getFullName());
        assertEquals("1234567890", dto.getPhone());
        assertEquals(UserRole.FREELANCER, dto.getRole());
        assertFalse(dto.getIsActive());
        assertEquals("http://image.url", dto.getProfileImageUrl());
        assertEquals(now, dto.getCreatedAt());
        assertEquals(now, dto.getUpdatedAt());
    }

    @Test
    void shouldTestNoArgsConstructor() {
        UserResponseDTO dto = new UserResponseDTO();
        assertNotNull(dto);
    }
}