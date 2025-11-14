package com.freelancenexus.userservice.exception;

/**
 * UserNotFoundException
 *
 * <p>Runtime exception thrown when a requested user cannot be found in the system.
 * Common scenarios include lookup by ID or email when the resource does not exist.
 * This unchecked exception is intended to be handled by a global exception handler
 * and typically translated into an HTTP 404 Not Found response.</p>
 *
 * @since 1.0
 */
public class UserNotFoundException extends RuntimeException {
    /**
     * Constructs a new UserNotFoundException with the specified detail message.
     *
     * @param message detail message describing the missing user condition
     */
    public UserNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new UserNotFoundException with the specified detail message and cause.
     *
     * @param message detail message describing the missing user condition
     * @param cause the underlying cause of this exception
     */
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}