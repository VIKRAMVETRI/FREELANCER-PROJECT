package com.freelancenexus.userservice.exception;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DuplicateResourceExceptionTest {

    @Test
    void shouldCreateExceptionWithMessage() {
        String message = "User with email test@example.com already exists";
        DuplicateResourceException exception = new DuplicateResourceException(message);

        assertEquals(message, exception.getMessage());
    }

    @Test
    void shouldCreateExceptionWithMessageAndCause() {
        String message = "Duplicate resource";
        Throwable cause = new RuntimeException("Constraint violation");
        DuplicateResourceException exception = new DuplicateResourceException(message, cause);

        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void shouldBeInstanceOfRuntimeException() {
        DuplicateResourceException exception = new DuplicateResourceException("Test");
        assertTrue(exception instanceof RuntimeException);
    }
}
