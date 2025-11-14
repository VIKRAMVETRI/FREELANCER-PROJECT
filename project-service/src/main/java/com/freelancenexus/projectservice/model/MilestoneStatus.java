package com.freelancenexus.projectservice.model;

/**
 * MilestoneStatus
 *
 * <p>Enumeration representing the lifecycle status of a project milestone.
 * Milestones are used to break down projects into manageable deliverables with
 * distinct completion states.</p>
 *
 * @since 1.0
 */
public enum MilestoneStatus {
    /**
     * Milestone has been created but work has not yet begun.
     */
    PENDING,
    
    /**
     * Work on the milestone is actively in progress.
     */
    IN_PROGRESS,
    
    /**
     * Milestone has been completed and deliverables accepted.
     */
    COMPLETED
}