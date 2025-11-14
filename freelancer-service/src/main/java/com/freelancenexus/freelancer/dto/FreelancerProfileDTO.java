package com.freelancenexus.freelancer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object (DTO) for representing a freelancer's complete profile.
 * Includes details such as skills, portfolios, ratings, and earnings.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FreelancerProfileDTO {
    
    /**
     * The unique identifier of the freelancer profile.
     */
    private Long id;

    /**
     * The user ID associated with the freelancer.
     */
    private Long userId;

    /**
     * The title or headline of the freelancer profile.
     */
    private String title;

    /**
     * The biography or description of the freelancer.
     */
    private String bio;

    /**
     * The hourly rate of the freelancer.
     */
    private BigDecimal hourlyRate;

    /**
     * The availability status of the freelancer.
     */
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
     * The list of portfolio items associated with the freelancer.
     */
    private List<PortfolioDTO> portfolios;

    /**
     * The list of recent ratings received by the freelancer.
     */
    private List<RatingDTO> recentRatings;
    
    /**
     * The timestamp when the freelancer profile was created.
     */
    private LocalDateTime createdAt;

    /**
     * The timestamp when the freelancer profile was last updated.
     */
    private LocalDateTime updatedAt;
}