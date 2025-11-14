package com.freelancenexus.userservice.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    @Test
    void user_GettersAndSetters() {
        // Arrange
        User user = new User();
        LocalDateTime now = LocalDateTime.now();

        // Act
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");
        user.setFullName("Test User");
        user.setPhone("1234567890");
        user.setRole(UserRole.CLIENT);
        user.setIsActive(true);
        user.setProfileImageUrl("http://example.com/image.jpg");
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        // Assert
        assertEquals(1L, user.getId());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("encodedPassword", user.getPassword());
        assertEquals("Test User", user.getFullName());
        assertEquals("1234567890", user.getPhone());
        assertEquals(UserRole.CLIENT, user.getRole());
        assertTrue(user.getIsActive());
        assertEquals("http://example.com/image.jpg", user.getProfileImageUrl());
        assertEquals(now, user.getCreatedAt());
        assertEquals(now, user.getUpdatedAt());
    }

    @Test
    void user_AllArgsConstructor() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();

        // Act
        User user = new User(
            1L,
            "test@example.com",
            "password",
            "Test User",
            "1234567890",
            UserRole.CLIENT,
            true,
            "http://example.com/image.jpg",
            now,
            now
        );

        // Assert
        assertEquals(1L, user.getId());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals("Test User", user.getFullName());
        assertEquals("1234567890", user.getPhone());
        assertEquals(UserRole.CLIENT, user.getRole());
        assertTrue(user.getIsActive());
        assertEquals("http://example.com/image.jpg", user.getProfileImageUrl());
        assertEquals(now, user.getCreatedAt());
        assertEquals(now, user.getUpdatedAt());
    }

    @Test
    void user_NoArgsConstructor() {
        // Act
        User user = new User();

        // Assert
        assertNotNull(user);
        assertNull(user.getId());
        assertNull(user.getEmail());
    }

    @Test
    void user_WithDifferentRoles() {
        // Test ADMIN role
        User adminUser = new User();
        adminUser.setRole(UserRole.ADMIN);
        assertEquals(UserRole.ADMIN, adminUser.getRole());

        // Test CLIENT role
        User clientUser = new User();
        clientUser.setRole(UserRole.CLIENT);
        assertEquals(UserRole.CLIENT, clientUser.getRole());

        // Test FREELANCER role
        User freelancerUser = new User();
        freelancerUser.setRole(UserRole.FREELANCER);
        assertEquals(UserRole.FREELANCER, freelancerUser.getRole());
    }

    @Test
    void user_WithNullableFields() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setFullName("Test User");
        user.setRole(UserRole.CLIENT);
        user.setIsActive(true);
        // phone and profileImageUrl are null

        // Assert
        assertNull(user.getPhone());
        assertNull(user.getProfileImageUrl());
    }

    @Test
    void userRole_HasCorrectValues() {
        // Assert
        assertEquals("ADMIN", UserRole.ADMIN.name());
        assertEquals("CLIENT", UserRole.CLIENT.name());
        assertEquals("FREELANCER", UserRole.FREELANCER.name());
    }

    @Test
    void userRole_ValueOf() {
        // Act & Assert
        assertEquals(UserRole.ADMIN, UserRole.valueOf("ADMIN"));
        assertEquals(UserRole.CLIENT, UserRole.valueOf("CLIENT"));
        assertEquals(UserRole.FREELANCER, UserRole.valueOf("FREELANCER"));
    }

    @Test
    void userRole_Values() {
        // Act
        UserRole[] roles = UserRole.values();

        // Assert
        assertEquals(3, roles.length);
        assertTrue(containsRole(roles, UserRole.ADMIN));
        assertTrue(containsRole(roles, UserRole.CLIENT));
        assertTrue(containsRole(roles, UserRole.FREELANCER));
    }

    @Test
    void user_EqualsAndHashCode() {
        // Arrange
        User user1 = new User();
        user1.setId(1L);
        user1.setEmail("test@example.com");

        User user2 = new User();
        user2.setId(1L);
        user2.setEmail("test@example.com");

        User user3 = new User();
        user3.setId(2L);
        user3.setEmail("other@example.com");

        // Assert
        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
        assertNotEquals(user1, user3);
    }

    @Test
    void user_ToString() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setFullName("Test User");

        // Act
        String toString = user.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("test@example.com"));
    }

    private boolean containsRole(UserRole[] roles, UserRole role) {
        for (UserRole r : roles) {
            if (r == role) {
                return true;
            }
        }
        return false;
    }
}