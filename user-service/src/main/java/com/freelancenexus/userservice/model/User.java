package com.freelancenexus.userservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * User
 *
 * <p>JPA entity representing a user account in the Freelance Nexus system.
 * Stores authentication and profile information used across the user service.
 * Sensitive fields (e.g. password) should never be exposed in DTOs returned to clients.</p>
 *
 * <p>Indexes are defined for commonly queried columns (email, role) to improve lookup performance.</p>
 *
 * @since 1.0
 */
@Entity
@Table(name = "users", indexes = {
    @Index(name = "idx_email", columnList = "email"),
    @Index(name = "idx_role", columnList = "role")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    /**
     * Primary key identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Unique email address used for authentication and identification.
     * This column is indexed and must be unique and non-null.
     */
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    
    /**
     * Hashed password for the user. Never expose this field in API responses.
     */
    @Column(name = "password")
    private String password;
    
    /**
     * User's full name for display purposes.
     */
    @Column(name = "full_name", nullable = false)
    private String fullName;
    
    /**
     * Contact phone number (optional). Max length constrained by DB column.
     */
    @Column(name = "phone", length = 20)
    private String phone;
    
    /**
     * Role assigned to the user (e.g., ADMIN, CLIENT, FREELANCER).
     * Persisted as a String representation of the enum.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;
    
    /**
     * Flag indicating whether the user account is active.
     */
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    /**
     * URL to the user's profile image (optional).
     */
    @Column(name = "profile_image_url", length = 500)
    private String profileImageUrl;
    
    /**
     * Timestamp when the user was created. Managed by Hibernate.
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    /**
     * Timestamp when the user was last updated. Managed by Hibernate.
     */
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}