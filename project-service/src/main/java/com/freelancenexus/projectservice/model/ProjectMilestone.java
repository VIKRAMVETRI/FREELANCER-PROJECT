package com.freelancenexus.projectservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * ProjectMilestone
 *
 * <p>JPA entity representing a milestone within a project. Milestones are used to
 * break down large projects into smaller, manageable deliverables with distinct
 * completion states and payment schedules.</p>
 *
 * <p>Each milestone tracks its own status, due date, and completion timestamp,
 * enabling progressive project delivery and payment release upon completion.</p>
 *
 * @since 1.0
 */
@Entity
@Table(name = "project_milestones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMilestone {

    /**
     * Primary key identifier for the milestone.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Many-to-one relationship with the parent Project entity.
     * Each milestone belongs to exactly one project.
     * Lazy fetch type for performance optimization.
     *
     * @see Project
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    /**
     * Milestone title (required). A short descriptive name for this deliverable.
     */
    @Column(nullable = false)
    private String title;

    /**
     * Detailed description of what this milestone entails and what deliverables are expected.
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * Payment amount for this milestone upon completion.
     * Precision: 10 digits total, 2 after decimal.
     */
    @Column(precision = 10, scale = 2)
    private BigDecimal amount;

    /**
     * Due date for completing this milestone deliverable.
     */
    @Column(name = "due_date")
    private LocalDate dueDate;

    /**
     * Current status of the milestone (PENDING, IN_PROGRESS, COMPLETED).
     * Default value is PENDING.
     *
     * @see MilestoneStatus
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MilestoneStatus status = MilestoneStatus.PENDING;

    /**
     * Timestamp when the milestone was marked as completed.
     * Null until the milestone reaches COMPLETED status.
     */
    @Column(name = "completed_at")
    private LocalDateTime completedAt;
}