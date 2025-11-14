package com.freelancenexus.userservice.dto;

import com.freelancenexus.userservice.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * UserResponseDTO
 *
 * <p>Data Transfer Object for user response. Contains user profile information
 * returned by API endpoints. This DTO is used to transfer user data to clients
 * while excluding sensitive information such as passwords.</p>
 *
 * <p>Typically returned by endpoints such as login, registration, profile retrieval,
 * and user listing operations.</p>
 *
 * @author Freelance Nexus
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    
    /**
     * The unique identifier for the user.
     */
    private Long id;
    
    /**
     * The user's email address used for login and identification.
     */
    private String email;
    
    /**
     * The user's full name for display purposes.
     */
    private String fullName;
    
    /**
     * The user's contact phone number.
     */
    private String phone;
    
    /**
     * The user's role in the system (e.g., ADMIN, CLIENT, FREELANCER).
     *
     * @see UserRole
     */
    private UserRole role;
    
    /**
     * Indicates whether the user account is active.
     * {@code true} if active, {@code false} if deactivated.
     */
    private Boolean isActive;
    
    /**
     * The URL to the user's profile image.
     */
    private String profileImageUrl;
    
    /**
     * The timestamp when the user account was created.
     */
    private LocalDateTime createdAt;
    
    /**
     * The timestamp when the user account was last updated.
     */
    private LocalDateTime updatedAt;
}