package com.freelancenexus.projectservice.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
/**
 * Data Transfer Object (DTO) for transferring ranked proposal data.
 * Contains details about the proposal, its ranking, and AI analysis.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankedProposalDTO {

    /**
     * The unique identifier of the proposal.
     */
    private Long id;

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
     * The rank of the proposal based on AI analysis.
     */
    private Integer rank;

    /**
     * The AI-generated analysis of the proposal.
     */
    private String aiAnalysis;

    /**
     * The list of strengths identified in the proposal.
     */
    private java.util.List<String> strengths;

    /**
     * The list of concerns identified in the proposal.
     */
    private java.util.List<String> concerns;

    /**
     * The timestamp when the proposal was submitted.
     */
    private LocalDateTime submittedAt;
}