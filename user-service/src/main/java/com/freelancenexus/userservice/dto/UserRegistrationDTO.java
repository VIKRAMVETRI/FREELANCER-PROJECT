package com.freelancenexus.userservice.dto;

import com.freelancenexus.userservice.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserRegistrationDTO
 *
 * <p>Data Transfer Object for user registration request. Contains all required
 * and optional information needed to create a new user account. All fields are
 * validated for presence, format, and length constraints.</p>
 *
 * <p>This DTO is sent by clients to the registration endpoint to create a new
 * user account in the system.</p>
 *
 * @author Freelance Nexus
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDTO {
    
    /**
     * The user's email address used for account identification and login.
     * Must be a valid email format and cannot be blank.
     *
     * @validation NotBlank, Email format
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    
    /**
     * The user's password for account security.
     * Must be at least 8 characters long and cannot be blank.
     *
     * @validation NotBlank, minimum 8 characters
     */
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
    
    /**
     * The user's full name for display purposes.
     * Must be between 2 and 255 characters and cannot be blank.
     *
     * @validation NotBlank, length between 2-255 characters
     */
    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 255, message = "Full name must be between 2 and 255 characters")
    private String fullName;
    
    /**
     * The user's contact phone number.
     * Optional field. If provided, must not exceed 20 characters.
     *
     * @validation maximum 20 characters
     */
    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    private String phone;
    
    /**
     * The user's role in the system (e.g., ADMIN, CLIENT, FREELANCER).
     * Must be specified and cannot be null.
     *
     * @validation NotNull
     * @see UserRole
     */
    @NotNull(message = "Role is required")
    private UserRole role;
    
    /**
     * The URL to the user's profile image.
     * Optional field for user profile picture storage.
     */
    private String profileImageUrl;
}