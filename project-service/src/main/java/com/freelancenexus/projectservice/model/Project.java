package com.freelancenexus.projectservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Project
 *
 * <p>JPA entity representing a freelance project posted by a client.
 * Stores project details, budget information, required skills, status,
 * and relationships to proposals and milestones.</p>
 *
 * <p>A project can have multiple proposals from different freelancers
 * and can be broken down into multiple milestones for progressive delivery.</p>
 *
 * @since 1.0
 */
@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    /**
     * Primary key identifier for the project.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique identifier of the client who created and owns the project.
     */
    @Column(name = "client_id", nullable = false)
    private Long clientId;

    /**
     * Project title (required). Typically short and descriptive.
     */
    @Column(nullable = false)
    private String title;

    /**
     * Detailed project description including requirements, scope, and deliverables.
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * Minimum budget for the project. Precision: 10 digits total, 2 after decimal.
     */
    @Column(name = "budget_min", precision = 10, scale = 2)
    private BigDecimal budgetMin;

    /**
     * Maximum budget for the project. Precision: 10 digits total, 2 after decimal.
     */
    @Column(name = "budget_max", precision = 10, scale = 2)
    private BigDecimal budgetMax;

    /**
     * Estimated project duration in days.
     */
    @Column(name = "duration_days")
    private Integer durationDays;

    /**
     * Comma-separated or JSON array of required skills stored as a string.
     * Examples: "Java,Spring,AWS" or "[\"Java\", \"Spring\", \"AWS\"]"
     */
    @Column(name = "required_skills", columnDefinition = "TEXT")
    private String requiredSkills;

    /**
     * Project category (e.g., "Web Development", "Mobile App", "Design").
     * Max length: 100 characters.
     */
    @Column(length = 100)
    private String category;

    /**
     * Current status of the project (OPEN, IN_PROGRESS, COMPLETED, CANCELLED).
     * Default value is OPEN.
     *
     * @see ProjectStatus
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectStatus status = ProjectStatus.OPEN;

    /**
     * Project deadline date. Optional field.
     */
    private LocalDate deadline;

    /**
     * Unique identifier of the assigned freelancer (if any).
     * Null if no freelancer has been assigned yet.
     */
    @Column(name = "assigned_freelancer")
    private Long assignedFreelancer;

    /**
     * Timestamp when the project was created. Managed by Hibernate.
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp when the project was last updated. Managed by Hibernate.
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * One-to-many relationship with Proposal entities.
     * A project can have multiple proposals from different freelancers.
     * Cascade delete ensures proposals are removed when the project is deleted.
     *
     * @see Proposal
     */
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Proposal> proposals = new ArrayList<>();

    /**
     * One-to-many relationship with ProjectMilestone entities.
     * A project can be broken down into multiple milestones for phased delivery.
     * Cascade delete ensures milestones are removed when the project is deleted.
     *
     * @see ProjectMilestone
     */
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectMilestone> milestones = new ArrayList<>();
}