package com.freelancenexus.freelancer.exception;

/**
 * Exception thrown when attempting to create a freelancer that already exists.
 */
public class FreelancerAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new FreelancerAlreadyExistsException with the specified detail message.
     *
     * @param message the detail message
     */
    public FreelancerAlreadyExistsException(String message) {
        super(message);
    }
}