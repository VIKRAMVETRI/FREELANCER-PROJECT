package com.freelancenexus.userservice.exception;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UnauthorizedExceptionTest {

    @Test
    void shouldCreateExceptionWithMessage() {
        String message = "Invalid email or password";
        UnauthorizedException exception = new UnauthorizedException(message);

        assertEquals(message, exception.getMessage());
    }

    @Test
    void shouldCreateExceptionWithMessageAndCause() {
        String message = "Unauthorized access";
        Throwable cause = new RuntimeException("Authentication failed");
        UnauthorizedException exception = new UnauthorizedException(message, cause);

        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void shouldBeInstanceOfRuntimeException() {
        UnauthorizedException exception = new UnauthorizedException("Test");
        assertTrue(exception instanceof RuntimeException);
    }
}