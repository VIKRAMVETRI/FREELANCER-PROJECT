package com.freelancenexus.userservice.dto;

import com.freelancenexus.userservice.model.User;
import com.freelancenexus.userservice.model.UserRole;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class DtoModelTest {

    @Test
    void testUserRegistrationDTO() {
        UserRegistrationDTO dto = new UserRegistrationDTO("test@example.com", "password123", "John Doe", "1234567890", UserRole.CLIENT, "profile.jpg");
        assertEquals("test@example.com", dto.getEmail());
        assertEquals("John Doe", dto.getFullName());
    }

    @Test
    void testUserLoginDTO() {
        UserLoginDTO dto = new UserLoginDTO("user@example.com", "secret");
        assertEquals("user@example.com", dto.getEmail());
        assertEquals("secret", dto.getPassword());
    }

    @Test
    void testLoginResponseDTO() {
        UserResponseDTO user = new UserResponseDTO(1L, "test@example.com", "John", "9999999999", UserRole.CLIENT, true, null, LocalDateTime.now(), LocalDateTime.now());
        LoginResponseDTO response = new LoginResponseDTO("access", "refresh", 3600L, "Bearer", user);
        assertEquals("access", response.getAccessToken());
        assertEquals(user, response.getUser());
    }

    @Test
    void testUserResponseDTO() {
        LocalDateTime now = LocalDateTime.now();
        UserResponseDTO dto = new UserResponseDTO(1L, "a@b.com", "Alice", "123", UserRole.ADMIN, true, "img.jpg", now, now);
        assertEquals("Alice", dto.getFullName());
        assertEquals(UserRole.ADMIN, dto.getRole());
    }

    @Test
    void testUserUpdateDTO() {
        UserUpdateDTO dto = new UserUpdateDTO("New Name", "9999", "newimg.jpg");
        assertEquals("New Name", dto.getFullName());
        assertEquals("newimg.jpg", dto.getProfileImageUrl());
    }

    @Test
    void testUserModel() {
        LocalDateTime now = LocalDateTime.now();
        User user = new User(1L, "a@b.com", "pass", "Bob", "111", UserRole.FREELANCER, true, "img", now, now);
        assertEquals("Bob", user.getFullName());
        assertEquals(UserRole.FREELANCER, user.getRole());
    }

    @Test
    void testUserRoleEnum() {
        assertEquals("ADMIN", UserRole.ADMIN.name());
        assertEquals("CLIENT", UserRole.CLIENT.name());
        assertEquals("FREELANCER", UserRole.FREELANCER.name());
    }
}
