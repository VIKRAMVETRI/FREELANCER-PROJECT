package com.freelancenexus.notification.listener;

import com.freelancenexus.notification.dto.ProjectEventDTO;
import com.freelancenexus.notification.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ProjectEventListener
 * Tests RabbitMQ message handling for project events
 */
@ExtendWith(MockitoExtension.class)
class ProjectEventListenerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private ProjectEventListener projectEventListener;

    private ProjectEventDTO projectCreatedEvent;
    private ProjectEventDTO projectAssignedEvent;

    @BeforeEach
    void setUp() {
        // Setup ProjectCreatedEvent
        projectCreatedEvent = new ProjectEventDTO();
        projectCreatedEvent.setProjectId(1L);
        projectCreatedEvent.setClientId(100L);
        projectCreatedEvent.setClientEmail("client@example.com");
        projectCreatedEvent.setProjectTitle("Web Development Project");
        projectCreatedEvent.setDescription("Build a modern web application");
        projectCreatedEvent.setBudget(5000.0);
        projectCreatedEvent.setStatus("OPEN");
        projectCreatedEvent.setCreatedAt(LocalDateTime.now());

        // Setup ProjectAssignedEvent
        projectAssignedEvent = new ProjectEventDTO();
        projectAssignedEvent.setProjectId(1L);
        projectAssignedEvent.setClientId(100L);
        projectAssignedEvent.setClientEmail("client@example.com");
        projectAssignedEvent.setProjectTitle("Web Development Project");
        projectAssignedEvent.setDescription("Build a modern web application");
        projectAssignedEvent.setBudget(5000.0);
        projectAssignedEvent.setStatus("ASSIGNED");
        projectAssignedEvent.setAssignedFreelancerId(200L);
        projectAssignedEvent.setFreelancerName("John Doe");
        projectAssignedEvent.setFreelancerEmail("freelancer@example.com");
        projectAssignedEvent.setCreatedAt(LocalDateTime.now());
    }

    /**
     * Test: Handle project created event - Success scenario
     */
    @Test
    void shouldProcessEvent_whenProjectCreatedEventIsReceived() {
        // Arrange
        doNothing().when(notificationService).handleProjectCreated(any(ProjectEventDTO.class));

        // Act
        projectEventListener.handleProjectCreated(projectCreatedEvent);

        // Assert
        verify(notificationService, times(1)).handleProjectCreated(projectCreatedEvent);
    }

    /**
     * Test: Handle project created - Service processes event successfully
     */
    @Test
    void shouldCallService_whenProjectCreatedEventIsValid() {
        // Arrange
        doNothing().when(notificationService).handleProjectCreated(any(ProjectEventDTO.class));

        // Act
        assertDoesNotThrow(() -> projectEventListener.handleProjectCreated(projectCreatedEvent));

        // Assert
        verify(notificationService, times(1)).handleProjectCreated(projectCreatedEvent);
    }

    /**
     * Test: Handle project created - Service throws exception
     */
    @Test
    void shouldRethrowException_whenProjectCreatedServiceFails() {
        // Arrange
        RuntimeException serviceException = new RuntimeException("Service error");
        doThrow(serviceException).when(notificationService).handleProjectCreated(any(ProjectEventDTO.class));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                projectEventListener.handleProjectCreated(projectCreatedEvent)
        );

        assertEquals("Service error", exception.getMessage());
        verify(notificationService, times(1)).handleProjectCreated(projectCreatedEvent);
    }

    /**
     * Test: Handle project assigned event - Success scenario
     */
    @Test
    void shouldProcessEvent_whenProjectAssignedEventIsReceived() {
        // Arrange
        doNothing().when(notificationService).handleProjectAssigned(any(ProjectEventDTO.class));

        // Act
        projectEventListener.handleProjectAssigned(projectAssignedEvent);

        // Assert
        verify(notificationService, times(1)).handleProjectAssigned(projectAssignedEvent);
    }

    /**
     * Test: Handle project assigned - Service processes event successfully
     */
    @Test
    void shouldCallService_whenProjectAssignedEventIsValid() {
        // Arrange
        doNothing().when(notificationService).handleProjectAssigned(any(ProjectEventDTO.class));

        // Act
        assertDoesNotThrow(() -> projectEventListener.handleProjectAssigned(projectAssignedEvent));

        // Assert
        verify(notificationService, times(1)).handleProjectAssigned(projectAssignedEvent);
    }

    /**
     * Test: Handle project assigned - Service throws exception
     */
    @Test
    void shouldRethrowException_whenProjectAssignedServiceFails() {
        // Arrange
        RuntimeException serviceException = new RuntimeException("Assignment error");
        doThrow(serviceException).when(notificationService).handleProjectAssigned(any(ProjectEventDTO.class));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                projectEventListener.handleProjectAssigned(projectAssignedEvent)
        );

        assertEquals("Assignment error", exception.getMessage());
        verify(notificationService, times(1)).handleProjectAssigned(projectAssignedEvent);
    }

    /**
     * Test: Handle project created - Null event
     */
    @Test
    void shouldHandleNullEvent_forProjectCreated() {
        // Act & Assert - Null will cause NullPointerException in the listener
        assertThrows(NullPointerException.class, () ->
                projectEventListener.handleProjectCreated(null)
        );

        // Note: Service is not called because NPE occurs before service invocation
        verifyNoInteractions(notificationService);
    }

    /**
     * Test: Handle project assigned - Null event
     */
    @Test
    void shouldHandleNullEvent_forProjectAssigned() {
        // Act & Assert - Null will cause NullPointerException in the listener
        assertThrows(NullPointerException.class, () ->
                projectEventListener.handleProjectAssigned(null)
        );

        // Note: Service is not called because NPE occurs before service invocation
        verifyNoInteractions(notificationService);
    }

    /**
     * Test: Handle project created - Event with minimum data
     */
    @Test
    void shouldHandleEvent_withMinimumData() {
        // Arrange
        ProjectEventDTO minimalEvent = new ProjectEventDTO();
        minimalEvent.setProjectId(1L);
        minimalEvent.setClientId(100L);
        minimalEvent.setClientEmail("client@example.com");
        minimalEvent.setProjectTitle("Minimal Project");
        
        doNothing().when(notificationService).handleProjectCreated(any(ProjectEventDTO.class));

        // Act
        assertDoesNotThrow(() -> projectEventListener.handleProjectCreated(minimalEvent));

        // Assert
        verify(notificationService, times(1)).handleProjectCreated(minimalEvent);
    }

    /**
     * Test: Handle project created - Large budget value
     */
    @Test
    void shouldHandleEvent_withLargeBudget() {
        // Arrange
        projectCreatedEvent.setBudget(999999999.99);
        doNothing().when(notificationService).handleProjectCreated(any(ProjectEventDTO.class));

        // Act
        projectEventListener.handleProjectCreated(projectCreatedEvent);

        // Assert
        verify(notificationService, times(1)).handleProjectCreated(projectCreatedEvent);
    }

    /**
     * Test: Handle project created - Zero budget
     */
    @Test
    void shouldHandleEvent_withZeroBudget() {
        // Arrange
        projectCreatedEvent.setBudget(0.0);
        doNothing().when(notificationService).handleProjectCreated(any(ProjectEventDTO.class));

        // Act
        projectEventListener.handleProjectCreated(projectCreatedEvent);

        // Assert
        verify(notificationService, times(1)).handleProjectCreated(projectCreatedEvent);
    }

    /**
     * Test: Handle multiple project created events sequentially
     */
    @Test
    void shouldProcessMultipleEvents_sequentially() {
        // Arrange
        doNothing().when(notificationService).handleProjectCreated(any(ProjectEventDTO.class));

        ProjectEventDTO event1 = createProjectEvent(1L, "Project 1");
        ProjectEventDTO event2 = createProjectEvent(2L, "Project 2");
        ProjectEventDTO event3 = createProjectEvent(3L, "Project 3");

        // Act
        projectEventListener.handleProjectCreated(event1);
        projectEventListener.handleProjectCreated(event2);
        projectEventListener.handleProjectCreated(event3);

        // Assert
        verify(notificationService, times(3)).handleProjectCreated(any(ProjectEventDTO.class));
    }

    /**
     * Test: Handle project assigned - Multiple assignments
     */
    @Test
    void shouldProcessMultipleAssignments_sequentially() {
        // Arrange
        doNothing().when(notificationService).handleProjectAssigned(any(ProjectEventDTO.class));

        ProjectEventDTO assignment1 = createAssignedEvent(1L, 200L, "John Doe");
        ProjectEventDTO assignment2 = createAssignedEvent(2L, 201L, "Jane Smith");

        // Act
        projectEventListener.handleProjectAssigned(assignment1);
        projectEventListener.handleProjectAssigned(assignment2);

        // Assert
        verify(notificationService, times(2)).handleProjectAssigned(any(ProjectEventDTO.class));
    }

    /**
     * Test: Exception is rethrown to trigger retry mechanism - Project Created
     */
    @Test
    void shouldRethrowException_toTriggerRetry_forProjectCreated() {
        // Arrange
        RuntimeException exception = new RuntimeException("Temporary failure");
        doThrow(exception).when(notificationService).handleProjectCreated(any(ProjectEventDTO.class));

        // Act & Assert
        assertThrows(RuntimeException.class, () ->
                projectEventListener.handleProjectCreated(projectCreatedEvent)
        );

        verify(notificationService, times(1)).handleProjectCreated(projectCreatedEvent);
    }

    /**
     * Test: Exception is rethrown to trigger retry mechanism - Project Assigned
     */
    @Test
    void shouldRethrowException_toTriggerRetry_forProjectAssigned() {
        // Arrange
        RuntimeException exception = new RuntimeException("Temporary failure");
        doThrow(exception).when(notificationService).handleProjectAssigned(any(ProjectEventDTO.class));

        // Act & Assert
        assertThrows(RuntimeException.class, () ->
                projectEventListener.handleProjectAssigned(projectAssignedEvent)
        );

        verify(notificationService, times(1)).handleProjectAssigned(projectAssignedEvent);
    }

    /**
     * Test: Handle project with special characters in title
     */
    @Test
    void shouldHandleEvent_withSpecialCharactersInTitle() {
        // Arrange
        projectCreatedEvent.setProjectTitle("Project with special chars: @#$%^&*()");
        doNothing().when(notificationService).handleProjectCreated(any(ProjectEventDTO.class));

        // Act
        assertDoesNotThrow(() -> projectEventListener.handleProjectCreated(projectCreatedEvent));

        // Assert
        verify(notificationService, times(1)).handleProjectCreated(projectCreatedEvent);
    }

    /**
     * Test: Handle project with very long description
     */
    @Test
    void shouldHandleEvent_withLongDescription() {
        // Arrange
        String longDescription = "A".repeat(5000);
        projectCreatedEvent.setDescription(longDescription);
        doNothing().when(notificationService).handleProjectCreated(any(ProjectEventDTO.class));

        // Act
        assertDoesNotThrow(() -> projectEventListener.handleProjectCreated(projectCreatedEvent));

        // Assert
        verify(notificationService, times(1)).handleProjectCreated(projectCreatedEvent);
    }

    /**
     * Test: Handle project assigned with null freelancer email
     */
    @Test
    void shouldHandleEvent_withNullFreelancerEmail() {
        // Arrange
        projectAssignedEvent.setFreelancerEmail(null);
        doNothing().when(notificationService).handleProjectAssigned(any(ProjectEventDTO.class));

        // Act
        assertDoesNotThrow(() -> projectEventListener.handleProjectAssigned(projectAssignedEvent));

        // Assert
        verify(notificationService, times(1)).handleProjectAssigned(projectAssignedEvent);
    }

    /**
     * Test: Event processing time
     */
    @Test
    void shouldProcessEvent_quickly() {
        // Arrange
        doNothing().when(notificationService).handleProjectCreated(any(ProjectEventDTO.class));

        // Act
        long startTime = System.currentTimeMillis();
        projectEventListener.handleProjectCreated(projectCreatedEvent);
        long endTime = System.currentTimeMillis();

        // Assert
        long processingTime = endTime - startTime;
        assertTrue(processingTime < 1000, "Event processing should be fast");
        verify(notificationService, times(1)).handleProjectCreated(projectCreatedEvent);
    }

    /**
     * Test: Database exception handling for project created
     */
    @Test
    void shouldRethrowException_whenDatabaseErrorOccurs_forProjectCreated() {
        // Arrange
        RuntimeException dbException = new RuntimeException("Database connection failed");
        doThrow(dbException).when(notificationService).handleProjectCreated(any(ProjectEventDTO.class));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                projectEventListener.handleProjectCreated(projectCreatedEvent)
        );

        assertEquals("Database connection failed", exception.getMessage());
        verify(notificationService, times(1)).handleProjectCreated(projectCreatedEvent);
    }

    /**
     * Test: Database exception handling for project assigned
     */
    @Test
    void shouldRethrowException_whenDatabaseErrorOccurs_forProjectAssigned() {
        // Arrange
        RuntimeException dbException = new RuntimeException("Database connection failed");
        doThrow(dbException).when(notificationService).handleProjectAssigned(any(ProjectEventDTO.class));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                projectEventListener.handleProjectAssigned(projectAssignedEvent)
        );

        assertEquals("Database connection failed", exception.getMessage());
        verify(notificationService, times(1)).handleProjectAssigned(projectAssignedEvent);
    }

    // Helper methods
    private ProjectEventDTO createProjectEvent(Long projectId, String title) {
        ProjectEventDTO event = new ProjectEventDTO();
        event.setProjectId(projectId);
        event.setClientId(100L);
        event.setClientEmail("client@example.com");
        event.setProjectTitle(title);
        event.setDescription("Test description");
        event.setBudget(5000.0);
        event.setStatus("OPEN");
        event.setCreatedAt(LocalDateTime.now());
        return event;
    }

    private ProjectEventDTO createAssignedEvent(Long projectId, Long freelancerId, String freelancerName) {
        ProjectEventDTO event = new ProjectEventDTO();
        event.setProjectId(projectId);
        event.setClientId(100L);
        event.setClientEmail("client@example.com");
        event.setProjectTitle("Test Project");
        event.setDescription("Test description");
        event.setBudget(5000.0);
        event.setStatus("ASSIGNED");
        event.setAssignedFreelancerId(freelancerId);
        event.setFreelancerName(freelancerName);
        event.setFreelancerEmail(freelancerName.toLowerCase().replace(" ", ".") + "@example.com");
        event.setCreatedAt(LocalDateTime.now());
        return event;
    }
}