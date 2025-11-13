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
import org.springframework.test.annotation.DirtiesContext;


import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class UserServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserRegistrationDTO registrationDTO;
    private User testUser;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        registrationDTO = new UserRegistrationDTO(
                "integration@test.com",
                "password123",
                "Integration Test User",
                "1234567890",
                UserRole.CLIENT,
                null
        );

        testUser = new User();
        testUser.setEmail("existing@test.com");
        testUser.setPassword(passwordEncoder.encode("password123"));
        testUser.setFullName("Existing User");
        testUser.setPhone("9876543210");
        testUser.setRole(UserRole.CLIENT);
        testUser.setIsActive(true);
    }

    @Test
    void shouldRegisterUserSuccessfully() throws Exception {
        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(registrationDTO.getEmail()))
                .andExpect(jsonPath("$.fullName").value(registrationDTO.getFullName()))
                .andExpect(jsonPath("$.role").value(registrationDTO.getRole().name()))
                .andExpect(jsonPath("$.isActive").value(true))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.createdAt").exists());
    }

    @Test
    void shouldFailRegistrationWithDuplicateEmail() throws Exception {
        userRepository.save(testUser);

        UserRegistrationDTO duplicateDTO = new UserRegistrationDTO(
                "existing@test.com",
                "password123",
                "Another User",
                "1111111111",
                UserRole.CLIENT,
                null
        );

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(duplicateDTO)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value(containsString("already exists")));
    }

    @Test
    void shouldFailRegistrationWithInvalidEmail() throws Exception {
        registrationDTO.setEmail("invalid-email");

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldLoginSuccessfully() throws Exception {
        User savedUser = userRepository.save(testUser);

        UserLoginDTO loginDTO = new UserLoginDTO("existing@test.com", "password123");

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.expiresIn").exists())
                .andExpect(jsonPath("$.user.email").value("existing@test.com"))
                .andExpect(jsonPath("$.user.id").value(savedUser.getId()));
    }

    @Test
    void shouldFailLoginWithWrongPassword() throws Exception {
        userRepository.save(testUser);

        UserLoginDTO loginDTO = new UserLoginDTO("existing@test.com", "wrongpassword");

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value(containsString("Invalid email or password")));
    }

    @Test
    void shouldFailLoginWithNonExistentUser() throws Exception {
        UserLoginDTO loginDTO = new UserLoginDTO("nonexistent@test.com", "password123");

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldFailLoginWithInactiveUser() throws Exception {
        testUser.setIsActive(false);
        userRepository.save(testUser);

        UserLoginDTO loginDTO = new UserLoginDTO("existing@test.com", "password123");

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value(containsString("inactive")));
    }

    @Test
    void shouldGetUserProfileWithValidToken() throws Exception {
        User savedUser = userRepository.save(testUser);

        // First login to get token
        UserLoginDTO loginDTO = new UserLoginDTO("existing@test.com", "password123");
        String loginResponse = mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andReturn().getResponse().getContentAsString();

        LoginResponseDTO loginResponseDTO = objectMapper.readValue(loginResponse, LoginResponseDTO.class);
        String token = loginResponseDTO.getAccessToken();

        // Get profile
        mockMvc.perform(get("/api/users/profile")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("existing@test.com"))
                .andExpect(jsonPath("$.id").value(savedUser.getId()));
    }

    @Test
    void shouldUpdateUserProfileWithValidToken() throws Exception {
        userRepository.save(testUser);

        // Login
        UserLoginDTO loginDTO = new UserLoginDTO("existing@test.com", "password123");
        String loginResponse = mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andReturn().getResponse().getContentAsString();

        LoginResponseDTO loginResponseDTO = objectMapper.readValue(loginResponse, LoginResponseDTO.class);
        String token = loginResponseDTO.getAccessToken();

        // Update profile
        UserUpdateDTO updateDTO = new UserUpdateDTO("Updated Name", "5555555555", null);

        mockMvc.perform(put("/api/users/profile")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Updated Name"))
                .andExpect(jsonPath("$.phone").value("5555555555"));
    }

    @Test
    void shouldFailToAccessProtectedEndpointWithoutToken() throws Exception {
        mockMvc.perform(get("/api/users/profile"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldFailToAccessProtectedEndpointWithInvalidToken() throws Exception {
        mockMvc.perform(get("/api/users/profile")
                        .header("Authorization", "Bearer invalid.token.here"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldGetUserByIdWithValidToken() throws Exception {
        User savedUser = userRepository.save(testUser);

        // Login
        UserLoginDTO loginDTO = new UserLoginDTO("existing@test.com", "password123");
        String loginResponse = mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andReturn().getResponse().getContentAsString();

        LoginResponseDTO loginResponseDTO = objectMapper.readValue(loginResponse, LoginResponseDTO.class);
        String token = loginResponseDTO.getAccessToken();

        // Get user by ID
        mockMvc.perform(get("/api/users/" + savedUser.getId())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedUser.getId()))
                .andExpect(jsonPath("$.email").value("existing@test.com"));
    }

    @Test
    void shouldReturn404ForNonExistentUserId() throws Exception {
        userRepository.save(testUser);

        // Login
        UserLoginDTO loginDTO = new UserLoginDTO("existing@test.com", "password123");
        String loginResponse = mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andReturn().getResponse().getContentAsString();

        LoginResponseDTO loginResponseDTO = objectMapper.readValue(loginResponse, LoginResponseDTO.class);
        String token = loginResponseDTO.getAccessToken();

        // Try to get non-existent user
        mockMvc.perform(get("/api/users/9999")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldRegisterMultipleUsersWithDifferentRoles() throws Exception {
        // Register CLIENT
        UserRegistrationDTO clientDTO = new UserRegistrationDTO(
                "client@test.com", "password123", "Client User", "1111111111", UserRole.CLIENT, null);
        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.role").value("CLIENT"));

        // Register FREELANCER
        UserRegistrationDTO freelancerDTO = new UserRegistrationDTO(
                "freelancer@test.com", "password123", "Freelancer User", "2222222222", UserRole.FREELANCER, null);
        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(freelancerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.role").value("FREELANCER"));

        // Register ADMIN
        UserRegistrationDTO adminDTO = new UserRegistrationDTO(
                "admin@test.com", "password123", "Admin User", "3333333333", UserRole.ADMIN, null);
        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(adminDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.role").value("ADMIN"));
    }

    @Test
    void shouldValidateMinimumPasswordLength() throws Exception {
        registrationDTO.setPassword("short");

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(containsString("Password")));
    }

    @Test
    void shouldValidateFullNameLength() throws Exception {
        registrationDTO.setFullName("A");

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationDTO)))
                .andExpect(status().isBadRequest());
    }
}