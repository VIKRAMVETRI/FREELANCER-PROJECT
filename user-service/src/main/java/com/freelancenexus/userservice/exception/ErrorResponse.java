package com.freelancenexus.userservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * ErrorResponse
 *
 * <p>Data Transfer Object used to represent structured error information returned by the API.
 * Intended to be produced by global exception handlers to provide consistent HTTP error payloads
 * to clients.</p>
 *
 * <p>Fields:
 * <ul>
 *   <li>{@code timestamp} — when the error occurred</li>
 *   <li>{@code status} — HTTP status code</li>
 *   <li>{@code error} — short error description (e.g. "Not Found")</li>
 *   <li>{@code message} — detailed error message</li>
 *   <li>{@code path} — request path that produced the error</li>
 * </ul>
 * </p>
 *
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    /**
     * Timestamp indicating when the error occurred.
     */
    private LocalDateTime timestamp;

    /**
     * HTTP status code associated with the error (e.g. 400, 401, 404, 500).
     */
    private int status;

    /**
     * Short, human-readable error type or reason (e.g. "Bad Request", "Unauthorized").
     */
    private String error;

    /**
     * Detailed error message suitable for client display or debugging.
     */
    private String message;

    /**
     * The request path (URI) that produced the error.
     */
    private String path;
}