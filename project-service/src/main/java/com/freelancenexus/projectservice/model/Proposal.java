package com.freelancenexus.projectservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Proposal
 *
 * <p>JPA entity representing a freelancer's proposal/bid for a project.
 * Stores proposal details including cover letter, budget, delivery timeline,
 * and AI-computed ranking score for client comparison.</p>
 *
 * <p>Proposals progress through a lifecycle: PENDING → ACCEPTED/REJECTED.
 * Multiple proposals can exist for a single project from different freelancers.</p>
 *
 * @since 1.0
 */
@Entity
@Table(name = "proposals")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Proposal {

    /**
     * Primary key identifier for the proposal.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Many-to-one relationship with the Project entity.
     * Each proposal is submitted for exactly one project.
     * Lazy fetch type for performance optimization.
     *
     * @see Project
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    /**
     * Unique identifier of the freelancer submitting the proposal.
     */
    @Column(name = "freelancer_id", nullable = false)
    private Long freelancerId;

    /**
     * Cover letter or motivation statement from the freelancer explaining
     * why they are a good fit for the project.
     */
    @Column(name = "cover_letter", columnDefinition = "TEXT")
    private String coverLetter;

    /**
     * Freelancer's proposed budget for completing the project.
     * Precision: 10 digits total, 2 after decimal.
     */
    @Column(name = "proposed_budget", precision = 10, scale = 2)
    private BigDecimal proposedBudget;

    /**
     * Estimated number of days the freelancer needs to complete the project.
     */
    @Column(name = "delivery_days")
    private Integer deliveryDays;

    /**
     * AI-computed ranking score on a scale of 0-100 indicating the proposal's
     * quality and fit for the project. Computed by the AI service for client comparison.
     * Precision: 5 digits total, 2 after decimal.
     */
    @Column(name = "ai_score", precision = 5, scale = 2)
    private BigDecimal aiScore;

    /**
     * Current status of the proposal (PENDING, ACCEPTED, REJECTED).
     * Default value is PENDING.
     *
     * @see ProposalStatus
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProposalStatus status = ProposalStatus.PENDING;

    /**
     * Timestamp when the proposal was submitted. Managed by Hibernate.
     */
    @CreationTimestamp
    @Column(name = "submitted_at", nullable = false, updatable = false)
    private LocalDateTime submittedAt;

    /**
     * Timestamp when the proposal was last updated. Managed by Hibernate.
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}