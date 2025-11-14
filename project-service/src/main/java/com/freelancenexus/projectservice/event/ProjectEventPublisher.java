package com.freelancenexus.projectservice.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * ProjectEventPublisher
 *
 * <p>Component responsible for publishing project and proposal-related events to RabbitMQ.
 * Events are sent to a centralized message broker for consumption by other microservices
 * and event handlers. Includes graceful error handling and logging for each event type.</p>
 *
 * <p>Event types include project creation, status changes, assignment, and proposal lifecycle events.</p>
 *
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ProjectEventPublisher {
    
    /**
     * RabbitTemplate for sending messages to RabbitMQ.
     * Injected via constructor (Lombok {@code @RequiredArgsConstructor}).
     */
    private final RabbitTemplate rabbitTemplate;
    
    /**
     * RabbitMQ exchange name where all project service events are published.
     */
    private static final String EXCHANGE = "freelance-nexus-exchange";
    
    /**
     * Publish a PROJECT_CREATED event when a new project is created.
     *
     * <p>Event data includes project id, client id, title, category, and timestamp.
     * Routed with key: {@code project.created}</p>
     *
     * @param projectId the id of the created project
     * @param clientId the id of the client who created the project
     * @param title the project title
     * @param category the project category
     */
    public void publishProjectCreatedEvent(Long projectId, Long clientId, String title, String category) {
        try {
            Map<String, Object> eventData = new HashMap<>();
            eventData.put("eventType", "PROJECT_CREATED");
            eventData.put("projectId", projectId);
            eventData.put("clientId", clientId);
            eventData.put("title", title);
            eventData.put("category", category);
            eventData.put("timestamp", LocalDateTime.now().toString());
            
            String routingKey = "project.created";
            
            rabbitTemplate.convertAndSend(EXCHANGE, routingKey, eventData);
            
            log.info("Published PROJECT_CREATED event for project: {} with routing key: {}", projectId, routingKey);
            
        } catch (Exception e) {
            log.error("Error publishing project created event for project {}: {}", projectId, e.getMessage());
        }
    }
    
    /**
     * Publish a PROPOSAL_SUBMITTED event when a freelancer submits a proposal.
     *
     * <p>Event data includes proposal id, project id, freelancer id, project title, and timestamp.
     * Routed with key: {@code proposal.submitted}</p>
     *
     * @param proposalId the id of the submitted proposal
     * @param projectId the id of the project
     * @param freelancerId the id of the freelancer submitting the proposal
     * @param projectTitle the title of the project
     */
    public void publishProposalSubmittedEvent(Long proposalId, Long projectId, Long freelancerId, String projectTitle) {
        try {
            Map<String, Object> eventData = new HashMap<>();
            eventData.put("eventType", "PROPOSAL_SUBMITTED");
            eventData.put("proposalId", proposalId);
            eventData.put("projectId", projectId);
            eventData.put("freelancerId", freelancerId);
            eventData.put("projectTitle", projectTitle);
            eventData.put("timestamp", LocalDateTime.now().toString());
            
            String routingKey = "proposal.submitted";
            
            rabbitTemplate.convertAndSend(EXCHANGE, routingKey, eventData);
            
            log.info("Published PROPOSAL_SUBMITTED event for proposal: {} on project: {} with routing key: {}", 
                     proposalId, projectId, routingKey);
            
        } catch (Exception e) {
            log.error("Error publishing proposal submitted event for proposal {}: {}", proposalId, e.getMessage());
        }
    }
    
    /**
     * Publish a PROPOSAL_ACCEPTED event when a client accepts a proposal.
     *
     * <p>Event data includes proposal id, project id, freelancer id, and timestamp.
     * Routed with key: {@code proposal.accepted}</p>
     *
     * @param proposalId the id of the accepted proposal
     * @param projectId the id of the project
     * @param freelancerId the id of the freelancer whose proposal was accepted
     */
    public void publishProposalAcceptedEvent(Long proposalId, Long projectId, Long freelancerId) {
        try {
            Map<String, Object> eventData = new HashMap<>();
            eventData.put("eventType", "PROPOSAL_ACCEPTED");
            eventData.put("proposalId", proposalId);
            eventData.put("projectId", projectId);
            eventData.put("freelancerId", freelancerId);
            eventData.put("timestamp", LocalDateTime.now().toString());
            
            String routingKey = "proposal.accepted";
            
            rabbitTemplate.convertAndSend(EXCHANGE, routingKey, eventData);
            
            log.info("Published PROPOSAL_ACCEPTED event for proposal: {} with routing key: {}", 
                     proposalId, routingKey);
            
        } catch (Exception e) {
            log.error("Error publishing proposal accepted event for proposal {}: {}", proposalId, e.getMessage());
        }
    }
    
    /**
     * Publish a PROPOSAL_REJECTED event when a client rejects a proposal.
     *
     * <p>Event data includes proposal id, project id, freelancer id, and timestamp.
     * Routed with key: {@code proposal.rejected}</p>
     *
     * @param proposalId the id of the rejected proposal
     * @param projectId the id of the project
     * @param freelancerId the id of the freelancer whose proposal was rejected
     */
    public void publishProposalRejectedEvent(Long proposalId, Long projectId, Long freelancerId) {
        try {
            Map<String, Object> eventData = new HashMap<>();
            eventData.put("eventType", "PROPOSAL_REJECTED");
            eventData.put("proposalId", proposalId);
            eventData.put("projectId", projectId);
            eventData.put("freelancerId", freelancerId);
            eventData.put("timestamp", LocalDateTime.now().toString());
            
            String routingKey = "proposal.rejected";
            
            rabbitTemplate.convertAndSend(EXCHANGE, routingKey, eventData);
            
            log.info("Published PROPOSAL_REJECTED event for proposal: {} with routing key: {}", 
                     proposalId, routingKey);
            
        } catch (Exception e) {
            log.error("Error publishing proposal rejected event for proposal {}: {}", proposalId, e.getMessage());
        }
    }
    
    /**
     * Publish a PROJECT_STATUS_CHANGED event when a project's status is modified.
     *
     * <p>Event data includes project id, old status, new status, and timestamp.
     * Routed with key: {@code project.status.changed}</p>
     *
     * @param projectId the id of the project
     * @param oldStatus the previous project status
     * @param newStatus the new project status
     */
    public void publishProjectStatusChangedEvent(Long projectId, String oldStatus, String newStatus) {
        try {
            Map<String, Object> eventData = new HashMap<>();
            eventData.put("eventType", "PROJECT_STATUS_CHANGED");
            eventData.put("projectId", projectId);
            eventData.put("oldStatus", oldStatus);
            eventData.put("newStatus", newStatus);
            eventData.put("timestamp", LocalDateTime.now().toString());
            
            String routingKey = "project.status.changed";
            
            rabbitTemplate.convertAndSend(EXCHANGE, routingKey, eventData);
            
            log.info("Published PROJECT_STATUS_CHANGED event for project: {} from {} to {} with routing key: {}", 
                     projectId, oldStatus, newStatus, routingKey);
            
        } catch (Exception e) {
            log.error("Error publishing project status changed event for project {}: {}", projectId, e.getMessage());
        }
    }
    
    /**
     * Publish a PROJECT_ASSIGNED event when a freelancer is assigned to a project.
     *
     * <p>Event data includes project id, freelancer id, client id, and timestamp.
     * Routed with key: {@code project.assigned}</p>
     *
     * @param projectId the id of the project
     * @param freelancerId the id of the assigned freelancer
     * @param clientId the id of the project's client
     */
    public void publishProjectAssignedEvent(Long projectId, Long freelancerId, Long clientId) {
        try {
            Map<String, Object> eventData = new HashMap<>();
            eventData.put("eventType", "PROJECT_ASSIGNED");
            eventData.put("projectId", projectId);
            eventData.put("freelancerId", freelancerId);
            eventData.put("clientId", clientId);
            eventData.put("timestamp", LocalDateTime.now().toString());
            
            String routingKey = "project.assigned";
            
            rabbitTemplate.convertAndSend(EXCHANGE, routingKey, eventData);
            
            log.info("Published PROJECT_ASSIGNED event for project: {} to freelancer: {} with routing key: {}", 
                     projectId, freelancerId, routingKey);
            
        } catch (Exception e) {
            log.error("Error publishing project assigned event for project {}: {}", projectId, e.getMessage());
        }
    }
}