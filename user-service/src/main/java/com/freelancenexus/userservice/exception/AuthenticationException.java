package com.freelancenexus.userservice.exception;

/**
 * AuthenticationException
 *
 * <p>Runtime exception thrown when an authentication-related error occurs in the User Service.
 * Typical causes include invalid credentials, expired or malformed tokens, or other failures
 * during authentication processing.</p>
 *
 * <p>This unchecked exception is intended to be handled by global exception handlers that
 * translate it into appropriate HTTP responses (for example, 401 Unauthorized).</p>
 *
 * @since 1.0
 */
public class AuthenticationException extends RuntimeException {
    /**
     * Create a new AuthenticationException with a descriptive message.
     *
     * @param message detail message describing the authentication failure
     */
    public AuthenticationException(String message) {
        super(message);
    }

    /**
     * Create a new AuthenticationException with a descriptive message and an underlying cause.
     *
     * @param message detail message describing the authentication failure
     * @param cause the underlying cause of this exception
     */
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}