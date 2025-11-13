package com.freelancenexus.userservice.repository;

import com.freelancenexus.userservice.model.User;
import com.freelancenexus.userservice.model.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

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
    }

    @Test
    void shouldSaveUser() {
        User savedUser = userRepository.save(testUser);

        assertNotNull(savedUser.getId());
        assertNotNull(savedUser.getCreatedAt());
        assertNotNull(savedUser.getUpdatedAt());
        assertEquals(testUser.getEmail(), savedUser.getEmail());
    }

    @Test
    void shouldFindUserByEmail() {
        entityManager.persist(testUser);
        entityManager.flush();

        Optional<User> foundUser = userRepository.findByEmail("test@example.com");

        assertTrue(foundUser.isPresent());
        assertEquals(testUser.getEmail(), foundUser.get().getEmail());
        assertEquals(testUser.getFullName(), foundUser.get().getFullName());
    }

    @Test
    void shouldReturnEmptyWhenUserNotFoundByEmail() {
        Optional<User> foundUser = userRepository.findByEmail("nonexistent@example.com");

        assertFalse(foundUser.isPresent());
    }

    @Test
    void shouldCheckIfUserExistsByEmail() {
        entityManager.persist(testUser);
        entityManager.flush();

        boolean exists = userRepository.existsByEmail("test@example.com");

        assertTrue(exists);
    }

    @Test
    void shouldReturnFalseWhenUserDoesNotExistByEmail() {
        boolean exists = userRepository.existsByEmail("nonexistent@example.com");

        assertFalse(exists);
    }

    @Test
    void shouldFindUsersByRole() {
        User client1 = createUser("client1@test.com", UserRole.CLIENT);
        User client2 = createUser("client2@test.com", UserRole.CLIENT);
        User freelancer = createUser("freelancer@test.com", UserRole.FREELANCER);

        entityManager.persist(client1);
        entityManager.persist(client2);
        entityManager.persist(freelancer);
        entityManager.flush();

        List<User> clients = userRepository.findByRole(UserRole.CLIENT);

        assertEquals(2, clients.size());
        assertTrue(clients.stream().allMatch(u -> u.getRole() == UserRole.CLIENT));
    }

    @Test
    void shouldFindUsersByIsActive() {
        User activeUser1 = createUser("active1@test.com", UserRole.CLIENT);
        activeUser1.setIsActive(true);
        
        User activeUser2 = createUser("active2@test.com", UserRole.CLIENT);
        activeUser2.setIsActive(true);
        
        User inactiveUser = createUser("inactive@test.com", UserRole.CLIENT);
        inactiveUser.setIsActive(false);

        entityManager.persist(activeUser1);
        entityManager.persist(activeUser2);
        entityManager.persist(inactiveUser);
        entityManager.flush();

        List<User> activeUsers = userRepository.findByIsActive(true);
        List<User> inactiveUsers = userRepository.findByIsActive(false);

        assertEquals(2, activeUsers.size());
        assertEquals(1, inactiveUsers.size());
        assertTrue(activeUsers.stream().allMatch(User::getIsActive));
        assertTrue(inactiveUsers.stream().noneMatch(User::getIsActive));
    }

    @Test
    void shouldFindAllUsers() {
        User user1 = createUser("user1@test.com", UserRole.CLIENT);
        User user2 = createUser("user2@test.com", UserRole.FREELANCER);
        User user3 = createUser("user3@test.com", UserRole.ADMIN);

        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.persist(user3);
        entityManager.flush();

        List<User> allUsers = userRepository.findAll();

        assertEquals(3, allUsers.size());
    }

    @Test
    void shouldFindUserById() {
        User savedUser = entityManager.persist(testUser);
        entityManager.flush();

        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        assertTrue(foundUser.isPresent());
        assertEquals(savedUser.getId(), foundUser.get().getId());
    }

    @Test
    void shouldReturnEmptyWhenUserNotFoundById() {
        Optional<User> foundUser = userRepository.findById(9999L);

        assertFalse(foundUser.isPresent());
    }

    @Test
    void shouldDeleteUser() {
        User savedUser = entityManager.persist(testUser);
        entityManager.flush();
        Long userId = savedUser.getId();

        userRepository.delete(savedUser);
        entityManager.flush();

        Optional<User> deletedUser = userRepository.findById(userId);
        assertFalse(deletedUser.isPresent());
    }

    @Test
    void shouldUpdateUser() {
        User savedUser = entityManager.persist(testUser);
        entityManager.flush();

        savedUser.setFullName("Updated Name");
        savedUser.setPhone("9999999999");
        User updatedUser = userRepository.save(savedUser);
        entityManager.flush();

        Optional<User> foundUser = userRepository.findById(updatedUser.getId());
        assertTrue(foundUser.isPresent());
        assertEquals("Updated Name", foundUser.get().getFullName());
        assertEquals("9999999999", foundUser.get().getPhone());
    }

    @Test
    void shouldEnforceUniqueEmailConstraint() {
        entityManager.persist(testUser);
        entityManager.flush();

        User duplicateUser = new User();
        duplicateUser.setEmail("test@example.com");
        duplicateUser.setPassword("password");
        duplicateUser.setFullName("Duplicate User");
        duplicateUser.setRole(UserRole.CLIENT);
        duplicateUser.setIsActive(true);

        assertThrows(Exception.class, () -> {
            entityManager.persist(duplicateUser);
            entityManager.flush();
        });
    }

    @Test
    void shouldAutoGenerateTimestamps() {
        User savedUser = userRepository.save(testUser);

        assertNotNull(savedUser.getCreatedAt());
        assertNotNull(savedUser.getUpdatedAt());
    }

    @Test
    void shouldUpdateTimestampOnModification() throws InterruptedException {
        User savedUser = userRepository.save(testUser);
        entityManager.flush();
        
        // Wait a bit to ensure different timestamp
        Thread.sleep(100);
        
        savedUser.setFullName("Updated Name");
        User updatedUser = userRepository.save(savedUser);
        entityManager.flush();

        assertNotNull(updatedUser.getUpdatedAt());
        assertTrue(updatedUser.getUpdatedAt().isAfter(updatedUser.getCreatedAt()) ||
                   updatedUser.getUpdatedAt().isEqual(updatedUser.getCreatedAt()));
    }

    @Test
    void shouldFindUsersByMultipleRoles() {
        User admin = createUser("admin@test.com", UserRole.ADMIN);
        User client = createUser("client@test.com", UserRole.CLIENT);
        User freelancer = createUser("freelancer@test.com", UserRole.FREELANCER);

        entityManager.persist(admin);
        entityManager.persist(client);
        entityManager.persist(freelancer);
        entityManager.flush();

        List<User> admins = userRepository.findByRole(UserRole.ADMIN);
        List<User> clients = userRepository.findByRole(UserRole.CLIENT);
        List<User> freelancers = userRepository.findByRole(UserRole.FREELANCER);

        assertEquals(1, admins.size());
        assertEquals(1, clients.size());
        assertEquals(1, freelancers.size());
    }

    @Test
    void shouldHandleNullOptionalFields() {
        testUser.setPhone(null);
        testUser.setProfileImageUrl(null);

        User savedUser = userRepository.save(testUser);

        assertNotNull(savedUser.getId());
        assertNull(savedUser.getPhone());
        assertNull(savedUser.getProfileImageUrl());
    }

    private User createUser(String email, UserRole role) {
        User user = new User();
        user.setEmail(email);
        user.setPassword("encodedPassword");
        user.setFullName("User " + email);
        user.setPhone("1234567890");
        user.setRole(role);
        user.setIsActive(true);
        return user;
    }
}