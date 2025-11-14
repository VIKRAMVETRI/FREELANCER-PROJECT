package com.freelancenexus.userservice.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler exceptionHandler;

    @Mock
    private WebRequest webRequest;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        when(webRequest.getDescription(false)).thenReturn("uri=/api/users/test");
    }

    @Test
    void handleUserNotFoundException_ReturnsNotFound() {
        // Arrange
        UserNotFoundException exception = new UserNotFoundException("User not found with id: 1");

        // Act
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleUserNotFoundException(exception, webRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());
        assertEquals("Not Found", response.getBody().getError());
        assertEquals("User not found with id: 1", response.getBody().getMessage());
        assertEquals("uri=/api/users/test", response.getBody().getPath());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void handleDuplicateResourceException_ReturnsConflict() {
        // Arrange
        DuplicateResourceException exception = new DuplicateResourceException("User with email test@example.com already exists");

        // Act
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleDuplicateResourceException(exception, webRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CONFLICT.value(), response.getBody().getStatus());
        assertEquals("Conflict", response.getBody().getError());
        assertEquals("User with email test@example.com already exists", response.getBody().getMessage());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void handleUnauthorizedException_ReturnsUnauthorized() {
        // Arrange
        UnauthorizedException exception = new UnauthorizedException("User not authenticated");

        // Act
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleUnauthorizedException(exception, webRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getBody().getStatus());
        assertEquals("Unauthorized", response.getBody().getError());
        assertEquals("User not authenticated", response.getBody().getMessage());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void handleAuthenticationException_ReturnsUnauthorized() {
        // Arrange
        AuthenticationException exception = new AuthenticationException("Invalid credentials");

        // Act
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleAuthenticationException(exception, webRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getBody().getStatus());
        assertEquals("Unauthorized", response.getBody().getError());
        assertEquals("Invalid credentials", response.getBody().getMessage());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void handleValidationException_ReturnsBadRequest() {
        // Arrange
        FieldError fieldError1 = new FieldError("userRegistrationDTO", "email", "Email is required");
        FieldError fieldError2 = new FieldError("userRegistrationDTO", "password", "Password must be at least 8 characters");
        List<FieldError> fieldErrors = Arrays.asList(fieldError1, fieldError2);

        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        when(exception.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

        // Act
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleValidationException(exception, webRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
        assertEquals("Bad Request", response.getBody().getError());
        assertTrue(response.getBody().getMessage().contains("email: Email is required"));
        assertTrue(response.getBody().getMessage().contains("password: Password must be at least 8 characters"));
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void handleValidationException_SingleFieldError() {
        // Arrange
        FieldError fieldError = new FieldError("userLoginDTO", "email", "Email should be valid");
        List<FieldError> fieldErrors = Arrays.asList(fieldError);

        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        when(exception.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

        // Act
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleValidationException(exception, webRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("email: Email should be valid", response.getBody().getMessage());
    }

    @Test
    void handleGlobalException_ReturnsInternalServerError() {
        // Arrange
        Exception exception = new Exception("Unexpected error occurred");

        // Act
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleGlobalException(exception, webRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatus());
        assertEquals("Internal Server Error", response.getBody().getError());
        assertEquals("An unexpected error occurred", response.getBody().getMessage());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void handleGlobalException_RuntimeException() {
        // Arrange
        RuntimeException exception = new RuntimeException("Runtime error");

        // Act
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleGlobalException(exception, webRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred", response.getBody().getMessage());
    }

    @Test
    void handleGlobalException_NullPointerException() {
        // Arrange
        NullPointerException exception = new NullPointerException("Null value encountered");

        // Act
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleGlobalException(exception, webRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void errorResponse_ContainsAllRequiredFields() {
        // Arrange
        UserNotFoundException exception = new UserNotFoundException("Test error");

        // Act
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleUserNotFoundException(exception, webRequest);

        // Assert
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertNotNull(errorResponse.getTimestamp());
        assertTrue(errorResponse.getStatus() > 0);
        assertNotNull(errorResponse.getError());
        assertNotNull(errorResponse.getMessage());
        assertNotNull(errorResponse.getPath());
    }
}