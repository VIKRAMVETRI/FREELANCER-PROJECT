package com.freelancenexus.projectservice.repository;

import com.freelancenexus.projectservice.model.Project;
import com.freelancenexus.projectservice.model.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ProjectRepository
 *
 * <p>Spring Data JPA repository for performing CRUD and query operations on {@link Project} entities.
 * Provides convenience query methods for filtering projects by client, status, category, and search criteria.
 * Includes custom JPQL queries for complex filtering and retrieval scenarios.
 * Implementations are provided automatically by Spring Data at runtime.</p>
 *
 * @since 1.0
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    /**
     * Find all projects created by a specific client.
     *
     * @param clientId the unique identifier of the client
     * @return a list of projects created by the client (may be empty)
     */
    List<Project> findByClientId(Long clientId);

    /**
     * Find all projects with a specific status.
     *
     * @param status the {@link ProjectStatus} to filter by
     * @return a list of projects with the specified status (may be empty)
     */
    List<Project> findByStatus(ProjectStatus status);

    /**
     * Find all projects in a specific category.
     *
     * @param category the project category to filter by
     * @return a list of projects in the specified category (may be empty)
     */
    List<Project> findByCategory(String category);

    /**
     * Search for projects by keyword in title or description with optional status filter.
     *
     * <p>Performs case-insensitive partial matching on title and description fields.</p>
     *
     * @param keyword the search keyword
     * @param status the {@link ProjectStatus} to filter by
     * @return a list of matching projects (may be empty)
     */
    @Query("SELECT p FROM Project p WHERE p.status = :status AND " +
           "(LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Project> searchByKeywordAndStatus(@Param("keyword") String keyword, 
                                           @Param("status") ProjectStatus status);

    /**
     * Find all open projects ordered by creation date (newest first).
     *
     * <p>Returns projects available for freelancers to bid on.</p>
     *
     * @return a list of open projects sorted by creation date descending
     */
    @Query("SELECT p FROM Project p WHERE p.status = 'OPEN' ORDER BY p.createdAt DESC")
    List<Project> findAllOpenProjects();

    /**
     * Find all projects assigned to a specific freelancer.
     *
     * @param freelancerId the unique identifier of the freelancer
     * @return a list of projects assigned to the freelancer (may be empty)
     */
    @Query("SELECT p FROM Project p WHERE p.assignedFreelancer = :freelancerId")
    List<Project> findByAssignedFreelancer(@Param("freelancerId") Long freelancerId);
}