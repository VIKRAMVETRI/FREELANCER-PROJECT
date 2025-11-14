package com.freelancenexus.userservice.repository;

import com.freelancenexus.userservice.model.User;
import com.freelancenexus.userservice.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * UserRepository
 *
 * <p>Spring Data JPA repository for performing CRUD and query operations on {@link User} entities.
 * Provides convenience query methods commonly used by the User service, such as lookup by email,
 * existence checks, and filters by role or active status. Implementations are provided automatically
 * by Spring Data at runtime.</p>
 *
 * @since 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Find a user by their unique email address.
     *
     * @param email the email address to search for
     * @return an {@link Optional} containing the User if found, or empty if not found
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Check whether a user exists with the given email address.
     *
     * @param email the email address to check
     * @return {@code true} if a user with the email exists, {@code false} otherwise
     */
    boolean existsByEmail(String email);
    
    /**
     * Retrieve all users that have the specified role.
     *
     * @param role the {@link UserRole} to filter by
     * @return a list of users with the given role (may be empty)
     */
    List<User> findByRole(UserRole role);
    
    /**
     * Retrieve all users by their active status.
     *
     * @param isActive {@code true} to find active users, {@code false} for inactive users
     * @return a list of users filtered by their active state (may be empty)
     */
    List<User> findByIsActive(Boolean isActive);
}