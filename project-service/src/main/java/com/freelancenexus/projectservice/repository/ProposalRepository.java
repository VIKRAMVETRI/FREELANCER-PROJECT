package com.freelancenexus.projectservice.repository;

import com.freelancenexus.projectservice.model.Proposal;
import com.freelancenexus.projectservice.model.ProposalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * ProposalRepository
 *
 * <p>Spring Data JPA repository for performing CRUD and query operations on {@link Proposal} entities.
 * Provides convenience query methods for filtering proposals by project, freelancer, and status.
 * Includes custom JPQL queries for ranking proposals by AI score and retrieval by various criteria.
 * Implementations are provided automatically by Spring Data at runtime.</p>
 *
 * @since 1.0
 */
@Repository
public interface ProposalRepository extends JpaRepository<Proposal, Long> {

    /**
     * Find all proposals submitted for a specific project.
     *
     * @param projectId the unique identifier of the project
     * @return a list of proposals for the project (may be empty)
     */
    List<Proposal> findByProjectId(Long projectId);

    /**
     * Find all proposals submitted by a specific freelancer.
     *
     * @param freelancerId the unique identifier of the freelancer
     * @return a list of proposals submitted by the freelancer (may be empty)
     */
    List<Proposal> findByFreelancerId(Long freelancerId);

    /**
     * Find all proposals for a specific project filtered by status.
     *
     * @param projectId the unique identifier of the project
     * @param status the {@link ProposalStatus} to filter by
     * @return a list of proposals matching the criteria (may be empty)
     */
    List<Proposal> findByProjectIdAndStatus(Long projectId, ProposalStatus status);

    /**
     * Find a unique proposal from a specific freelancer for a specific project.
     *
     * @param projectId the unique identifier of the project
     * @param freelancerId the unique identifier of the freelancer
     * @return an {@link Optional} containing the proposal if found, or empty if not
     */
    Optional<Proposal> findByProjectIdAndFreelancerId(Long projectId, Long freelancerId);

    /**
     * Find all proposals for a project ranked by AI score in descending order,
     * with older submissions prioritized when scores are equal.
     *
     * @param projectId the unique identifier of the project
     * @return a list of proposals sorted by AI score (highest first) and submission time (oldest first)
     */
    @Query("SELECT p FROM Proposal p WHERE p.project.id = :projectId ORDER BY p.aiScore DESC, p.submittedAt ASC")
    List<Proposal> findByProjectIdOrderByAiScoreDesc(@Param("projectId") Long projectId);

    /**
     * Check whether a specific freelancer has submitted a proposal for a specific project.
     *
     * @param projectId the unique identifier of the project
     * @param freelancerId the unique identifier of the freelancer
     * @return true if a proposal exists, false otherwise
     */
    boolean existsByProjectIdAndFreelancerId(Long projectId, Long freelancerId);

    /**
     * Count the total number of proposals submitted for a specific project.
     *
     * @param projectId the unique identifier of the project
     * @return the count of proposals for the project
     */
    long countByProjectId(Long projectId);
}