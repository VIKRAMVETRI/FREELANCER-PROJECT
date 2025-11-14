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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService Comprehensive Tests")
class UserServiceExpandedTest {

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
        testUser = createTestUser(1L, "test@example.com", "Test User", UserRole.CLIENT, true);
        registrationDTO = createRegistrationDTO("test@example.com", "password123", "Test User", UserRole.CLIENT);
        loginDTO = new UserLoginDTO("test@example.com", "password123");
        updateDTO = new UserUpdateDTO("Updated Name", "9876543210", "http://example.com/new.jpg");
    }

    // Helper methods
    private User createTestUser(Long id, String email, String fullName, UserRole role, Boolean isActive) {
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setPassword("encodedPassword");
        user.setFullName(fullName);
        user.setPhone("1234567890");
        user.setRole(role);
        user.setIsActive(isActive);
        user.setProfileImageUrl("http://example.com/image.jpg");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }

    private UserRegistrationDTO createRegistrationDTO(String email, String password, String fullName, UserRole role) {
        UserRegistrationDTO dto = new UserRegistrationDTO();
        dto.setEmail(email);
        dto.setPassword(password);
        dto.setFullName(fullName);
        dto.setPhone("1234567890");
        dto.setRole(role);
        dto.setProfileImageUrl("http://example.com/image.jpg");
        return dto;
    }

    @Nested
    @DisplayName("User Registration Tests")
    class RegistrationTests {

        @Test
        @DisplayName("Should register user with CLIENT role successfully")
        void registerUser_ClientRole_Success() {
            when(userRepository.existsByEmail(anyString())).thenReturn(false);
            when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
            when(userRepository.save(any(User.class))).thenReturn(testUser);

            UserResponseDTO result = userService.registerUser(registrationDTO);

            assertNotNull(result);
            assertEquals(testUser.getEmail(), result.getEmail());
            verify(userRepository).save(any(User.class));
        }

        @Test
        @DisplayName("Should register user with FREELANCER role successfully")
        void registerUser_FreelancerRole_Success() {
            registrationDTO.setRole(UserRole.FREELANCER);
            testUser.setRole(UserRole.FREELANCER);
            
            when(userRepository.existsByEmail(anyString())).thenReturn(false);
            when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
            when(userRepository.save(any(User.class))).thenReturn(testUser);

            UserResponseDTO result = userService.registerUser(registrationDTO);

            assertEquals(UserRole.FREELANCER, result.getRole());
        }

        @Test
        @DisplayName("Should register user with ADMIN role successfully")
        void registerUser_AdminRole_Success() {
            registrationDTO.setRole(UserRole.ADMIN);
            testUser.setRole(UserRole.ADMIN);
            
            when(userRepository.existsByEmail(anyString())).thenReturn(false);
            when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
            when(userRepository.save(any(User.class))).thenReturn(testUser);

            UserResponseDTO result = userService.registerUser(registrationDTO);

            assertEquals(UserRole.ADMIN, result.getRole());
        }

        @ParameterizedTest
        @EnumSource(UserRole.class)
        @DisplayName("Should register user with any role")
        void registerUser_AllRoles_Success(UserRole role) {
            registrationDTO.setRole(role);
            testUser.setRole(role);
            
            when(userRepository.existsByEmail(anyString())).thenReturn(false);
            when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
            when(userRepository.save(any(User.class))).thenReturn(testUser);

            UserResponseDTO result = userService.registerUser(registrationDTO);

            assertEquals(role, result.getRole());
        }

        @Test
        @DisplayName("Should throw exception when email already exists")
        void registerUser_DuplicateEmail_ThrowsException() {
            when(userRepository.existsByEmail(anyString())).thenReturn(true);

            assertThrows(DuplicateResourceException.class, () -> {
                userService.registerUser(registrationDTO);
            });
            verify(userRepository, never()).save(any(User.class));
        }

        @Test
        @DisplayName("Should encode password during registration")
        void registerUser_EncodesPassword() {
            when(userRepository.existsByEmail(anyString())).thenReturn(false);
            when(passwordEncoder.encode("password123")).thenReturn("encodedPassword123");
            when(userRepository.save(any(User.class))).thenReturn(testUser);

            userService.registerUser(registrationDTO);

            verify(passwordEncoder).encode("password123");
        }

        @Test
        @DisplayName("Should set isActive to true by default")
        void registerUser_DefaultIsActiveTrue() {
            when(userRepository.existsByEmail(anyString())).thenReturn(false);
            when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
            when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
                User savedUser = invocation.getArgument(0);
                assertTrue(savedUser.getIsActive());
                return savedUser;
            });

            userService.registerUser(registrationDTO);
        }

        @Test
        @DisplayName("Should save all user fields correctly")
        void registerUser_SavesAllFields() {
            when(userRepository.existsByEmail(anyString())).thenReturn(false);
            when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
            when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
                User savedUser = invocation.getArgument(0);
                assertEquals(registrationDTO.getEmail(), savedUser.getEmail());
                assertEquals(registrationDTO.getFullName(), savedUser.getFullName());
                assertEquals(registrationDTO.getPhone(), savedUser.getPhone());
                assertEquals(registrationDTO.getRole(), savedUser.getRole());
                assertEquals(registrationDTO.getProfileImageUrl(), savedUser.getProfileImageUrl());
                return savedUser;
            });

            userService.registerUser(registrationDTO);
        }

        @Test
        @DisplayName("Should handle registration with null phone")
        void registerUser_NullPhone_Success() {
            registrationDTO.setPhone(null);
            when(userRepository.existsByEmail(anyString())).thenReturn(false);
            when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
            when(userRepository.save(any(User.class))).thenReturn(testUser);

            UserResponseDTO result = userService.registerUser(registrationDTO);

            assertNotNull(result);
        }

        @Test
        @DisplayName("Should handle registration with null profile image")
        void registerUser_NullProfileImage_Success() {
            registrationDTO.setProfileImageUrl(null);
            when(userRepository.existsByEmail(anyString())).thenReturn(false);
            when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
            when(userRepository.save(any(User.class))).thenReturn(testUser);

            UserResponseDTO result = userService.registerUser(registrationDTO);

            assertNotNull(result);
        }

        @ParameterizedTest
        @ValueSource(strings = {"user1@test.com", "admin@company.com", "freelancer@work.com"})
        @DisplayName("Should register users with different email formats")
        void registerUser_DifferentEmails_Success(String email) {
            registrationDTO.setEmail(email);
            when(userRepository.existsByEmail(email)).thenReturn(false);
            when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
            when(userRepository.save(any(User.class))).thenReturn(testUser);

            UserResponseDTO result = userService.registerUser(registrationDTO);

            assertNotNull(result);
            verify(userRepository).existsByEmail(email);
        }
    }

    @Nested
    @DisplayName("User Login Tests")
    class LoginTests {

        @Test
        @DisplayName("Should login successfully with valid credentials")
        void loginUser_ValidCredentials_Success() {
            when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));
            when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
            when(jwtTokenProvider.generateToken(anyString(), anyLong(), anyString())).thenReturn("jwt-token");

            LoginResponseDTO result = userService.loginUser(loginDTO);

            assertNotNull(result);
            assertEquals("jwt-token", result.getAccessToken());
            assertEquals("Bearer", result.getTokenType());
        }

        @Test
        @DisplayName("Should return user details in login response")
        void loginUser_ReturnsUserDetails() {
            when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));
            when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
            when(jwtTokenProvider.generateToken(anyString(), anyLong(), anyString())).thenReturn("jwt-token");

            LoginResponseDTO result = userService.loginUser(loginDTO);

            assertNotNull(result.getUser());
            assertEquals(testUser.getEmail(), result.getUser().getEmail());
            assertEquals(testUser.getFullName(), result.getUser().getFullName());
        }

        @Test
        @DisplayName("Should set correct token expiration")
        void loginUser_SetsTokenExpiration() {
            when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));
            when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
            when(jwtTokenProvider.generateToken(anyString(), anyLong(), anyString())).thenReturn("jwt-token");

            LoginResponseDTO result = userService.loginUser(loginDTO);

            assertEquals(86400L, result.getExpiresIn());
        }

        @Test
        @DisplayName("Should throw exception when user not found")
        void loginUser_UserNotFound_ThrowsException() {
            when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

            assertThrows(UnauthorizedException.class, () -> {
                userService.loginUser(loginDTO);
            });
        }

        @Test
        @DisplayName("Should throw exception when password is incorrect")
        void loginUser_IncorrectPassword_ThrowsException() {
            when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));
            when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

            assertThrows(UnauthorizedException.class, () -> {
                userService.loginUser(loginDTO);
            });
        }

        @Test
        @DisplayName("Should throw exception when account is inactive")
        void loginUser_InactiveAccount_ThrowsException() {
            testUser.setIsActive(false);
            when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));

            UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> {
                userService.loginUser(loginDTO);
            });
            assertEquals("Account is inactive", exception.getMessage());
        }

        @Test
        @DisplayName("Should generate token with correct parameters")
        void loginUser_GeneratesTokenWithCorrectParams() {
            when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));
            when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
            when(jwtTokenProvider.generateToken(anyString(), anyLong(), anyString())).thenReturn("jwt-token");

            userService.loginUser(loginDTO);

            verify(jwtTokenProvider).generateToken(
                testUser.getEmail(),
                testUser.getId(),
                testUser.getRole().name()
            );
        }

        @ParameterizedTest
        @EnumSource(UserRole.class)
        @DisplayName("Should login users with different roles")
        void loginUser_DifferentRoles_Success(UserRole role) {
            testUser.setRole(role);
            when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));
            when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
            when(jwtTokenProvider.generateToken(anyString(), anyLong(), anyString())).thenReturn("jwt-token");

            LoginResponseDTO result = userService.loginUser(loginDTO);

            assertEquals(role, result.getUser().getRole());
        }

        @Test
        @DisplayName("Should validate password before generating token")
        void loginUser_ValidatesPasswordFirst() {
            when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));
            when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

            assertThrows(UnauthorizedException.class, () -> {
                userService.loginUser(loginDTO);
            });
            verify(jwtTokenProvider, never()).generateToken(anyString(), anyLong(), anyString());
        }

        @Test
        @DisplayName("Should check user active status before password validation")
        void loginUser_ChecksActiveStatusFirst() {
            testUser.setIsActive(false);
            when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));

            assertThrows(UnauthorizedException.class, () -> {
                userService.loginUser(loginDTO);
            });
            verify(passwordEncoder, never()).matches(anyString(), anyString());
        }
    }

    @Nested
    @DisplayName("Get Current User Profile Tests")
    class GetCurrentProfileTests {

        @Test
        @DisplayName("Should get current user profile successfully")
        void getCurrentUserProfile_Success() {
            setupSecurityContext(1L);
            when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

            UserResponseDTO result = userService.getCurrentUserProfile();

            assertNotNull(result);
            assertEquals(testUser.getEmail(), result.getEmail());
        }

        @Test
        @DisplayName("Should throw exception when user not authenticated")
        void getCurrentUserProfile_NotAuthenticated_ThrowsException() {
            SecurityContextHolder.setContext(securityContext);
            when(securityContext.getAuthentication()).thenReturn(null);

            assertThrows(UnauthorizedException.class, () -> {
                userService.getCurrentUserProfile();
            });
        }

        @Test
        @DisplayName("Should throw exception when user not found")
        void getCurrentUserProfile_UserNotFound_ThrowsException() {
            setupSecurityContext(999L);
            when(userRepository.findById(999L)).thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class, () -> {
                userService.getCurrentUserProfile();
            });
        }

        @Test
        @DisplayName("Should return all user fields")
        void getCurrentUserProfile_ReturnsAllFields() {
            setupSecurityContext(1L);
            when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

            UserResponseDTO result = userService.getCurrentUserProfile();

            assertEquals(testUser.getId(), result.getId());
            assertEquals(testUser.getEmail(), result.getEmail());
            assertEquals(testUser.getFullName(), result.getFullName());
            assertEquals(testUser.getPhone(), result.getPhone());
            assertEquals(testUser.getRole(), result.getRole());
            assertEquals(testUser.getIsActive(), result.getIsActive());
            assertEquals(testUser.getProfileImageUrl(), result.getProfileImageUrl());
        }

        @ParameterizedTest
        @ValueSource(longs = {1L, 100L, 999L})
        @DisplayName("Should get profile for different user IDs")
        void getCurrentUserProfile_DifferentUserIds(Long userId) {
            setupSecurityContext(userId);
            testUser.setId(userId);
            when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));

            UserResponseDTO result = userService.getCurrentUserProfile();

            assertEquals(userId, result.getId());
        }

        @Test
        @DisplayName("Should handle principal as non-Long type")
        void getCurrentUserProfile_NonLongPrincipal_ThrowsException() {
            SecurityContextHolder.setContext(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getPrincipal()).thenReturn("not-a-long");

            assertThrows(UnauthorizedException.class, () -> {
                userService.getCurrentUserProfile();
            });
        }
    }

    @Nested
    @DisplayName("Update Current User Profile Tests")
    class UpdateCurrentProfileTests {

        @Test
        @DisplayName("Should update all fields successfully")
        void updateCurrentUserProfile_AllFields_Success() {
            setupSecurityContext(1L);
            when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
            when(userRepository.save(any(User.class))).thenReturn(testUser);

            UserResponseDTO result = userService.updateCurrentUserProfile(updateDTO);

            assertEquals("Updated Name", testUser.getFullName());
            assertEquals("9876543210", testUser.getPhone());
            assertEquals("http://example.com/new.jpg", testUser.getProfileImageUrl());
        }

        @Test
        @DisplayName("Should update only full name")
        void updateCurrentUserProfile_OnlyFullName() {
            UserUpdateDTO partialUpdate = new UserUpdateDTO();
            partialUpdate.setFullName("New Name Only");
            
            setupSecurityContext(1L);
            when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
            when(userRepository.save(any(User.class))).thenReturn(testUser);

            userService.updateCurrentUserProfile(partialUpdate);

            assertEquals("New Name Only", testUser.getFullName());
            assertEquals("1234567890", testUser.getPhone()); // Unchanged
        }

        @Test
        @DisplayName("Should update only phone")
        void updateCurrentUserProfile_OnlyPhone() {
            UserUpdateDTO partialUpdate = new UserUpdateDTO();
            partialUpdate.setPhone("9999999999");
            
            setupSecurityContext(1L);
            when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
            when(userRepository.save(any(User.class))).thenReturn(testUser);

            userService.updateCurrentUserProfile(partialUpdate);

            assertEquals("9999999999", testUser.getPhone());
            assertEquals("Test User", testUser.getFullName()); // Unchanged
        }

        @Test
        @DisplayName("Should update only profile image")
        void updateCurrentUserProfile_OnlyProfileImage() {
            UserUpdateDTO partialUpdate = new UserUpdateDTO();
            partialUpdate.setProfileImageUrl("http://new-image.com/pic.jpg");
            
            setupSecurityContext(1L);
            when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
            when(userRepository.save(any(User.class))).thenReturn(testUser);

            userService.updateCurrentUserProfile(partialUpdate);

            assertEquals("http://new-image.com/pic.jpg", testUser.getProfileImageUrl());
        }

        @Test
        @DisplayName("Should handle empty update DTO")
        void updateCurrentUserProfile_EmptyDTO_NoChanges() {
            UserUpdateDTO emptyUpdate = new UserUpdateDTO();
            String originalName = testUser.getFullName();
            String originalPhone = testUser.getPhone();
            
            setupSecurityContext(1L);
            when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
            when(userRepository.save(any(User.class))).thenReturn(testUser);

            userService.updateCurrentUserProfile(emptyUpdate);

            assertEquals(originalName, testUser.getFullName());
            assertEquals(originalPhone, testUser.getPhone());
        }

        @Test
        @DisplayName("Should throw exception when user not found")
        void updateCurrentUserProfile_UserNotFound_ThrowsException() {
            setupSecurityContext(999L);
            when(userRepository.findById(999L)).thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class, () -> {
                userService.updateCurrentUserProfile(updateDTO);
            });
        }

        @Test
        @DisplayName("Should save updated user")
        void updateCurrentUserProfile_SavesUser() {
            setupSecurityContext(1L);
            when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
            when(userRepository.save(any(User.class))).thenReturn(testUser);

            userService.updateCurrentUserProfile(updateDTO);

            verify(userRepository).save(testUser);
        }

        @Test
        @DisplayName("Should return updated user response")
        void updateCurrentUserProfile_ReturnsUpdatedResponse() {
            setupSecurityContext(1L);
            testUser.setFullName("Updated Name");
            when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
            when(userRepository.save(any(User.class))).thenReturn(testUser);

            UserResponseDTO result = userService.updateCurrentUserProfile(updateDTO);

            assertEquals("Updated Name", result.getFullName());
        }

        @Test
        @DisplayName("Should not update email")
        void updateCurrentUserProfile_EmailUnchanged() {
            String originalEmail = testUser.getEmail();
            setupSecurityContext(1L);
            when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
            when(userRepository.save(any(User.class))).thenReturn(testUser);

            userService.updateCurrentUserProfile(updateDTO);

            assertEquals(originalEmail, testUser.getEmail());
        }

        @Test
        @DisplayName("Should not update role")
        void updateCurrentUserProfile_RoleUnchanged() {
            UserRole originalRole = testUser.getRole();
            setupSecurityContext(1L);
            when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
            when(userRepository.save(any(User.class))).thenReturn(testUser);

            userService.updateCurrentUserProfile(updateDTO);

            assertEquals(originalRole, testUser.getRole());
        }

        @Test
        @DisplayName("Should not update password")
        void updateCurrentUserProfile_PasswordUnchanged() {
            String originalPassword = testUser.getPassword();
            setupSecurityContext(1L);
            when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
            when(userRepository.save(any(User.class))).thenReturn(testUser);

            userService.updateCurrentUserProfile(updateDTO);

            assertEquals(originalPassword, testUser.getPassword());
        }
    }

    @Nested
    @DisplayName("Get User By ID Tests")
    class GetUserByIdTests {

        @Test
        @DisplayName("Should get user by ID successfully")
        void getUserById_Success() {
            when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

            UserResponseDTO result = userService.getUserById(1L);

            assertNotNull(result);
            assertEquals(1L, result.getId());
            assertEquals(testUser.getEmail(), result.getEmail());
        }

        @Test
        @DisplayName("Should throw exception when user not found")
        void getUserById_NotFound_ThrowsException() {
            when(userRepository.findById(999L)).thenReturn(Optional.empty());

            UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
                userService.getUserById(999L);
            });
            assertTrue(exception.getMessage().contains("999"));
        }

        @ParameterizedTest
        @ValueSource(longs = {1L, 50L, 100L, 999L})
        @DisplayName("Should handle different user IDs")
        void getUserById_DifferentIds(Long userId) {
            testUser.setId(userId);
            when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));

            UserResponseDTO result = userService.getUserById(userId);

            assertEquals(userId, result.getId());
        }

        @Test
        @DisplayName("Should return complete user information")
        void getUserById_ReturnsCompleteInfo() {
            when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

            UserResponseDTO result = userService.getUserById(1L);

            assertNotNull(result.getId());
            assertNotNull(result.getEmail());
            assertNotNull(result.getFullName());
            assertNotNull(result.getRole());
            assertNotNull(result.getIsActive());
        }

        @Test
        @DisplayName("Should call repository findById method")
        void getUserById_CallsRepository() {
            when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

            userService.getUserById(1L);

            verify(userRepository).findById(1L);
        }
    }

    @Nested
    @DisplayName("Get All Users Tests")
    class GetAllUsersTests {

        @Test
        @DisplayName("Should return all users")
        void getAllUsers_ReturnsAllUsers() {
            User user2 = createTestUser(2L, "user2@test.com", "User Two", UserRole.FREELANCER, true);
            User user3 = createTestUser(3L, "user3@test.com", "User Three", UserRole.ADMIN, true);
            List<User> users = Arrays.asList(testUser, user2, user3);
            
            when(userRepository.findAll()).thenReturn(users);

            List<UserResponseDTO> result = userService.getAllUsers();

            assertEquals(3, result.size());
        }

        @Test
        @DisplayName("Should return empty list when no users")
        void getAllUsers_EmptyList() {
            when(userRepository.findAll()).thenReturn(Collections.emptyList());

            List<UserResponseDTO> result = userService.getAllUsers();

            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("Should map all user fields correctly")
        void getAllUsers_MapsFieldsCorrectly() {
            when(userRepository.findAll()).thenReturn(Arrays.asList(testUser));

            List<UserResponseDTO> result = userService.getAllUsers();

            assertEquals(testUser.getEmail(), result.get(0).getEmail());
            assertEquals(testUser.getFullName(), result.get(0).getFullName());
        }

        @Test
        @DisplayName("Should return users with different roles")
        void getAllUsers_DifferentRoles() {
            User admin = createTestUser(2L, "admin@test.com", "Admin", UserRole.ADMIN, true);
            User client = createTestUser(3L, "client@test.com", "Client", UserRole.CLIENT, true);
            User freelancer = createTestUser(4L, "freelancer@test.com", "Freelancer", UserRole.FREELANCER, true);
            
            when(userRepository.findAll()).thenReturn(Arrays.asList(admin, client, freelancer));

            List<UserResponseDTO> result = userService.getAllUsers();

            assertEquals(3, result.size());
            assertTrue(result.stream().anyMatch(u -> u.getRole() == UserRole.ADMIN));
            assertTrue(result.stream().anyMatch(u -> u.getRole() == UserRole.CLIENT));
            assertTrue(result.stream().anyMatch(u -> u.getRole() == UserRole.FREELANCER));
        }

        @Test
        @DisplayName("Should include both active and inactive users")
        void getAllUsers_ActiveAndInactive() {
            User activeUser = createTestUser(2L, "active@test.com", "Active", UserRole.CLIENT, true);
            User inactiveUser = createTestUser(3L, "inactive@test.com", "Inactive", UserRole.CLIENT, false);
            
            when(userRepository.findAll()).thenReturn(Arrays.asList(activeUser, inactiveUser));

            List<UserResponseDTO> result = userService.getAllUsers();

            assertEquals(2, result.size());
            assertTrue(result.stream().anyMatch(u -> u.getIsActive()));
            assertTrue(result.stream().anyMatch(u -> !u.getIsActive()));
        }

        @Test
        @DisplayName("Should call repository findAll method")
        void getAllUsers_CallsRepository() {
            when(userRepository.findAll()).thenReturn(Collections.emptyList());

            userService.getAllUsers();

            verify(userRepository).findAll();
        }
    }

    @Nested
    @DisplayName("Delete User Tests")
    class DeleteUserTests {

        @Test
        @DisplayName("Should delete user successfully")
        void deleteUser_Success() {
            when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
            doNothing().when(userRepository).delete(testUser);

            userService.deleteUser(1L);

            verify(userRepository).delete(testUser);
        }

        @Test
        @DisplayName("Should throw exception when user not found")
        void deleteUser_NotFound_ThrowsException() {
            when(userRepository.findById(999L)).thenReturn(Optional.empty());

            UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
                userService.deleteUser(999L);
            });
            assertTrue(exception.getMessage().contains("999"));
        }

        @ParameterizedTest
        @ValueSource(longs = {1L, 10L, 100L})
        @DisplayName("Should delete users with different IDs")
        void deleteUser_DifferentIds(Long userId) {
            testUser.setId(userId);
            when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
            doNothing().when(userRepository).delete(testUser);

            userService.deleteUser(userId);

            verify(userRepository).delete(testUser);
        }

        @Test
        @DisplayName("Should verify user exists before deletion")
        void deleteUser_VerifiesUserExists() {
            when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
            doNothing().when(userRepository).delete(testUser);

            userService.deleteUser(1L);

            verify(userRepository).findById(1L);
        }

        @Test
        @DisplayName("Should not delete when user not found")
        void deleteUser_NotFound_DoesNotDelete() {
            when(userRepository.findById(999L)).thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class, () -> {
                userService.deleteUser(999L);
            });
            verify(userRepository, never()).delete(any(User.class));
        }

        @Test
        @DisplayName("Should delete admin user")
        void deleteUser_AdminRole() {
            testUser.setRole(UserRole.ADMIN);
            when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
            doNothing().when(userRepository).delete(testUser);

            userService.deleteUser(1L);

            verify(userRepository).delete(testUser);
        }

        @Test
        @DisplayName("Should delete inactive user")
        void deleteUser_InactiveUser() {
            testUser.setIsActive(false);
            when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
            doNothing().when(userRepository).delete(testUser);

            userService.deleteUser(1L);

            verify(userRepository).delete(testUser);
        }
    }

    // Helper method
    private void setupSecurityContext(Long userId) {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userId);
    }
}