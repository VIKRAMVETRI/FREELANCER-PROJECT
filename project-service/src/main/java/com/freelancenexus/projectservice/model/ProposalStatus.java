package com.freelancenexus.projectservice.model;

/**
 * ProposalStatus
 *
 * <p>Enumeration representing the lifecycle status of a freelancer's proposal.
 * Tracks the progression of a proposal from submission through client decision.</p>
 *
 * @since 1.0
 */
public enum ProposalStatus {
    /**
     * Proposal has been submitted and is awaiting client review.
     * The client can view, rank, and compare this proposal with others.
     */
    PENDING,
    
    /**
     * Proposal has been accepted by the client.
     * The associated freelancer is assigned to the project and work can begin.
     */
    ACCEPTED,
    
    /**
     * Proposal has been rejected by the client.
     * The freelancer will not be considered for this project.
     */
    REJECTED
}