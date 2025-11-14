package com.freelancenexus.freelancer.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object (DTO) for representing a freelancer.
 * Includes details such as title, bio, hourly rate, and skills.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FreelancerDTO {
    
    /**
     * The unique identifier of the freelancer.
     */
    private Long id;
    
    /**
     * The user ID associated with the freelancer.
     * Must not be null.
     */
    @NotNull(message = "User ID is required")
    private Long userId;
    
    /**
     * The title or headline of the freelancer profile.
     * Must not be blank and cannot exceed 255 characters.
     */
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;
    
    /**
     * The biography or description of the freelancer.
     * Cannot exceed 5000 characters.
     */
    @Size(max = 5000, message = "Bio must not exceed 5000 characters")
    private String bio;
    
    /**
     * The hourly rate of the freelancer.
     * Must be positive and follow a valid format.
     */
    @DecimalMin(value = "0.0", inclusive = false, message = "Hourly rate must be positive")
    @Digits(integer = 8, fraction = 2, message = "Invalid hourly rate format")
    private BigDecimal hourlyRate;
    
    /**
     * The availability status of the freelancer.
     * Must match one of the predefined statuses.
     */
    @Pattern(regexp = "AVAILABLE|BUSY|UNAVAILABLE", message = "Invalid availability status")
    private String availability;
    
    /**
     * The total earnings of the freelancer.
     */
    private BigDecimal totalEarnings;

    /**
     * The number of completed projects by the freelancer.
     */
    private Integer completedProjects;

    /**
     * The average rating of the freelancer.
     */
    private BigDecimal averageRating;
    
    /**
     * The list of skills associated with the freelancer.
     */
    private List<SkillDTO> skills;
    
    /**
     * The timestamp when the freelancer profile was created.
     */
    private LocalDateTime createdAt;

    /**
     * The timestamp when the freelancer profile was last updated.
     */
    private LocalDateTime updatedAt;
}