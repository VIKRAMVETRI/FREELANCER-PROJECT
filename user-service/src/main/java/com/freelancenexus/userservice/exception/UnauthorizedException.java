package com.freelancenexus.userservice.exception;

/**
 * UnauthorizedException
 *
 * <p>Runtime exception thrown when a user attempts to access a resource or perform
 * an action for which they do not have the necessary authentication or permissions.
 * This unchecked exception is intended to be handled by a global exception handler
 * and typically maps to HTTP 401 Unauthorized or 403 Forbidden responses depending
 * on the application's error mapping.</p>
 *
 * @since 1.0
 */
public class UnauthorizedException extends RuntimeException {
    /**
     * Constructs a new UnauthorizedException with the specified detail message.
     *
     * @param message detail message describing the authorization failure
     */
    public UnauthorizedException(String message) {
        super(message);
    }

    /**
     * Constructs a new UnauthorizedException with the specified detail message and cause.
     *
     * @param message detail message describing the authorization failure
     * @param cause the underlying cause of this exception
     */
    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}