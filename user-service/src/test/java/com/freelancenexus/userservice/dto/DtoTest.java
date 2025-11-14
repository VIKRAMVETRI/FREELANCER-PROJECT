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

class DTOTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // UserRegistrationDTO Tests
    @Test
    void userRegistrationDTO_ValidData_NoViolations() {
        // Arrange
        UserRegistrationDTO dto = new UserRegistrationDTO();
        dto.setEmail("test@example.com");
        dto.setPassword("password123");
        dto.setFullName("Test User");
        dto.setPhone("1234567890");
        dto.setRole(UserRole.CLIENT);

        // Act
        Set<ConstraintViolation<UserRegistrationDTO>> violations = validator.validate(dto);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void userRegistrationDTO_InvalidEmail_HasViolation() {
        // Arrange
        UserRegistrationDTO dto = new UserRegistrationDTO();
        dto.setEmail("invalid-email");
        dto.setPassword("password123");
        dto.setFullName("Test User");
        dto.setRole(UserRole.CLIENT);

        // Act
        Set<ConstraintViolation<UserRegistrationDTO>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    void userRegistrationDTO_ShortPassword_HasViolation() {
        // Arrange
        UserRegistrationDTO dto = new UserRegistrationDTO();
        dto.setEmail("test@example.com");
        dto.setPassword("short");
        dto.setFullName("Test User");
        dto.setRole(UserRole.CLIENT);

        // Act
        Set<ConstraintViolation<UserRegistrationDTO>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }

    @Test
    void userRegistrationDTO_EmptyFullName_HasViolation() {
        // Arrange
        UserRegistrationDTO dto = new UserRegistrationDTO();
        dto.setEmail("test@example.com");
        dto.setPassword("password123");
        dto.setFullName("");
        dto.setRole(UserRole.CLIENT);

        // Act
        Set<ConstraintViolation<UserRegistrationDTO>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
    }

    @Test
    void userRegistrationDTO_NullRole_HasViolation() {
        // Arrange
        UserRegistrationDTO dto = new UserRegistrationDTO();
        dto.setEmail("test@example.com");
        dto.setPassword("password123");
        dto.setFullName("Test User");
        dto.setRole(null);

        // Act
        Set<ConstraintViolation<UserRegistrationDTO>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("role")));
    }

    // UserLoginDTO Tests
    @Test
    void userLoginDTO_ValidData_NoViolations() {
        // Arrange
        UserLoginDTO dto = new UserLoginDTO();
        dto.setEmail("test@example.com");
        dto.setPassword("password123");

        // Act
        Set<ConstraintViolation<UserLoginDTO>> violations = validator.validate(dto);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void userLoginDTO_InvalidEmail_HasViolation() {
        // Arrange
        UserLoginDTO dto = new UserLoginDTO();
        dto.setEmail("invalid-email");
        dto.setPassword("password123");

        // Act
        Set<ConstraintViolation<UserLoginDTO>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
    }

    @Test
    void userLoginDTO_EmptyPassword_HasViolation() {
        // Arrange
        UserLoginDTO dto = new UserLoginDTO();
        dto.setEmail("test@example.com");
        dto.setPassword("");

        // Act
        Set<ConstraintViolation<UserLoginDTO>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
    }

    // UserUpdateDTO Tests
    @Test
    void userUpdateDTO_ValidData_NoViolations() {
        // Arrange
        UserUpdateDTO dto = new UserUpdateDTO();
        dto.setFullName("Updated Name");
        dto.setPhone("9876543210");
        dto.setProfileImageUrl("http://example.com/image.jpg");

        // Act
        Set<ConstraintViolation<UserUpdateDTO>> violations = validator.validate(dto);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void userUpdateDTO_ShortFullName_HasViolation() {
        // Arrange
        UserUpdateDTO dto = new UserUpdateDTO();
        dto.setFullName("A");

        // Act
        Set<ConstraintViolation<UserUpdateDTO>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
    }

    @Test
    void userUpdateDTO_NullFields_NoViolations() {
        // Arrange
        UserUpdateDTO dto = new UserUpdateDTO();
        // All fields null

        // Act
        Set<ConstraintViolation<UserUpdateDTO>> violations = validator.validate(dto);

        // Assert
        assertTrue(violations.isEmpty());
    }

    // UserResponseDTO Tests
    @Test
    void userResponseDTO_GettersAndSetters() {
        // Arrange
        UserResponseDTO dto = new UserResponseDTO();
        LocalDateTime now = LocalDateTime.now();

        // Act
        dto.setId(1L);
        dto.setEmail("test@example.com");
        dto.setFullName("Test User");
        dto.setPhone("1234567890");
        dto.setRole(UserRole.CLIENT);
        dto.setIsActive(true);
        dto.setProfileImageUrl("http://example.com/image.jpg");
        dto.setCreatedAt(now);
        dto.setUpdatedAt(now);

        // Assert
        assertEquals(1L, dto.getId());
        assertEquals("test@example.com", dto.getEmail());
        assertEquals("Test User", dto.getFullName());
        assertEquals("1234567890", dto.getPhone());
        assertEquals(UserRole.CLIENT, dto.getRole());
        assertTrue(dto.getIsActive());
        assertEquals("http://example.com/image.jpg", dto.getProfileImageUrl());
        assertEquals(now, dto.getCreatedAt());
        assertEquals(now, dto.getUpdatedAt());
    }

    // LoginResponseDTO Tests
    @Test
    void loginResponseDTO_GettersAndSetters() {
        // Arrange
        LoginResponseDTO dto = new LoginResponseDTO();
        UserResponseDTO userDTO = new UserResponseDTO();
        userDTO.setId(1L);
        userDTO.setEmail("test@example.com");

        // Act
        dto.setAccessToken("jwt-token");
        dto.setRefreshToken("refresh-token");
        dto.setExpiresIn(86400L);
        dto.setTokenType("Bearer");
        dto.setUser(userDTO);

        // Assert
        assertEquals("jwt-token", dto.getAccessToken());
        assertEquals("refresh-token", dto.getRefreshToken());
        assertEquals(86400L, dto.getExpiresIn());
        assertEquals("Bearer", dto.getTokenType());
        assertNotNull(dto.getUser());
        assertEquals(1L, dto.getUser().getId());
    }

    @Test
    void loginResponseDTO_DefaultTokenType() {
        // Arrange
        LoginResponseDTO dto = new LoginResponseDTO();

        // Assert
        assertEquals("Bearer", dto.getTokenType());
    }

    @Test
    void loginResponseDTO_AllArgsConstructor() {
        // Arrange
        UserResponseDTO userDTO = new UserResponseDTO();
        userDTO.setId(1L);

        // Act
        LoginResponseDTO dto = new LoginResponseDTO("token", "refresh", 3600L, "Bearer", userDTO);

        // Assert
        assertEquals("token", dto.getAccessToken());
        assertEquals("refresh", dto.getRefreshToken());
        assertEquals(3600L, dto.getExpiresIn());
        assertEquals("Bearer", dto.getTokenType());
        assertEquals(1L, dto.getUser().getId());
    }

    @Test
    void userRegistrationDTO_AllArgsConstructor() {
        // Act
        UserRegistrationDTO dto = new UserRegistrationDTO(
            "test@example.com",
            "password123",
            "Test User",
            "1234567890",
            UserRole.CLIENT,
            "http://example.com/image.jpg"
        );

        // Assert
        assertEquals("test@example.com", dto.getEmail());
        assertEquals("password123", dto.getPassword());
        assertEquals("Test User", dto.getFullName());
        assertEquals("1234567890", dto.getPhone());
        assertEquals(UserRole.CLIENT, dto.getRole());
        assertEquals("http://example.com/image.jpg", dto.getProfileImageUrl());
    }

    @Test
    void userLoginDTO_AllArgsConstructor() {
        // Act
        UserLoginDTO dto = new UserLoginDTO("test@example.com", "password123");

        // Assert
        assertEquals("test@example.com", dto.getEmail());
        assertEquals("password123", dto.getPassword());
    }

    @Test
    void userUpdateDTO_AllArgsConstructor() {
        // Act
        UserUpdateDTO dto = new UserUpdateDTO("Updated Name", "9876543210", "http://example.com/new.jpg");

        // Assert
        assertEquals("Updated Name", dto.getFullName());
        assertEquals("9876543210", dto.getPhone());
        assertEquals("http://example.com/new.jpg", dto.getProfileImageUrl());
    }
}