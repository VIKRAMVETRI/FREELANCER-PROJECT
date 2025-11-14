package com.freelancenexus.projectservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freelancenexus.projectservice.dto.ProjectCreateDTO;
import com.freelancenexus.projectservice.dto.ProjectDTO;
import com.freelancenexus.projectservice.model.Project;
import com.freelancenexus.projectservice.model.ProjectStatus;
import com.freelancenexus.projectservice.repository.ProjectRepository;
import com.freelancenexus.projectservice.repository.ProposalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ProjectService
 *
 * <p>Core service layer for project management. Handles creation, retrieval, update,
 * assignment and soft-deletion (cancellation) of projects. Coordinates persistence
 * via {@link ProjectRepository}, proposal counting via {@link ProposalRepository},
 * and event publication to RabbitMQ. Methods that modify state are annotated with
 * {@link Transactional} to ensure data consistency.</p>
 *
 * <p>Typical responsibilities:
 * <ul>
 *   <li>Persist new projects</li>
 *   <li>Update existing projects</li>
 *   <li>Soft-delete (cancel) projects</li>
 *   <li>Assign freelancers to projects</li>
 *   <li>Convert between entity and DTO representations</li>
 *   <li>Publish domain events for downstream consumers</li>
 * </ul>
 * </p>
 *
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {

    /**
     * Repository for project CRUD operations.
     */
    private final ProjectRepository projectRepository;

    /**
     * Repository for proposal access used to compute proposal counts and queries.
     */
    private final ProposalRepository proposalRepository;

    /**
     * RabbitTemplate used to publish project-related events to RabbitMQ.
     */
    private final RabbitTemplate rabbitTemplate;

    /**
     * Jackson ObjectMapper used to serialize/deserialize skill lists.
     */
    private final ObjectMapper objectMapper;

    /**
     * Exchange name for publishing project events (injected from configuration).
     */
    @Value("${rabbitmq.exchange.project}")
    private String projectExchange;

    /**
     * Routing key used for project.created events (injected from configuration).
     */
    @Value("${rabbitmq.routing.project.created}")
    private String projectCreatedRoutingKey;

    /**
     * Create a new project.
     *
     * <p>Persists the project entity, publishes a project.created event, and returns
     * a {@link ProjectDTO} representing the persisted project.</p>
     *
     * @param createDTO validated DTO containing project creation details
     * @return the created project's {@link ProjectDTO}
     */
    @Transactional
    public ProjectDTO createProject(ProjectCreateDTO createDTO) {
        log.info("Creating new project: {}", createDTO.getTitle());

        Project project = new Project();
        project.setClientId(createDTO.getClientId());
        project.setTitle(createDTO.getTitle());
        project.setDescription(createDTO.getDescription());
        project.setBudgetMin(createDTO.getBudgetMin());
        project.setBudgetMax(createDTO.getBudgetMax());
        project.setDurationDays(createDTO.getDurationDays());
        project.setRequiredSkills(convertSkillsToJson(createDTO.getRequiredSkills()));
        project.setCategory(createDTO.getCategory());
        project.setStatus(ProjectStatus.OPEN);
        project.setDeadline(createDTO.getDeadline());

        Project savedProject = projectRepository.save(project);
        log.info("Project created with ID: {}", savedProject.getId());

        // Publish event to RabbitMQ
        publishProjectCreatedEvent(savedProject);

        return convertToDTO(savedProject);
    }

    /**
     * Retrieve a project by its id.
     *
     * @param id unique identifier of the project
     * @return the project's {@link ProjectDTO}
     * @throws RuntimeException if the project is not found
     */
    public ProjectDTO getProjectById(Long id) {
        log.info("Fetching project by ID: {}", id);
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + id));
        return convertToDTO(project);
    }

    /**
     * Update an existing project.
     *
     * <p>Only fields present in {@link ProjectCreateDTO} are updated. The method
     * validates existence and persists changes within a transaction.</p>
     *
     * @param id unique identifier of the project to update
     * @param updateDTO DTO containing updated project fields
     * @return the updated project's {@link ProjectDTO}
     * @throws RuntimeException if the project is not found
     */
    @Transactional
    public ProjectDTO updateProject(Long id, ProjectCreateDTO updateDTO) {
        log.info("Updating project ID: {}", id);

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + id));

        project.setTitle(updateDTO.getTitle());
        project.setDescription(updateDTO.getDescription());
        project.setBudgetMin(updateDTO.getBudgetMin());
        project.setBudgetMax(updateDTO.getBudgetMax());
        project.setDurationDays(updateDTO.getDurationDays());
        project.setRequiredSkills(convertSkillsToJson(updateDTO.getRequiredSkills()));
        project.setCategory(updateDTO.getCategory());
        project.setDeadline(updateDTO.getDeadline());

        Project updatedProject = projectRepository.save(project);
        return convertToDTO(updatedProject);
    }

    /**
     * Soft-delete (cancel) a project.
     *
     * <p>Marks the project's status as {@link ProjectStatus#CANCELLED} rather than
     * removing it from the database, allowing historical data and associated proposals
     * to remain for audit and reporting.</p>
     *
     * @param id unique identifier of the project to cancel
     * @throws RuntimeException if the project is not found
     */
    @Transactional
    public void deleteProject(Long id) {
        log.info("Deleting project ID: {}", id);
        
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + id));
        
        project.setStatus(ProjectStatus.CANCELLED);
        projectRepository.save(project);
    }

    /**
     * Retrieve all projects.
     *
     * @return list of {@link ProjectDTO} for all projects
     */
    public List<ProjectDTO> getAllProjects() {
        log.info("Fetching all projects");
        return projectRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve projects created by a specific client.
     *
     * @param clientId unique identifier of the client
     * @return list of {@link ProjectDTO} for that client
     */
    public List<ProjectDTO> getProjectsByClientId(Long clientId) {
        log.info("Fetching projects for client ID: {}", clientId);
        return projectRepository.findByClientId(clientId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve all open projects available for bidding.
     *
     * @return list of open {@link ProjectDTO}
     */
    public List<ProjectDTO> getOpenProjects() {
        log.info("Fetching all open projects");
        return projectRepository.findAllOpenProjects().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Search projects by keyword and optional status.
     *
     * <p>Performs a case-insensitive keyword search against title and description,
     * filtering by provided status (defaults to OPEN when status is null).</p>
     *
     * @param keyword search keyword
     * @param status optional status string (e.g., "OPEN")
     * @return list of matching {@link ProjectDTO}
     * @throws IllegalArgumentException if provided status is invalid
     */
    public List<ProjectDTO> searchProjects(String keyword, String status) {
        log.info("Searching projects with keyword: {} and status: {}", keyword, status);
        
        ProjectStatus projectStatus = status != null ? ProjectStatus.valueOf(status.toUpperCase()) : ProjectStatus.OPEN;
        
        return projectRepository.searchByKeywordAndStatus(keyword, projectStatus).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve projects by category.
     *
     * @param category the project category
     * @return list of {@link ProjectDTO} in the specified category
     */
    public List<ProjectDTO> getProjectsByCategory(String category) {
        log.info("Fetching projects by category: {}", category);
        return projectRepository.findByCategory(category).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Assign a freelancer to a project and transition the project to IN_PROGRESS.
     *
     * @param projectId the project id
     * @param freelancerId the freelancer id to assign
     * @return the updated project's {@link ProjectDTO}
     * @throws RuntimeException if the project is not found
     */
    @Transactional
    public ProjectDTO assignFreelancer(Long projectId, Long freelancerId) {
        log.info("Assigning freelancer {} to project {}", freelancerId, projectId);
        
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + projectId));
        
        project.setAssignedFreelancer(freelancerId);
        project.setStatus(ProjectStatus.IN_PROGRESS);
        
        Project updatedProject = projectRepository.save(project);
        return convertToDTO(updatedProject);
    }

    /**
     * Convert a {@link Project} entity to a {@link ProjectDTO}.
     *
     * <p>Maps entity fields to the DTO and computes the current proposal count.</p>
     *
     * @param project the entity to convert
     * @return the mapped {@link ProjectDTO}
     */
    private ProjectDTO convertToDTO(Project project) {
        ProjectDTO dto = new ProjectDTO();
        dto.setId(project.getId());
        dto.setClientId(project.getClientId());
        dto.setTitle(project.getTitle());
        dto.setDescription(project.getDescription());
        dto.setBudgetMin(project.getBudgetMin());
        dto.setBudgetMax(project.getBudgetMax());
        dto.setDurationDays(project.getDurationDays());
        dto.setRequiredSkills(parseSkillsFromJson(project.getRequiredSkills()));
        dto.setCategory(project.getCategory());
        dto.setStatus(project.getStatus());
        dto.setDeadline(project.getDeadline());
        dto.setAssignedFreelancer(project.getAssignedFreelancer());
        dto.setCreatedAt(project.getCreatedAt());
        dto.setUpdatedAt(project.getUpdatedAt());
        dto.setProposalCount((int) proposalRepository.countByProjectId(project.getId()));
        return dto;
    }

    /**
     * Serialize a list of skills to a JSON string for storage.
     *
     * @param skills list of skill strings
     * @return JSON string representation of skills, or "[]" on error
     */
    private String convertSkillsToJson(List<String> skills) {
        try {
            return objectMapper.writeValueAsString(skills);
        } catch (Exception e) {
            log.error("Error converting skills to JSON", e);
            return "[]";
        }
    }

    /**
     * Parse a stored skills JSON string back into a list of skills.
     *
     * <p>Attempts JSON parsing and falls back to comma-splitting on error.</p>
     *
     * @param skillsJson the stored skills string
     * @return list of skill strings
     */
    private List<String> parseSkillsFromJson(String skillsJson) {
        try {
            return objectMapper.readValue(skillsJson, List.class);
        } catch (Exception e) {
            log.error("Error parsing skills from JSON", e);
            return Arrays.asList(skillsJson.split(","));
        }
    }

    /**
     * Publish a project.created event to RabbitMQ.
     *
     * <p>Converts the project to {@link ProjectDTO} and sends it to the configured exchange
     * with the configured routing key. Exceptions are logged but do not prevent the caller
     * from continuing.</p>
     *
     * @param project the project that was created
     */
    private void publishProjectCreatedEvent(Project project) {
        try {
            ProjectDTO dto = convertToDTO(project);
            rabbitTemplate.convertAndSend(projectExchange, projectCreatedRoutingKey, dto);
            log.info("Published project.created event for project ID: {}", project.getId());
        } catch (Exception e) {
            log.error("Error publishing project.created event", e);
        }
    }
}