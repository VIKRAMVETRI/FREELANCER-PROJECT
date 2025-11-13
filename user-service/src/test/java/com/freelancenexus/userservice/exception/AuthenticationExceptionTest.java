package com.freelancenexus.userservice.exception;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationExceptionTest {

    @Test
    void shouldCreateExceptionWithMessage() {
        String message = "Authentication failed";
        AuthenticationException exception = new AuthenticationException(message);

        assertEquals(message, exception.getMessage());
    }

    @Test
    void shouldCreateExceptionWithMessageAndCause() {
        String message = "Authentication error";
        Throwable cause = new RuntimeException("Token expired");
        AuthenticationException exception = new AuthenticationException(message, cause);

        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void shouldBeInstanceOfRuntimeException() {
        AuthenticationException exception = new AuthenticationException("Test");
        assertTrue(exception instanceof RuntimeException);
    }
}
