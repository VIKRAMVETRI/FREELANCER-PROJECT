package com.freelancenexus.projectservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * AIRecommendationDTO
 *
 * <p>Data Transfer Object representing an AI-recommended project for a freelancer.
 * Contains project details, required skills, and AI-computed match scoring metrics
 * that indicate how well the project aligns with the freelancer's profile.</p>
 *
 * <p>Returned by AI recommendation endpoints to help freelancers discover suitable projects.</p>
 *
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AIRecommendationDTO {
    
    /**
     * Unique identifier of the recommended project.
     */
    private Long projectId;
    
    /**
     * Title/name of the project.
     */
    private String projectTitle;
    
    /**
     * Project category (e.g., "Web Development", "Mobile App", "Design").
     */
    private String category;
    
    /**
     * Minimum budget amount for the project.
     */
    private BigDecimal budgetMin;
    
    /**
     * Maximum budget amount for the project.
     */
    private BigDecimal budgetMax;
    
    /**
     * Estimated duration of the project in days.
     */
    private Integer durationDays;
    
    /**
     * List of skills required to complete the project.
     */
    private List<String> requiredSkills;
    
    /**
     * AI-computed match score on a scale of 0-100 indicating overall project compatibility.
     * Higher scores indicate better alignment with the freelancer's profile.
     */
    private BigDecimal matchScore;
    
    /**
     * Human-readable explanation of why this project is recommended
     * (e.g., reasons for the match score).
     */
    private String matchReason;
    
    /**
     * List of freelancer skills that match the project's required skills.
     */
    private List<String> matchingSkills;
    
    /**
     * Percentage of project-required skills that the freelancer possesses (0-100).
     */
    private Integer skillMatchPercentage;
}