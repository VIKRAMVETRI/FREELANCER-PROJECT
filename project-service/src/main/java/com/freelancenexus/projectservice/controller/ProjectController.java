package com.freelancenexus.projectservice.controller;

import com.freelancenexus.projectservice.dto.ProjectCreateDTO;
import com.freelancenexus.projectservice.dto.ProjectDTO;
import com.freelancenexus.projectservice.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ProjectController
 *
 * <p>REST API controller for project management in the Project Service.
 * Provides endpoints for creating, retrieving, updating, and deleting projects.
 * Implements role-based access control to restrict operations by user role.</p>
 *
 * <p>Endpoints are categorized by operation type:
 * <ul>
 *   <li><strong>Creation</strong> — clients create new projects</li>
 *   <li><strong>Retrieval</strong> — authenticated users view projects</li>
 *   <li><strong>Updates</strong> — clients update their projects</li>
 *   <li><strong>Deletion</strong> — clients and admins delete projects</li>
 *   <li><strong>Search/Filter</strong> — keyword, category, and status filtering</li>
 *   <li><strong>Assignment</strong> — clients assign freelancers to projects</li>
 * </ul>
 * </p>
 *
 * @since 1.0
 */
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Slf4j
public class ProjectController {

    private final ProjectService projectService;

    /**
     * Create a new project.
     *
     * <p>Requires CLIENT role. Accepts validated project creation details and
     * persists the new project to the database.</p>
     *
     * @param createDTO project creation details
     * @return ResponseEntity with HTTP 201 (Created) status and the created {@link ProjectDTO}
     */
    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ProjectDTO> createProject(@Valid @RequestBody ProjectCreateDTO createDTO) {
        log.info("POST /api/projects - Creating project: {}", createDTO.getTitle());
        ProjectDTO createdProject = projectService.createProject(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
    }

    /**
     * Retrieve a project by id.
     *
     * <p>Requires authentication. Any authenticated user can view project details.</p>
     *
     * @param id the unique identifier of the project
     * @return ResponseEntity with HTTP 200 (OK) status and the {@link ProjectDTO}
     */
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ProjectDTO> getProject(@PathVariable Long id) {
        log.info("GET /api/projects/{} - Fetching project", id);
        ProjectDTO project = projectService.getProjectById(id);
        return ResponseEntity.ok(project);
    }

    /**
     * Update an existing project.
     *
     * <p>Requires CLIENT role. Only the client who created the project can update it.</p>
     *
     * @param id the unique identifier of the project to update
     * @param updateDTO updated project details
     * @return ResponseEntity with HTTP 200 (OK) status and the updated {@link ProjectDTO}
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ProjectDTO> updateProject(
            @PathVariable Long id,
            @Valid @RequestBody ProjectCreateDTO updateDTO) {
        log.info("PUT /api/projects/{} - Updating project", id);
        ProjectDTO updatedProject = projectService.updateProject(id, updateDTO);
        return ResponseEntity.ok(updatedProject);
    }

    /**
     * Delete a project by id.
     *
     * <p>Requires CLIENT or ADMIN role. Only the project owner (client) or an admin can delete.</p>
     *
     * @param id the unique identifier of the project to delete
     * @return ResponseEntity with HTTP 204 (No Content) status
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CLIENT', 'ADMIN')")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        log.info("DELETE /api/projects/{} - Deleting project", id);
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieve all projects with optional filtering.
     *
     * <p>Requires authentication. Supports filtering by keyword, status, and category.
     * Returns open projects by default if status="OPEN" is specified.</p>
     *
     * @param keyword optional keyword for searching project titles/descriptions
     * @param status optional status filter (e.g., "OPEN", "IN_PROGRESS")
     * @param category optional category filter
     * @return ResponseEntity with HTTP 200 (OK) status and a list of {@link ProjectDTO}
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ProjectDTO>> getAllProjects(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String category) {
        log.info("GET /api/projects - Fetching projects with filters");
        
        List<ProjectDTO> projects;
        
        if (keyword != null && !keyword.isEmpty()) {
            projects = projectService.searchProjects(keyword, status);
        } else if (category != null && !category.isEmpty()) {
            projects = projectService.getProjectsByCategory(category);
        } else if ("OPEN".equalsIgnoreCase(status)) {
            projects = projectService.getOpenProjects();
        } else {
            projects = projectService.getAllProjects();
        }
        
        return ResponseEntity.ok(projects);
    }

    /**
     * Retrieve all projects created by a specific client.
     *
     * <p>Requires CLIENT role.</p>
     *
     * @param clientId the unique identifier of the client
     * @return ResponseEntity with HTTP 200 (OK) status and a list of {@link ProjectDTO}
     */
    @GetMapping("/client/{clientId}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<ProjectDTO>> getClientProjects(@PathVariable Long clientId) {
        log.info("GET /api/projects/client/{} - Fetching client projects", clientId);
        List<ProjectDTO> projects = projectService.getProjectsByClientId(clientId);
        return ResponseEntity.ok(projects);
    }

    /**
     * Assign a freelancer to a project.
     *
     * <p>Requires CLIENT role. Only the client who created the project can assign a freelancer.</p>
     *
     * @param projectId the unique identifier of the project
     * @param freelancerId the unique identifier of the freelancer to assign
     * @return ResponseEntity with HTTP 200 (OK) status and the updated {@link ProjectDTO}
     */
    @PutMapping("/{projectId}/assign/{freelancerId}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ProjectDTO> assignFreelancer(
            @PathVariable Long projectId,
            @PathVariable Long freelancerId) {
        log.info("PUT /api/projects/{}/assign/{} - Assigning freelancer", projectId, freelancerId);
        ProjectDTO project = projectService.assignFreelancer(projectId, freelancerId);
        return ResponseEntity.ok(project);
    }

    /**
     * Global exception handler for RuntimeException in this controller.
     *
     * @param ex the thrown {@link RuntimeException}
     * @return ResponseEntity with HTTP 400 (Bad Request) status and error message
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        log.error("Error in ProjectController", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}