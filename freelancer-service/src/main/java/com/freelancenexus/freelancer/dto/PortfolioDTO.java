package com.freelancenexus.freelancer.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for representing a portfolio item.
 * Includes details such as title, description, URLs, and completion date.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioDTO {
    
    /**
     * The unique identifier of the portfolio item.
     */
    private Long id;
    
    /**
     * The title of the portfolio item.
     * Must not be blank and cannot exceed 255 characters.
     */
    @NotBlank(message = "Portfolio title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;
    
    /**
     * A description of the portfolio item.
     * Cannot exceed 5000 characters.
     */
    @Size(max = 5000, message = "Description must not exceed 5000 characters")
    private String description;
    
    /**
     * The URL of the project associated with the portfolio item.
     * Cannot exceed 500 characters.
     */
    @Size(max = 500, message = "Project URL must not exceed 500 characters")
    private String projectUrl;
    
    /**
     * The URL of the image associated with the portfolio item.
     * Cannot exceed 500 characters.
     */
    @Size(max = 500, message = "Image URL must not exceed 500 characters")
    private String imageUrl;
    
    /**
     * A description of the technologies used in the portfolio item.
     * Cannot exceed 1000 characters.
     */
    @Size(max = 1000, message = "Technologies used must not exceed 1000 characters")
    private String technologiesUsed;
    
    /**
     * The completion date of the portfolio item.
     * Must be in the past or present.
     */
    @PastOrPresent(message = "Completion date cannot be in the future")
    private LocalDate completionDate;
    
    /**
     * The timestamp when the portfolio item was created.
     */
    private LocalDateTime createdAt;
}