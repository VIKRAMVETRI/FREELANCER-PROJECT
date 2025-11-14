package com.freelancenexus.userservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freelancenexus.userservice.dto.*;
import com.freelancenexus.userservice.model.User;
import com.freelancenexus.userservice.model.UserRole;
import com.freelancenexus.userservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class UserServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void fullUserFlow_RegisterLoginUpdateProfile() throws Exception {
        // Step 1: Register a new user
        UserRegistrationDTO registrationDTO = new UserRegistrationDTO();
        registrationDTO.setEmail("integration@example.com");
        registrationDTO.setPassword("password123");
        registrationDTO.setFullName("Integration Test User");
        registrationDTO.setPhone("1234567890");
        registrationDTO.setRole(UserRole.CLIENT);

        MvcResult registerResult = mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registrationDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("integration@example.com"))
                .andExpect(jsonPath("$.fullName").value("Integration Test User"))
                .andReturn();

        UserResponseDTO registeredUser = objectMapper.readValue(
            registerResult.getResponse().getContentAsString(),
            UserResponseDTO.class
        );
        assertNotNull(registeredUser.getId());

        // Step 2: Login with the registered user
        UserLoginDTO loginDTO = new UserLoginDTO();
        loginDTO.setEmail("integration@example.com");
        loginDTO.setPassword("password123");

        MvcResult loginResult = mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.user.email").value("integration@example.com"))
                .andReturn();

        LoginResponseDTO loginResponse = objectMapper.readValue(
            loginResult.getResponse().getContentAsString(),
            LoginResponseDTO.class
        );
        String token = loginResponse.getAccessToken();
        assertNotNull(token);

        // Step 3: Get current user profile with token
        mockMvc.perform(get("/api/users/profile")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("integration@example.com"));

        // Step 4: Update profile
        UserUpdateDTO updateDTO = new UserUpdateDTO();
        updateDTO.setFullName("Updated Integration User");
        updateDTO.setPhone("9876543210");

        mockMvc.perform(put("/api/users/profile")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Updated Integration User"))
                .andExpect(jsonPath("$.phone").value("9876543210"));
    }

    @Test
    void registerUser_DuplicateEmail_ReturnsConflict() throws Exception {
        // Step 1: Register first user
        UserRegistrationDTO registrationDTO = new UserRegistrationDTO();
        registrationDTO.setEmail("duplicate@example.com");
        registrationDTO.setPassword("password123");
        registrationDTO.setFullName("First User");
        registrationDTO.setRole(UserRole.CLIENT);

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registrationDTO)))
                .andExpect(status().isCreated());

        // Step 2: Try to register with same email
        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registrationDTO)))
                .andExpect(status().isConflict());
    }

    @Test
    void login_InvalidCredentials_ReturnsUnauthorized() throws Exception {
        // Register user first
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword(passwordEncoder.encode("correctpassword"));
        user.setFullName("Test User");
        user.setRole(UserRole.CLIENT);
        user.setIsActive(true);
        userRepository.save(user);

        // Try to login with wrong password
        UserLoginDTO loginDTO = new UserLoginDTO();
        loginDTO.setEmail("test@example.com");
        loginDTO.setPassword("wrongpassword");

        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void accessProtectedEndpoint_WithoutToken_ReturnsUnauthorized() throws Exception {
        mockMvc.perform(get("/api/users/profile"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void accessProtectedEndpoint_WithInvalidToken_ReturnsUnauthorized() throws Exception {
        mockMvc.perform(get("/api/users/profile")
                .header("Authorization", "Bearer invalid.token.here"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void adminOperations_GetAllUsersAndDelete() throws Exception {
        // Create admin user
        User adminUser = new User();
        adminUser.setEmail("admin@example.com");
        adminUser.setPassword(passwordEncoder.encode("adminpass"));
        adminUser.setFullName("Admin User");
        adminUser.setRole(UserRole.ADMIN);
        adminUser.setIsActive(true);
        userRepository.save(adminUser);

        // Create regular user
        User regularUser = new User();
        regularUser.setEmail("user@example.com");
        regularUser.setPassword(passwordEncoder.encode("userpass"));
        regularUser.setFullName("Regular User");
        regularUser.setRole(UserRole.CLIENT);
        regularUser.setIsActive(true);
        User savedUser = userRepository.save(regularUser);

        // Login as admin
        UserLoginDTO loginDTO = new UserLoginDTO();
        loginDTO.setEmail("admin@example.com");
        loginDTO.setPassword("adminpass");

        MvcResult loginResult = mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andReturn();

        LoginResponseDTO loginResponse = objectMapper.readValue(
            loginResult.getResponse().getContentAsString(),
            LoginResponseDTO.class
        );
        String adminToken = loginResponse.getAccessToken();

        // Get all users
        mockMvc.perform(get("/api/users")
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));

        // Delete user
        mockMvc.perform(delete("/api/users/" + savedUser.getId())
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isNoContent());

        // Verify user is deleted
        assertFalse(userRepository.findById(savedUser.getId()).isPresent());
    }

    @Test
    void getUserById_ValidUser_ReturnsUser() throws Exception {
        // Create user
        User user = new User();
        user.setEmail("viewable@example.com");
        user.setPassword(passwordEncoder.encode("password"));
        user.setFullName("Viewable User");
        user.setRole(UserRole.CLIENT);
        user.setIsActive(true);
        User savedUser = userRepository.save(user);

        // Login
        UserLoginDTO loginDTO = new UserLoginDTO();
        loginDTO.setEmail("viewable@example.com");
        loginDTO.setPassword("password");

        MvcResult loginResult = mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andReturn();

        LoginResponseDTO loginResponse = objectMapper.readValue(
            loginResult.getResponse().getContentAsString(),
            LoginResponseDTO.class
        );
        String token = loginResponse.getAccessToken();

        // Get user by ID
        mockMvc.perform(get("/api/users/" + savedUser.getId())
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedUser.getId()))
                .andExpect(jsonPath("$.email").value("viewable@example.com"));
    }

    @Test
    void login_InactiveUser_ReturnsUnauthorized() throws Exception {
        // Create inactive user
        User user = new User();
        user.setEmail("inactive@example.com");
        user.setPassword(passwordEncoder.encode("password"));
        user.setFullName("Inactive User");
        user.setRole(UserRole.CLIENT);
        user.setIsActive(false);
        userRepository.save(user);

        // Try to login
        UserLoginDTO loginDTO = new UserLoginDTO();
        loginDTO.setEmail("inactive@example.com");
        loginDTO.setPassword("password");

        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void updateProfile_PartialUpdate_OnlyUpdatesProvidedFields() throws Exception {
        // Register and login
        UserRegistrationDTO registrationDTO = new UserRegistrationDTO();
        registrationDTO.setEmail("partial@example.com");
        registrationDTO.setPassword("password123");
        registrationDTO.setFullName("Original Name");
        registrationDTO.setPhone("1111111111");
        registrationDTO.setRole(UserRole.CLIENT);

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registrationDTO)))
                .andExpect(status().isCreated());

        UserLoginDTO loginDTO = new UserLoginDTO();
        loginDTO.setEmail("partial@example.com");
        loginDTO.setPassword("password123");

        MvcResult loginResult = mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andReturn();

        LoginResponseDTO loginResponse = objectMapper.readValue(
            loginResult.getResponse().getContentAsString(),
            LoginResponseDTO.class
        );
        String token = loginResponse.getAccessToken();

        // Update only full name
        UserUpdateDTO updateDTO = new UserUpdateDTO();
        updateDTO.setFullName("New Name");

        mockMvc.perform(put("/api/users/profile")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("New Name"))
                .andExpect(jsonPath("$.phone").value("1111111111")); // Phone should remain unchanged
    }
}