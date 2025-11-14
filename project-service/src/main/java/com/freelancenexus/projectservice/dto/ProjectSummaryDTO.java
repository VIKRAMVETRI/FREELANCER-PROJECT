package com.freelancenexus.projectservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ProjectSummaryDTO
 *
 * <p>Data Transfer Object representing an AI-generated summary of a project.
 * Contains concise summaries, key requirements, ideal candidate profile, and
 * complexity assessment produced by the AI service.</p>
 *
 * <p>Returned by AI summary endpoints to provide clients and freelancers with
 * quick, AI-generated insights about a project.</p>
 *
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectSummaryDTO {
    
    /**
     * Unique identifier of the project being summarized.
     */
    private Long projectId;
    
    /**
     * AI-generated concise summary of the project's objectives and scope.
     */
    private String summary;
    
    /**
     * AI-extracted key requirements and deliverables for the project.
     */
    private String keyRequirements;
    
    /**
     * AI-generated description of the ideal candidate profile
     * (experience level, skill set, background).
     */
    private String idealCandidate;
    
    /**
     * AI-assessed complexity level of the project
     * (e.g., "Low", "Medium", "High", "Very High").
     */
    private String estimatedComplexity;
    
    /**
     * List of suggested skills recommended by AI for the project,
     * based on analysis of the project description and requirements.
     */
    private java.util.List<String> suggestedSkills;
}