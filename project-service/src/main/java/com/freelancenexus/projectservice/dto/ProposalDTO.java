package com.freelancenexus.projectservice.dto;

import com.freelancenexus.projectservice.model.ProposalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for transferring proposal data.
 * Contains all the fields required for representing a proposal.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProposalDTO {

    /**
     * The unique identifier of the proposal.
     */
    private Long id;

    /**
     * The ID of the project associated with the proposal.
     */
    private Long projectId;

    /**
     * The title of the project associated with the proposal.
     */
    private String projectTitle;

    /**
     * The ID of the freelancer who submitted the proposal.
     */
    private Long freelancerId;

    /**
     * The cover letter submitted with the proposal.
     */
    private String coverLetter;

    /**
     * The budget proposed by the freelancer.
     */
    private BigDecimal proposedBudget;

    /**
     * The number of days proposed for delivery.
     */
    private Integer deliveryDays;

    /**
     * The AI-generated score for the proposal (0-100).
     */
    private BigDecimal aiScore;

    /**
     * The current status of the proposal.
     */
    private ProposalStatus status;

    /**
     * The timestamp when the proposal was submitted.
     */
    private LocalDateTime submittedAt;

    /**
     * The timestamp when the proposal was last updated.
     */
    private LocalDateTime updatedAt;
}