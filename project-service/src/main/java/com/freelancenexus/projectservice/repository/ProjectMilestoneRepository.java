package com.freelancenexus.projectservice.repository;

import com.freelancenexus.projectservice.model.MilestoneStatus;
import com.freelancenexus.projectservice.model.ProjectMilestone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ProjectMilestoneRepository
 *
 * <p>Spring Data JPA repository for performing CRUD and query operations on {@link ProjectMilestone} entities.
 * Provides convenience query methods for retrieving milestones by project and filtering by status.
 * Implementations are provided automatically by Spring Data at runtime.</p>
 *
 * @since 1.0
 */
@Repository
public interface ProjectMilestoneRepository extends JpaRepository<ProjectMilestone, Long> {

    /**
     * Find all milestones associated with a specific project.
     *
     * @param projectId the unique identifier of the project
     * @return a list of milestones for the project (may be empty)
     */
    List<ProjectMilestone> findByProjectId(Long projectId);

    /**
     * Find all milestones for a specific project filtered by their status.
     *
     * @param projectId the unique identifier of the project
     * @param status the {@link MilestoneStatus} to filter by
     * @return a list of milestones with the specified status (may be empty)
     */
    List<ProjectMilestone> findByProjectIdAndStatus(Long projectId, MilestoneStatus status);
}