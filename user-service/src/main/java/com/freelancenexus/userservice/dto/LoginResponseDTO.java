package com.freelancenexus.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * LoginResponseDTO
 *
 * <p>Data Transfer Object for user login response. Contains authentication tokens,
 * token expiration information, and the authenticated user's details.</p>
 *
 * <p>This DTO is returned by the login endpoint after successful authentication
 * and is used by clients to obtain JWT tokens for subsequent API requests.</p>
 *
 * @author Freelance Nexus
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    
    /**
     * The JWT access token for authenticating subsequent API requests.
     * This token should be included in the {@code Authorization} header as a Bearer token.
     */
    private String accessToken;
    
    /**
     * The refresh token used to obtain a new access token when the current one expires.
     */
    private String refreshToken;
    
    /**
     * Token expiration time in seconds from the time of issuance.
     */
    private Long expiresIn;
    
    /**
     * The type of token, typically "Bearer" for JWT tokens.
     * Default value is "Bearer".
     */
    private String tokenType = "Bearer";
    
    /**
     * The authenticated user's profile information.
     *
     * @see UserResponseDTO
     */
    private UserResponseDTO user;
}