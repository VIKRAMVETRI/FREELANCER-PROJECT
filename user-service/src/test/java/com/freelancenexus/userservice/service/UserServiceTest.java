package com.freelancenexus.userservice.service;

import com.freelancenexus.userservice.dto.*;
import com.freelancenexus.userservice.exception.DuplicateResourceException;
import com.freelancenexus.userservice.exception.UnauthorizedException;
import com.freelancenexus.userservice.exception.UserNotFoundException;
import com.freelancenexus.userservice.model.User;
import com.freelancenexus.userservice.model.UserRole;
import com.freelancenexus.userservice.repository.UserRepository;
import com.freelancenexus.userservice.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private UserRegistrationDTO registrationDTO;
    private UserLoginDTO loginDTO;
    private UserUpdateDTO updateDTO;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");
        testUser.setPassword("encodedPassword");
        testUser.setFullName("Test User");
        testUser.setPhone("1234567890");
        testUser.setRole(UserRole.CLIENT);
        testUser.setIsActive(true);
        testUser.setProfileImageUrl("http://example.com/image.jpg");
        testUser.setCreatedAt(LocalDateTime.now());
        testUser.setUpdatedAt(LocalDateTime.now());

        registrationDTO = new UserRegistrationDTO();
        registrationDTO.setEmail("test@example.com");
        registrationDTO.setPassword("password123");
        registrationDTO.setFullName("Test User");
        registrationDTO.setPhone("1234567890");
        registrationDTO.setRole(UserRole.CLIENT);
        registrationDTO.setProfileImageUrl("http://example.com/image.jpg");

        loginDTO = new UserLoginDTO();
        loginDTO.setEmail("test@example.com");
        loginDTO.setPassword("password123");

        updateDTO = new UserUpdateDTO();
        updateDTO.setFullName("Updated Name");
        updateDTO.setPhone("9876543210");
        updateDTO.setProfileImageUrl("http://example.com/new-image.jpg");
    }

    @Test
    void registerUser_Success() {
        // Arrange
        when(userRepository.existsByEmail(registrationDTO.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(registrationDTO.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act
        UserResponseDTO result = userService.registerUser(registrationDTO);

        // Assert
        assertNotNull(result);
        assertEquals(testUser.getId(), result.getId());
        assertEquals(testUser.getEmail(), result.getEmail());
        assertEquals(testUser.getFullName(), result.getFullName());
        verify(userRepository).existsByEmail(registrationDTO.getEmail());
        verify(passwordEncoder).encode(registrationDTO.getPassword());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void registerUser_DuplicateEmail_ThrowsException() {
        // Arrange
        when(userRepository.existsByEmail(registrationDTO.getEmail())).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateResourceException.class, () -> {
            userService.registerUser(registrationDTO);
        });
        verify(userRepository).existsByEmail(registrationDTO.getEmail());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void loginUser_Success() {
        // Arrange
        when(userRepository.findByEmail(loginDTO.getEmail())).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(loginDTO.getPassword(), testUser.getPassword())).thenReturn(true);
        when(jwtTokenProvider.generateToken(testUser.getEmail(), testUser.getId(), testUser.getRole().name()))
            .thenReturn("jwt-token");

        // Act
        LoginResponseDTO result = userService.loginUser(loginDTO);

        // Assert
        assertNotNull(result);
        assertEquals("jwt-token", result.getAccessToken());
        assertEquals("Bearer", result.getTokenType());
        assertEquals(86400L, result.getExpiresIn());
        assertNotNull(result.getUser());
        assertEquals(testUser.getEmail(), result.getUser().getEmail());
        verify(userRepository).findByEmail(loginDTO.getEmail());
        verify(passwordEncoder).matches(loginDTO.getPassword(), testUser.getPassword());
    }

    @Test
    void loginUser_UserNotFound_ThrowsException() {
        // Arrange
        when(userRepository.findByEmail(loginDTO.getEmail())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UnauthorizedException.class, () -> {
            userService.loginUser(loginDTO);
        });
        verify(userRepository).findByEmail(loginDTO.getEmail());
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    void loginUser_InactiveUser_ThrowsException() {
        // Arrange
        testUser.setIsActive(false);
        when(userRepository.findByEmail(loginDTO.getEmail())).thenReturn(Optional.of(testUser));

        // Act & Assert
        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> {
            userService.loginUser(loginDTO);
        });
        assertEquals("Account is inactive", exception.getMessage());
        verify(userRepository).findByEmail(loginDTO.getEmail());
    }

    @Test
    void loginUser_InvalidPassword_ThrowsException() {
        // Arrange
        when(userRepository.findByEmail(loginDTO.getEmail())).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(loginDTO.getPassword(), testUser.getPassword())).thenReturn(false);

        // Act & Assert
        assertThrows(UnauthorizedException.class, () -> {
            userService.loginUser(loginDTO);
        });
        verify(userRepository).findByEmail(loginDTO.getEmail());
        verify(passwordEncoder).matches(loginDTO.getPassword(), testUser.getPassword());
    }

    @Test
    void getCurrentUserProfile_Success() {
        // Arrange
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        // Act
        UserResponseDTO result = userService.getCurrentUserProfile();

        // Assert
        assertNotNull(result);
        assertEquals(testUser.getId(), result.getId());
        assertEquals(testUser.getEmail(), result.getEmail());
        verify(userRepository).findById(1L);
    }

    @Test
    void getCurrentUserProfile_UserNotFound_ThrowsException() {
        // Arrange
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> {
            userService.getCurrentUserProfile();
        });
        verify(userRepository).findById(1L);
    }

    @Test
    void getCurrentUserProfile_NotAuthenticated_ThrowsException() {
        // Arrange
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(null);

        // Act & Assert
        assertThrows(UnauthorizedException.class, () -> {
            userService.getCurrentUserProfile();
        });
    }

    @Test
    void updateCurrentUserProfile_Success() {
        // Arrange
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act
        UserResponseDTO result = userService.updateCurrentUserProfile(updateDTO);

        // Assert
        assertNotNull(result);
        verify(userRepository).findById(1L);
        verify(userRepository).save(testUser);
        assertEquals("Updated Name", testUser.getFullName());
        assertEquals("9876543210", testUser.getPhone());
    }

    @Test
    void updateCurrentUserProfile_PartialUpdate() {
        // Arrange
        UserUpdateDTO partialUpdate = new UserUpdateDTO();
        partialUpdate.setFullName("New Name");
        
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act
        UserResponseDTO result = userService.updateCurrentUserProfile(partialUpdate);

        // Assert
        assertNotNull(result);
        assertEquals("New Name", testUser.getFullName());
        verify(userRepository).save(testUser);
    }

    @Test
    void getUserById_Success() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        // Act
        UserResponseDTO result = userService.getUserById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(testUser.getId(), result.getId());
        assertEquals(testUser.getEmail(), result.getEmail());
        verify(userRepository).findById(1L);
    }

    @Test
    void getUserById_NotFound_ThrowsException() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> {
            userService.getUserById(1L);
        });
        verify(userRepository).findById(1L);
    }

    @Test
    void getAllUsers_Success() {
        // Arrange
        User user2 = new User();
        user2.setId(2L);
        user2.setEmail("user2@example.com");
        user2.setFullName("User Two");
        user2.setRole(UserRole.FREELANCER);
        user2.setIsActive(true);
        user2.setCreatedAt(LocalDateTime.now());
        user2.setUpdatedAt(LocalDateTime.now());

        List<User> users = Arrays.asList(testUser, user2);
        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<UserResponseDTO> result = userService.getAllUsers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(testUser.getEmail(), result.get(0).getEmail());
        assertEquals(user2.getEmail(), result.get(1).getEmail());
        verify(userRepository).findAll();
    }

    @Test
    void deleteUser_Success() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        doNothing().when(userRepository).delete(testUser);

        // Act
        userService.deleteUser(1L);

        // Assert
        verify(userRepository).findById(1L);
        verify(userRepository).delete(testUser);
    }

    @Test
    void deleteUser_NotFound_ThrowsException() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> {
            userService.deleteUser(1L);
        });
        verify(userRepository).findById(1L);
        verify(userRepository, never()).delete(any(User.class));
    }
}