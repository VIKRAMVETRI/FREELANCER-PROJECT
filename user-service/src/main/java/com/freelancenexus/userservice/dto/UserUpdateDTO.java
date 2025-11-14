package com.freelancenexus.userservice.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserUpdateDTO
 *
 * <p>Data Transfer Object used for updating a user's profile information.
 * Contains only the fields that may be changed by the user. Validation
 * annotations constrain allowed values and lengths.</p>
 *
 * <p>Typical use: PUT/POST requests to update the authenticated user's profile.</p>
 *
 * @author Freelance Nexus
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {
    
    /**
     * The user's full name for profile display.
     * Optional; when provided must be between 2 and 255 characters.
     *
     * @validation Size(min = 2, max = 255)
     */
    @Size(min = 2, max = 255, message = "Full name must be between 2 and 255 characters")
    private String fullName;
    
    /**
     * The user's contact phone number.
     * Optional; when provided must not exceed 20 characters.
     *
     * @validation Size(max = 20)
     */
    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    private String phone;
    
    /**
     * URL pointing to the user's profile image.
     * Optional; used to update or set the profile image location.
     */
    private String profileImageUrl;
}