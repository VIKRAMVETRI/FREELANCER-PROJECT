package com.freelancenexus.userservice.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserRoleTest {

    @Test
    void shouldHaveThreeRoles() {
        UserRole[] roles = UserRole.values();
        assertEquals(3, roles.length);
    }

    @Test
    void shouldContainAdminRole() {
        assertTrue(containsRole(UserRole.ADMIN));
    }

    @Test
    void shouldContainClientRole() {
        assertTrue(containsRole(UserRole.CLIENT));
    }

    @Test
    void shouldContainFreelancerRole() {
        assertTrue(containsRole(UserRole.FREELANCER));
    }

    @Test
    void shouldConvertStringToEnum() {
        assertEquals(UserRole.ADMIN, UserRole.valueOf("ADMIN"));
        assertEquals(UserRole.CLIENT, UserRole.valueOf("CLIENT"));
        assertEquals(UserRole.FREELANCER, UserRole.valueOf("FREELANCER"));
    }

    @Test
    void shouldReturnCorrectName() {
        assertEquals("ADMIN", UserRole.ADMIN.name());
        assertEquals("CLIENT", UserRole.CLIENT.name());
        assertEquals("FREELANCER", UserRole.FREELANCER.name());
    }

    private boolean containsRole(UserRole role) {
        for (UserRole r : UserRole.values()) {
            if (r == role) {
                return true;
            }
        }
        return false;
    }
}