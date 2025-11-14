package com.freelancenexus.projectservice.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * ProjectCreateDTO
 *
 * <p>Data Transfer Object for creating a new project. Contains all required and optional
 * information needed to set up a project in the system. All fields are validated for
 * presence, format, and business logic constraints.</p>
 *
 * <p>Typically sent by clients via POST requests to create new projects.</p>
 *
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectCreateDTO {

    /**
     * Unique identifier of the client creating the project.
     * Must be provided and must reference a valid client.
     *
     * @validation NotNull
     */
    @NotNull(message = "Client ID is required")
    private Long clientId;

    /**
     * Project title. Must be unique and descriptive.
     * Cannot exceed 255 characters.
     *
     * @validation NotBlank, Size(max = 255)
     */
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    /**
     * Detailed description of the project, requirements, and deliverables.
     * Cannot be blank.
     *
     * @validation NotBlank
     */
    @NotBlank(message = "Description is required")
    private String description;

    /**
     * Minimum budget amount for the project.
     * Must be greater than 0.
     *
     * @validation NotNull, DecimalMin(0.0, exclusive)
     */
    @NotNull(message = "Minimum budget is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Minimum budget must be greater than 0")
    private BigDecimal budgetMin;

    /**
     * Maximum budget amount for the project.
     * Must be greater than 0.
     *
     * @validation NotNull, DecimalMin(0.0, exclusive)
     */
    @NotNull(message = "Maximum budget is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Maximum budget must be greater than 0")
    private BigDecimal budgetMax;

    /**
     * Estimated project duration in days.
     * Must be at least 1 day.
     *
     * @validation NotNull, Min(1)
     */
    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 day")
    private Integer durationDays;

    /**
     * List of required skills for the project (e.g., "Java", "React", "AWS").
     * At least one skill must be specified.
     *
     * @validation NotEmpty
     */
    @NotEmpty(message = "At least one skill is required")
    private List<String> requiredSkills;

    /**
     * Project category (e.g., "Web Development", "Mobile App", "Design").
     * Cannot be blank.
     *
     * @validation NotBlank
     */
    @NotBlank(message = "Category is required")
    private String category;

    /**
     * Project deadline date. Must be in the future.
     * Optional field.
     *
     * @validation Future
     */
    @Future(message = "Deadline must be in the future")
    private LocalDate deadline;
}