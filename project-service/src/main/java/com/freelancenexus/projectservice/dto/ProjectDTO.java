package com.freelancenexus.projectservice.dto;

import com.freelancenexus.projectservice.model.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * ProjectDTO
 *
 * <p>Data Transfer Object representing project information returned by API endpoints.
 * Contains project details, status, budget information, required skills, and metadata.
 * Excludes sensitive information and is used for transferring project data to clients.</p>
 *
 * <p>Typically returned by project retrieval, creation, and update endpoints.</p>
 *
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {
    
    /**
     * Unique identifier of the project.
     */
    private Long id;
    
    /**
     * Unique identifier of the client who created the project.
     */
    private Long clientId;
    
    /**
     * Project title.
     */
    private String title;
    
    /**
     * Detailed description of the project and its requirements.
     */
    private String description;
    
    /**
     * Minimum budget amount for the project.
     */
    private BigDecimal budgetMin;
    
    /**
     * Maximum budget amount for the project.
     */
    private BigDecimal budgetMax;
    
    /**
     * Estimated project duration in days.
     */
    private Integer durationDays;
    
    /**
     * List of required skills to complete the project.
     */
    private List<String> requiredSkills;
    
    /**
     * Project category (e.g., "Web Development", "Mobile App", "Design").
     */
    private String category;
    
    /**
     * Current status of the project (e.g., OPEN, IN_PROGRESS, COMPLETED).
     *
     * @see ProjectStatus
     */
    private ProjectStatus status;
    
    /**
     * Project deadline date.
     */
    private LocalDate deadline;
    
    /**
     * Unique identifier of the freelancer assigned to the project (if any).
     * {@code null} if no freelancer is assigned.
     */
    private Long assignedFreelancer;
    
    /**
     * Timestamp when the project was created.
     */
    private LocalDateTime createdAt;
    
    /**
     * Timestamp when the project was last updated.
     */
    private LocalDateTime updatedAt;
    
    /**
     * Number of proposals submitted for this project.
     */
    private Integer proposalCount;
}