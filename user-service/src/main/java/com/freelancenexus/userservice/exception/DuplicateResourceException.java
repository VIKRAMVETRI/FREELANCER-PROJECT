package com.freelancenexus.userservice.exception;

/**
 * DuplicateResourceException
 *
 * <p>Runtime exception thrown when an attempt is made to create or persist a resource
 * that already exists (for example, registering a user with an email that is already taken).
 * This unchecked exception is intended to be translated by a global exception handler
 * into an appropriate HTTP response (typically 409 Conflict).</p>
 *
 * @since 1.0
 */
public class DuplicateResourceException extends RuntimeException {
    /**
     * Constructs a new DuplicateResourceException with the specified detail message.
     *
     * @param message detail message describing the duplicate resource condition
     */
    public DuplicateResourceException(String message) {
        super(message);
    }

    /**
     * Constructs a new DuplicateResourceException with the specified detail message and cause.
     *
     * @param message detail message describing the duplicate resource condition
     * @param cause the underlying cause of this exception
     */
    public DuplicateResourceException(String message, Throwable cause) {
        super(message, cause);
    }
}