package com.freelancenexus.userservice.repository;

import com.freelancenexus.userservice.model.User;
import com.freelancenexus.userservice.model.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setEmail("test@example.com");
        testUser.setPassword("encodedPassword");
        testUser.setFullName("Test User");
        testUser.setPhone("1234567890");
        testUser.setRole(UserRole.CLIENT);
        testUser.setIsActive(true);
        testUser.setProfileImageUrl("http://example.com/image.jpg");
    }

    @Test
    void findByEmail_ExistingEmail_ReturnsUser() {
        // Arrange
        entityManager.persist(testUser);
        entityManager.flush();

        // Act
        Optional<User> found = userRepository.findByEmail("test@example.com");

        // Assert
        assertTrue(found.isPresent());
        assertEquals("test@example.com", found.get().getEmail());
        assertEquals("Test User", found.get().getFullName());
    }

    @Test
    void findByEmail_NonExistingEmail_ReturnsEmpty() {
        // Act
        Optional<User> found = userRepository.findByEmail("nonexistent@example.com");

        // Assert
        assertFalse(found.isPresent());
    }

    @Test
    void existsByEmail_ExistingEmail_ReturnsTrue() {
        // Arrange
        entityManager.persist(testUser);
        entityManager.flush();

        // Act
        boolean exists = userRepository.existsByEmail("test@example.com");

        // Assert
        assertTrue(exists);
    }

    @Test
    void existsByEmail_NonExistingEmail_ReturnsFalse() {
        // Act
        boolean exists = userRepository.existsByEmail("nonexistent@example.com");

        // Assert
        assertFalse(exists);
    }

    @Test
    void findByRole_ReturnsUsersWithRole() {
        // Arrange
        User clientUser = new User();
        clientUser.setEmail("client@example.com");
        clientUser.setPassword("password");
        clientUser.setFullName("Client User");
        clientUser.setRole(UserRole.CLIENT);
        clientUser.setIsActive(true);

        User freelancerUser = new User();
        freelancerUser.setEmail("freelancer@example.com");
        freelancerUser.setPassword("password");
        freelancerUser.setFullName("Freelancer User");
        freelancerUser.setRole(UserRole.FREELANCER);
        freelancerUser.setIsActive(true);

        entityManager.persist(clientUser);
        entityManager.persist(freelancerUser);
        entityManager.flush();

        // Act
        List<User> clients = userRepository.findByRole(UserRole.CLIENT);
        List<User> freelancers = userRepository.findByRole(UserRole.FREELANCER);

        // Assert
        assertEquals(1, clients.size());
        assertEquals("client@example.com", clients.get(0).getEmail());
        
        assertEquals(1, freelancers.size());
        assertEquals("freelancer@example.com", freelancers.get(0).getEmail());
    }

    @Test
    void findByRole_NoUsersWithRole_ReturnsEmptyList() {
        // Act
        List<User> admins = userRepository.findByRole(UserRole.ADMIN);

        // Assert
        assertTrue(admins.isEmpty());
    }

    @Test
    void findByIsActive_ReturnsActiveUsers() {
        // Arrange
        User activeUser = new User();
        activeUser.setEmail("active@example.com");
        activeUser.setPassword("password");
        activeUser.setFullName("Active User");
        activeUser.setRole(UserRole.CLIENT);
        activeUser.setIsActive(true);

        User inactiveUser = new User();
        inactiveUser.setEmail("inactive@example.com");
        inactiveUser.setPassword("password");
        inactiveUser.setFullName("Inactive User");
        inactiveUser.setRole(UserRole.CLIENT);
        inactiveUser.setIsActive(false);

        entityManager.persist(activeUser);
        entityManager.persist(inactiveUser);
        entityManager.flush();

        // Act
        List<User> activeUsers = userRepository.findByIsActive(true);
        List<User> inactiveUsers = userRepository.findByIsActive(false);

        // Assert
        assertEquals(1, activeUsers.size());
        assertEquals("active@example.com", activeUsers.get(0).getEmail());
        
        assertEquals(1, inactiveUsers.size());
        assertEquals("inactive@example.com", inactiveUsers.get(0).getEmail());
    }

    @Test
    void save_CreatesNewUser() {
        // Act
        User savedUser = userRepository.save(testUser);

        // Assert
        assertNotNull(savedUser.getId());
        assertEquals("test@example.com", savedUser.getEmail());
        assertNotNull(savedUser.getCreatedAt());
        assertNotNull(savedUser.getUpdatedAt());
    }

    @Test
    void save_UpdatesExistingUser() {
        // Arrange
        User savedUser = entityManager.persist(testUser);
        entityManager.flush();
        
        Long userId = savedUser.getId();
        savedUser.setFullName("Updated Name");

        // Act
        User updatedUser = userRepository.save(savedUser);

        // Assert
        assertEquals(userId, updatedUser.getId());
        assertEquals("Updated Name", updatedUser.getFullName());
    }

    @Test
    void findById_ExistingUser_ReturnsUser() {
        // Arrange
        User savedUser = entityManager.persist(testUser);
        entityManager.flush();

        // Act
        Optional<User> found = userRepository.findById(savedUser.getId());

        // Assert
        assertTrue(found.isPresent());
        assertEquals(savedUser.getId(), found.get().getId());
    }

    @Test
    void findById_NonExistingUser_ReturnsEmpty() {
        // Act
        Optional<User> found = userRepository.findById(999L);

        // Assert
        assertFalse(found.isPresent());
    }

    @Test
    void delete_RemovesUser() {
        // Arrange
        User savedUser = entityManager.persist(testUser);
        entityManager.flush();
        Long userId = savedUser.getId();

        // Act
        userRepository.delete(savedUser);
        entityManager.flush();

        // Assert
        Optional<User> found = userRepository.findById(userId);
        assertFalse(found.isPresent());
    }

    @Test
    void findAll_ReturnsAllUsers() {
        // Arrange
        User user1 = new User();
        user1.setEmail("user1@example.com");
        user1.setPassword("password");
        user1.setFullName("User One");
        user1.setRole(UserRole.CLIENT);
        user1.setIsActive(true);

        User user2 = new User();
        user2.setEmail("user2@example.com");
        user2.setPassword("password");
        user2.setFullName("User Two");
        user2.setRole(UserRole.FREELANCER);
        user2.setIsActive(true);

        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.flush();

        // Act
        List<User> users = userRepository.findAll();

        // Assert
        assertEquals(2, users.size());
    }

    @Test
    void user_TimestampsAreAutoGenerated() {
        // Act
        User savedUser = entityManager.persist(testUser);
        entityManager.flush();

        // Assert
        assertNotNull(savedUser.getCreatedAt());
        assertNotNull(savedUser.getUpdatedAt());
    }

    @Test
    void user_EmailIsUnique() {
        // Arrange
        entityManager.persist(testUser);
        entityManager.flush();

        User duplicateUser = new User();
        duplicateUser.setEmail("test@example.com"); // Same email
        duplicateUser.setPassword("password");
        duplicateUser.setFullName("Duplicate User");
        duplicateUser.setRole(UserRole.CLIENT);
        duplicateUser.setIsActive(true);

        // Act & Assert
        assertThrows(Exception.class, () -> {
            entityManager.persist(duplicateUser);
            entityManager.flush();
        });
    }
}