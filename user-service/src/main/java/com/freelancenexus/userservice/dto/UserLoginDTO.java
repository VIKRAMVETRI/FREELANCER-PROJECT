package com.freelancenexus.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserLoginDTO
 *
 * <p>Data Transfer Object for user login request. Contains credentials required
 * for user authentication. Both fields are validated for presence and format.</p>
 *
 * <p>This DTO is sent by clients to the login endpoint to authenticate and obtain
 * JWT tokens for accessing protected resources.</p>
 *
 * @author Freelance Nexus
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {
    
    /**
     * The user's email address used for authentication.
     * Must be a valid email format and cannot be blank.
     *
     * @validation NotBlank, Email format
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    
    /**
     * The user's password for authentication.
     * Must not be blank.
     *
     * @validation NotBlank
     */
    @NotBlank(message = "Password is required")
    private String password;
}