package com.freelancenexus.userservice.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void shouldCreateUserWithNoArgsConstructor() {
        User user = new User();
        assertNotNull(user);
    }

    @Test
    void shouldCreateUserWithAllArgsConstructor() {
        LocalDateTime now = LocalDateTime.now();
        User user = new User(
                1L,
                "test@example.com",
                "password",
                "Test User",
                "1234567890",
                UserRole.CLIENT,
                true,
                "http://image.url",
                now,
                now
        );

        assertEquals(1L, user.getId());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals("Test User", user.getFullName());
        assertEquals("1234567890", user.getPhone());
        assertEquals(UserRole.CLIENT, user.getRole());
        assertTrue(user.getIsActive());
        assertEquals("http://image.url", user.getProfileImageUrl());
        assertEquals(now, user.getCreatedAt());
        assertEquals(now, user.getUpdatedAt());
    }

    @Test
    void shouldSetAndGetId() {
        User user = new User();
        user.setId(1L);
        assertEquals(1L, user.getId());
    }

    @Test
    void shouldSetAndGetEmail() {
        User user = new User();
        user.setEmail("test@example.com");
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    void shouldSetAndGetPassword() {
        User user = new User();
        user.setPassword("securePassword");
        assertEquals("securePassword", user.getPassword());
    }

    @Test
    void shouldSetAndGetFullName() {
        User user = new User();
        user.setFullName("John Doe");
        assertEquals("John Doe", user.getFullName());
    }

    @Test
    void shouldSetAndGetPhone() {
        User user = new User();
        user.setPhone("9876543210");
        assertEquals("9876543210", user.getPhone());
    }

    @Test
    void shouldSetAndGetRole() {
        User user = new User();
        user.setRole(UserRole.ADMIN);
        assertEquals(UserRole.ADMIN, user.getRole());
    }

    @Test
    void shouldSetAndGetIsActive() {
        User user = new User();
        user.setIsActive(false);
        assertFalse(user.getIsActive());
    }

    @Test
    void shouldSetAndGetProfileImageUrl() {
        User user = new User();
        user.setProfileImageUrl("http://profile.image");
        assertEquals("http://profile.image", user.getProfileImageUrl());
    }

    @Test
    void shouldSetAndGetCreatedAt() {
        User user = new User();
        LocalDateTime now = LocalDateTime.now();
        user.setCreatedAt(now);
        assertEquals(now, user.getCreatedAt());
    }

    @Test
    void shouldSetAndGetUpdatedAt() {
        User user = new User();
        LocalDateTime now = LocalDateTime.now();
        user.setUpdatedAt(now);
        assertEquals(now, user.getUpdatedAt());
    }

    @Test
    void shouldDefaultIsActiveToTrue() {
        User user = new User();
        user.setIsActive(true);
        assertTrue(user.getIsActive());
    }

    @Test
    void shouldTestEqualsAndHashCode() {
        LocalDateTime now = LocalDateTime.now();
        User user1 = new User(1L, "test@example.com", "password", "Test", "123", 
                UserRole.CLIENT, true, null, now, now);
        User user2 = new User(1L, "test@example.com", "password", "Test", "123", 
                UserRole.CLIENT, true, null, now, now);

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void shouldTestToString() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        String toString = user.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("test@example.com"));
    }
}

