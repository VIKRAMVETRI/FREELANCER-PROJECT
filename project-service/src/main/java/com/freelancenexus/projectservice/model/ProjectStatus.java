package com.freelancenexus.projectservice.model;

/**
 * ProjectStatus
 *
 * <p>Enumeration representing the lifecycle status of a project in the Freelance Nexus system.
 * Tracks the progression of a project from creation through completion or cancellation.</p>
 *
 * @since 1.0
 */
public enum ProjectStatus {
    /**
     * Project has been posted and is accepting proposals from freelancers.
     * Clients can still modify project details and no freelancer has been assigned yet.
     */
    OPEN,
    
    /**
     * Project is actively being worked on by an assigned freelancer.
     * Payment milestones may be released as work progresses.
     */
    IN_PROGRESS,
    
    /**
     * Project has been completed and all deliverables accepted.
     * Final payment has been released to the freelancer.
     */
    COMPLETED,
    
    /**
     * Project has been cancelled and will not proceed.
     * No further work is expected and participants are notified.
     */
    CANCELLED
}