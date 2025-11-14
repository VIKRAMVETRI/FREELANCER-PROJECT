package com.freelancenexus.userservice.service;

import com.freelancenexus.userservice.dto.*;
import com.freelancenexus.userservice.model.User;
import com.freelancenexus.userservice.repository.UserRepository;
import com.freelancenexus.userservice.security.JwtTokenProvider;
import com.freelancenexus.userservice.exception.DuplicateResourceException;
import com.freelancenexus.userservice.exception.UnauthorizedException;
import com.freelancenexus.userservice.exception.UserNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * UserService
 *
 * <p>Core application service that encapsulates user-related business logic for the User Service.
 * Responsibilities include user registration, authentication (login), profile retrieval and updates,
 * administrative user management (listing and deletion) and mapping between entity and DTO representations.</p>
 *
 * <p>Transaction boundaries are declared on methods where data consistency is required. Authentication
 * relies on the current SecurityContext for operations that require the authenticated user.</p>
 *
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    
    /**
     * Repository for CRUD operations against User entities.
     */
    private final UserRepository userRepository;
    
    /**
     * Password encoder used to hash and verify user passwords.
     */
    private final PasswordEncoder passwordEncoder;
    
    /**
     * JWT token provider used to generate and validate authentication tokens.
     */
    private final JwtTokenProvider jwtTokenProvider;
    
    /**
     * Register a new user.
     *
     * <p>Validates uniqueness of the provided email, hashes the password, persists the new user,
     * and returns a {@link UserResponseDTO} representing the created user.</p>
     *
     * @param registrationDTO registration details provided by the client
     * @return created user's {@link UserResponseDTO}
     * @throws DuplicateResourceException if a user with the same email already exists
     */
    @Transactional
    public UserResponseDTO registerUser(UserRegistrationDTO registrationDTO) {
        log.info("Registering new user with email: {}", registrationDTO.getEmail());
        
        // Check if user already exists
        if (userRepository.existsByEmail(registrationDTO.getEmail())) {
            throw new DuplicateResourceException("User with email " + registrationDTO.getEmail() + " already exists");
        }
        
        // Create user in database
        User user = new User();
        user.setEmail(registrationDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        user.setFullName(registrationDTO.getFullName());
        user.setPhone(registrationDTO.getPhone());
        user.setRole(registrationDTO.getRole());
        user.setIsActive(true);
        user.setProfileImageUrl(registrationDTO.getProfileImageUrl());
        
        User savedUser = userRepository.save(user);
        log.info("User registered successfully with ID: {}", savedUser.getId());
        
        return mapToResponseDTO(savedUser);
    }
    
    /**
     * Authenticate a user and return login response containing JWT token information.
     *
     * <p>Verifies credentials, account active state, and generates a JWT access token and
     * {@link LoginResponseDTO} including basic user info.</p>
     *
     * @param loginDTO login credentials
     * @return {@link LoginResponseDTO} with token and user details
     * @throws UnauthorizedException if credentials are invalid or account is inactive
     */
    @Transactional(readOnly = true)
    public LoginResponseDTO loginUser(UserLoginDTO loginDTO) {
        log.info("Authenticating user with email: {}", loginDTO.getEmail());
        
        // Find user by email
        User user = userRepository.findByEmail(loginDTO.getEmail())
            .orElseThrow(() -> new UnauthorizedException("Invalid email or password"));
        
        // Check if user is active
        if (!user.getIsActive()) {
            throw new UnauthorizedException("Account is inactive");
        }
        
        // Verify password
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid email or password");
        }
        
        // Generate JWT token
        String token = jwtTokenProvider.generateToken(
            user.getEmail(), 
            user.getId(), 
            user.getRole().name()
        );
        
        // Create response
        LoginResponseDTO loginResponse = new LoginResponseDTO();
        loginResponse.setAccessToken(token);
        loginResponse.setTokenType("Bearer");
        loginResponse.setExpiresIn(86400L); // 24 hours in seconds
        loginResponse.setUser(mapToResponseDTO(user));
        
        log.info("User authenticated successfully: {}", loginDTO.getEmail());
        return loginResponse;
    }
    
    /**
     * Retrieve the profile of the currently authenticated user.
     *
     * <p>Extracts the user id from the security context and loads the user from the database.</p>
     *
     * @return {@link UserResponseDTO} for the authenticated user
     * @throws UnauthorizedException if no authenticated user is present
     * @throws UserNotFoundException if the user cannot be found in the database
     */
    @Transactional(readOnly = true)
    public UserResponseDTO getCurrentUserProfile() {
        Long userId = getCurrentUserId();
        
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User not found"));
        
        return mapToResponseDTO(user);
    }
    
    /**
     * Update the profile of the currently authenticated user.
     *
     * <p>Only updates fields provided in {@link UserUpdateDTO} and persists changes.</p>
     *
     * @param updateDTO profile fields to update
     * @return updated {@link UserResponseDTO}
     * @throws UnauthorizedException if no authenticated user is present
     * @throws UserNotFoundException if the user cannot be found in the database
     */
    @Transactional
    public UserResponseDTO updateCurrentUserProfile(UserUpdateDTO updateDTO) {
        Long userId = getCurrentUserId();
        
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User not found"));
        
        // Update fields if provided
        if (updateDTO.getFullName() != null) {
            user.setFullName(updateDTO.getFullName());
        }
        if (updateDTO.getPhone() != null) {
            user.setPhone(updateDTO.getPhone());
        }
        if (updateDTO.getProfileImageUrl() != null) {
            user.setProfileImageUrl(updateDTO.getProfileImageUrl());
        }
        
        User updatedUser = userRepository.save(user);
        log.info("User profile updated successfully: {}", user.getId());
        
        return mapToResponseDTO(updatedUser);
    }
    
    /**
     * Retrieve a user by their id.
     *
     * @param id unique identifier of the user
     * @return {@link UserResponseDTO} for the requested user
     * @throws UserNotFoundException if no user exists with the given id
     */
    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        
        return mapToResponseDTO(user);
    }
    
    /**
     * Retrieve all users in the system.
     *
     * <p>Useful for administrative listing endpoints.</p>
     *
     * @return list of {@link UserResponseDTO}
     */
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
            .map(this::mapToResponseDTO)
            .collect(Collectors.toList());
    }
    
    /**
     * Delete a user by id.
     *
     * <p>Performs a persistent deletion of the user entity. Intended for administrative use.</p>
     *
     * @param id id of the user to delete
     * @throws UserNotFoundException if the user does not exist
     */
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        
        userRepository.delete(user);
        log.info("User deleted successfully: {}", id);
    }
    
    /**
     * Extracts the currently authenticated user's id from the SecurityContext.
     *
     * @return authenticated user's id
     * @throws UnauthorizedException if no authenticated principal is available
     */
    private Long getCurrentUserId() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.getPrincipal() instanceof Long principal) {
            return principal;
        }

        throw new UnauthorizedException("User not authenticated");
    }

    /**
     * Map a {@link User} entity to {@link UserResponseDTO}.
     *
     * @param user the entity to map
     * @return DTO representing the user (excludes sensitive data such as password)
     */
    private UserResponseDTO mapToResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole());
        dto.setIsActive(user.getIsActive());
        dto.setProfileImageUrl(user.getProfileImageUrl());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }
}