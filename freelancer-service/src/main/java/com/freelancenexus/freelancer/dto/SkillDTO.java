package com.freelancenexus.freelancer.dto;

import com.freelancenexus.freelancer.model.Skill.ProficiencyLevel;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for representing a skill.
 * Includes details such as skill name, proficiency level, and years of experience.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillDTO {
    
    /**
     * The unique identifier of the skill.
     */
    private Long id;
    
    /**
     * The name of the skill.
     * Must not be blank and cannot exceed 100 characters.
     */
    @NotBlank(message = "Skill name is required")
    @Size(max = 100, message = "Skill name must not exceed 100 characters")
    private String skillName;
    
    /**
     * The proficiency level of the skill.
     * Must not be null.
     */
    @NotNull(message = "Proficiency level is required")
    private ProficiencyLevel proficiencyLevel;
    
    /**
     * The number of years of experience with the skill.
     * Must be a non-negative value and cannot exceed 50 years.
     */
    @Min(value = 0, message = "Years of experience cannot be negative")
    @Max(value = 50, message = "Years of experience seems too high")
    private Integer yearsExperience;
}