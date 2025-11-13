package com.freelancenexus.userservice.exception;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserNotFoundExceptionTest {

    @Test
    void shouldCreateExceptionWithMessage() {
        String message = "User not found with id: 1";
        UserNotFoundException exception = new UserNotFoundException(message);

        assertEquals(message, exception.getMessage());
    }

    @Test
    void shouldCreateExceptionWithMessageAndCause() {
        String message = "User not found";
        Throwable cause = new RuntimeException("Database error");
        UserNotFoundException exception = new UserNotFoundException(message, cause);

        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void shouldBeInstanceOfRuntimeException() {
        UserNotFoundException exception = new UserNotFoundException("Test");
        assertTrue(exception instanceof RuntimeException);
    }
}