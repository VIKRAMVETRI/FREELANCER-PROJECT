package com.freelancenexus.freelancer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a skill associated with a freelancer.
 */
@Entity
@Table(name = "skills")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Skill {
    
    /**
     * The unique identifier of the skill.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * The freelancer associated with this skill.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "freelancer_id", nullable = false)
    private Freelancer freelancer;
    
    /**
     * The name of the skill.
     */
    @Column(name = "skill_name", nullable = false, length = 100)
    private String skillName;
    
    /**
     * The proficiency level of the skill.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "proficiency_level", length = 50)
    private ProficiencyLevel proficiencyLevel;
    
    /**
     * The number of years of experience with the skill.
     */
    @Column(name = "years_experience")
    private Integer yearsExperience;
    
    /**
     * Enum representing the proficiency levels for a skill.
     */
    public enum ProficiencyLevel {
        BEGINNER,
        INTERMEDIATE,
        EXPERT
    }
}