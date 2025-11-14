package com.freelancenexus.userservice.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionClassesTest {

    @Test
    void userNotFoundException_WithMessage() {
        // Arrange
        String message = "User not found with id: 1";

        // Act
        UserNotFoundException exception = new UserNotFoundException(message);

        // Assert
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void userNotFoundException_WithMessageAndCause() {
        // Arrange
        String message = "User not found";
        Throwable cause = new RuntimeException("Database error");

        // Act
        UserNotFoundException exception = new UserNotFoundException(message, cause);

        // Assert
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void duplicateResourceException_WithMessage() {
        // Arrange
        String message = "User with email test@example.com already exists";

        // Act
        DuplicateResourceException exception = new DuplicateResourceException(message);

        // Assert
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void duplicateResourceException_WithMessageAndCause() {
        // Arrange
        String message = "Duplicate resource";
        Throwable cause = new RuntimeException("Constraint violation");

        // Act
        DuplicateResourceException exception = new DuplicateResourceException(message, cause);

        // Assert
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void unauthorizedException_WithMessage() {
        // Arrange
        String message = "User not authenticated";

        // Act
        UnauthorizedException exception = new UnauthorizedException(message);

        // Assert
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void unauthorizedException_WithMessageAndCause() {
        // Arrange
        String message = "Unauthorized access";
        Throwable cause = new RuntimeException("Token expired");

        // Act
        UnauthorizedException exception = new UnauthorizedException(message, cause);

        // Assert
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void authenticationException_WithMessage() {
        // Arrange
        String message = "Invalid credentials";

        // Act
        AuthenticationException exception = new AuthenticationException(message);

        // Assert
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void authenticationException_WithMessageAndCause() {
        // Arrange
        String message = "Authentication failed";
        Throwable cause = new RuntimeException("Password mismatch");

        // Act
        AuthenticationException exception = new AuthenticationException(message, cause);

        // Assert
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void allExceptions_AreRuntimeExceptions() {
        // Assert
        assertTrue(new UserNotFoundException("test") instanceof RuntimeException);
        assertTrue(new DuplicateResourceException("test") instanceof RuntimeException);
        assertTrue(new UnauthorizedException("test") instanceof RuntimeException);
        assertTrue(new AuthenticationException("test") instanceof RuntimeException);
    }
}