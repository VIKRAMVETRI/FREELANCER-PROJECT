package com.freelancenexus.freelancer.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for representing a rating.
 * Includes details such as client ID, project ID, rating value, and review.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingDTO {
    
    /**
     * The unique identifier of the rating.
     */
    private Long id;
    
    /**
     * The ID of the client who provided the rating.
     * Must not be null.
     */
    @NotNull(message = "Client ID is required")
    private Long clientId;
    
    /**
     * The ID of the project associated with the rating.
     */
    private Long projectId;
    
    /**
     * The rating value.
     * Must be between 1 and 5.
     */
    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must not exceed 5")
    private Integer rating;
    
    /**
     * The review text provided by the client.
     * Cannot exceed 2000 characters.
     */
    @Size(max = 2000, message = "Review must not exceed 2000 characters")
    private String review;
    
    /**
     * The timestamp when the rating was created.
     */
    private LocalDateTime createdAt;
}