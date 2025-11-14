package com.freelancenexus.freelancer.exception;

/**
 * Exception thrown when attempting to add a duplicate rating for a freelancer.
 */
public class DuplicateRatingException extends RuntimeException {

    /**
     * Constructs a new DuplicateRatingException with the specified detail message.
     *
     * @param message the detail message
     */
    public DuplicateRatingException(String message) {
        super(message);
    }
}