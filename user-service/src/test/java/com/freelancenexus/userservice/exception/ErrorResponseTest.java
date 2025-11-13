package com.freelancenexus.userservice.exception;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {

    @Test
    void shouldCreateErrorResponse() {
        LocalDateTime timestamp = LocalDateTime.now();
        ErrorResponse errorResponse = new ErrorResponse(
                timestamp,
                404,
                "Not Found",
                "User not found",
                "/api/users/1"
        );

        assertEquals(timestamp, errorResponse.getTimestamp());
        assertEquals(404, errorResponse.getStatus());
        assertEquals("Not Found", errorResponse.getError());
        assertEquals("User not found", errorResponse.getMessage());
        assertEquals("/api/users/1", errorResponse.getPath());
    }

    @Test
    void shouldTestGettersAndSetters() {
        ErrorResponse errorResponse = new ErrorResponse();
        LocalDateTime timestamp = LocalDateTime.now();

        errorResponse.setTimestamp(timestamp);
        errorResponse.setStatus(500);
        errorResponse.setError("Internal Server Error");
        errorResponse.setMessage("Unexpected error");
        errorResponse.setPath("/api/users");

        assertEquals(timestamp, errorResponse.getTimestamp());
        assertEquals(500, errorResponse.getStatus());
        assertEquals("Internal Server Error", errorResponse.getError());
        assertEquals("Unexpected error", errorResponse.getMessage());
        assertEquals("/api/users", errorResponse.getPath());
    }

    @Test
    void shouldTestNoArgsConstructor() {
        ErrorResponse errorResponse = new ErrorResponse();
        assertNotNull(errorResponse);
    }

    @Test
    void shouldTestEqualsAndHashCode() {
        LocalDateTime timestamp = LocalDateTime.now();
        ErrorResponse error1 = new ErrorResponse(timestamp, 404, "Not Found", "User not found", "/api/users");
        ErrorResponse error2 = new ErrorResponse(timestamp, 404, "Not Found", "User not found", "/api/users");

        assertEquals(error1, error2);
        assertEquals(error1.hashCode(), error2.hashCode());
    }

    @Test
    void shouldTestToString() {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(), 404, "Not Found", "User not found", "/api/users"
        );

        String toString = errorResponse.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("404"));
        assertTrue(toString.contains("User not found"));
    }
}






