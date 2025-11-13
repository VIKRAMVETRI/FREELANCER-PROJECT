package com.freelancenexus.notification.listener;

import com.freelancenexus.notification.dto.ProposalEventDTO;
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
 * Unit tests for ProposalEventListener
 * Tests RabbitMQ message handling for proposal events
 */
@ExtendWith(MockitoExtension.class)
class ProposalEventListenerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private ProposalEventListener proposalEventListener;

    private ProposalEventDTO proposalEventDTO;

    @BeforeEach
    void setUp() {
        // Setup ProposalEventDTO
        proposalEventDTO = new ProposalEventDTO();
        proposalEventDTO.setProposalId(1L);
        proposalEventDTO.setProjectId(100L);
        proposalEventDTO.setProjectTitle("Web Development Project");
        proposalEventDTO.setFreelancerId(200L);
        proposalEventDTO.setFreelancerName("John Doe");
        proposalEventDTO.setFreelancerEmail("freelancer@example.com");
        proposalEventDTO.setClientId(300L);
        proposalEventDTO.setClientEmail("client@example.com");
        proposalEventDTO.setBidAmount(4500.0);
        proposalEventDTO.setStatus("SUBMITTED");
        proposalEventDTO.setSubmittedAt(LocalDateTime.now());
    }

    /**
     * Test: Handle proposal submitted event - Success scenario
     */
    @Test
    void shouldProcessEvent_whenProposalSubmittedEventIsReceived() {
        // Arrange
        doNothing().when(notificationService).handleProposalSubmitted(any(ProposalEventDTO.class));

        // Act
        proposalEventListener.handleProposalSubmitted(proposalEventDTO);

        // Assert
        verify(notificationService, times(1)).handleProposalSubmitted(proposalEventDTO);
    }

    /**
     * Test: Handle proposal submitted - Service processes event successfully
     */
    @Test
    void shouldCallService_whenProposalSubmittedEventIsValid() {
        // Arrange
        doNothing().when(notificationService).handleProposalSubmitted(any(ProposalEventDTO.class));

        // Act
        assertDoesNotThrow(() -> proposalEventListener.handleProposalSubmitted(proposalEventDTO));

        // Assert
        verify(notificationService, times(1)).handleProposalSubmitted(proposalEventDTO);
    }

    /**
     * Test: Handle proposal submitted - Service throws exception
     */
    @Test
    void shouldRethrowException_whenProposalSubmittedServiceFails() {
        // Arrange
        RuntimeException serviceException = new RuntimeException("Service error");
        doThrow(serviceException).when(notificationService).handleProposalSubmitted(any(ProposalEventDTO.class));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                proposalEventListener.handleProposalSubmitted(proposalEventDTO)
        );

        assertEquals("Service error", exception.getMessage());
        verify(notificationService, times(1)).handleProposalSubmitted(proposalEventDTO);
    }

    /**
     * Test: Handle proposal submitted - Null event
     */
    @Test
    void shouldHandleNullEvent_forProposalSubmitted() {
        // Act & Assert - Null will cause NullPointerException in the listener
        assertThrows(NullPointerException.class, () ->
                proposalEventListener.handleProposalSubmitted(null)
        );

        // Note: Service is not called because NPE occurs before service invocation
        verifyNoInteractions(notificationService);
    }

    /**
     * Test: Handle proposal submitted - Event with null fields
     */
    @Test
    void shouldHandleEvent_withNullFields() {
        // Arrange
        ProposalEventDTO eventWithNulls = new ProposalEventDTO();
        eventWithNulls.setProposalId(1L);
        // Other fields are null
        
        doNothing().when(notificationService).handleProposalSubmitted(any(ProposalEventDTO.class));

        // Act
        assertDoesNotThrow(() -> proposalEventListener.handleProposalSubmitted(eventWithNulls));

        // Assert
        verify(notificationService, times(1)).handleProposalSubmitted(eventWithNulls);
    }

    /**
     * Test: Handle proposal submitted - Large bid amount
     */
    @Test
    void shouldProcessEvent_withLargeBidAmount() {
        // Arrange
        proposalEventDTO.setBidAmount(999999999.99);
        doNothing().when(notificationService).handleProposalSubmitted(any(ProposalEventDTO.class));

        // Act
        proposalEventListener.handleProposalSubmitted(proposalEventDTO);

        // Assert
        verify(notificationService, times(1)).handleProposalSubmitted(proposalEventDTO);
    }

    /**
     * Test: Handle proposal submitted - Zero bid amount
     */
    @Test
    void shouldProcessEvent_withZeroBidAmount() {
        // Arrange
        proposalEventDTO.setBidAmount(0.0);
        doNothing().when(notificationService).handleProposalSubmitted(any(ProposalEventDTO.class));

        // Act
        proposalEventListener.handleProposalSubmitted(proposalEventDTO);

        // Assert
        verify(notificationService, times(1)).handleProposalSubmitted(proposalEventDTO);
    }

    /**
     * Test: Handle proposal submitted - Negative bid amount
     */
    @Test
    void shouldProcessEvent_withNegativeBidAmount() {
        // Arrange
        proposalEventDTO.setBidAmount(-100.0);
        doNothing().when(notificationService).handleProposalSubmitted(any(ProposalEventDTO.class));

        // Act
        proposalEventListener.handleProposalSubmitted(proposalEventDTO);

        // Assert
        verify(notificationService, times(1)).handleProposalSubmitted(proposalEventDTO);
    }

    /**
     * Test: Handle multiple proposal submitted events sequentially
     */
    @Test
    void shouldProcessMultipleEvents_sequentially() {
        // Arrange
        doNothing().when(notificationService).handleProposalSubmitted(any(ProposalEventDTO.class));

        ProposalEventDTO event1 = createProposalEvent(1L, "Proposal 1", 1000.0);
        ProposalEventDTO event2 = createProposalEvent(2L, "Proposal 2", 2000.0);
        ProposalEventDTO event3 = createProposalEvent(3L, "Proposal 3", 3000.0);

        // Act
        proposalEventListener.handleProposalSubmitted(event1);
        proposalEventListener.handleProposalSubmitted(event2);
        proposalEventListener.handleProposalSubmitted(event3);

        // Assert
        verify(notificationService, times(3)).handleProposalSubmitted(any(ProposalEventDTO.class));
    }

    /**
     * Test: Exception is rethrown to trigger retry mechanism
     */
    @Test
    void shouldRethrowException_toTriggerRetryMechanism() {
        // Arrange
        RuntimeException exception = new RuntimeException("Temporary failure");
        doThrow(exception).when(notificationService).handleProposalSubmitted(any(ProposalEventDTO.class));

        // Act & Assert
        assertThrows(RuntimeException.class, () ->
                proposalEventListener.handleProposalSubmitted(proposalEventDTO)
        );

        verify(notificationService, times(1)).handleProposalSubmitted(proposalEventDTO);
    }

    /**
     * Test: Event with special characters in project title
     */
    @Test
    void shouldHandleEvent_withSpecialCharactersInProjectTitle() {
        // Arrange
        proposalEventDTO.setProjectTitle("Project with special chars: @#$%^&*()");
        doNothing().when(notificationService).handleProposalSubmitted(any(ProposalEventDTO.class));

        // Act
        assertDoesNotThrow(() -> proposalEventListener.handleProposalSubmitted(proposalEventDTO));

        // Assert
        verify(notificationService, times(1)).handleProposalSubmitted(proposalEventDTO);
    }

    /**
     * Test: Event with special characters in freelancer name
     */
    @Test
    void shouldHandleEvent_withSpecialCharactersInFreelancerName() {
        // Arrange
        proposalEventDTO.setFreelancerName("John O'Neil-Smith Jr.");
        doNothing().when(notificationService).handleProposalSubmitted(any(ProposalEventDTO.class));

        // Act
        assertDoesNotThrow(() -> proposalEventListener.handleProposalSubmitted(proposalEventDTO));

        // Assert
        verify(notificationService, times(1)).handleProposalSubmitted(proposalEventDTO);
    }

    /**
     * Test: Event with very long project title
     */
    @Test
    void shouldHandleEvent_withLongProjectTitle() {
        // Arrange
        String longTitle = "A".repeat(500);
        proposalEventDTO.setProjectTitle(longTitle);
        doNothing().when(notificationService).handleProposalSubmitted(any(ProposalEventDTO.class));

        // Act
        assertDoesNotThrow(() -> proposalEventListener.handleProposalSubmitted(proposalEventDTO));

        // Assert
        verify(notificationService, times(1)).handleProposalSubmitted(proposalEventDTO);
    }

    /**
     * Test: Event processing time
     */
    @Test
    void shouldProcessEvent_quickly() {
        // Arrange
        doNothing().when(notificationService).handleProposalSubmitted(any(ProposalEventDTO.class));

        // Act
        long startTime = System.currentTimeMillis();
        proposalEventListener.handleProposalSubmitted(proposalEventDTO);
        long endTime = System.currentTimeMillis();

        // Assert
        long processingTime = endTime - startTime;
        assertTrue(processingTime < 1000, "Event processing should be fast");
        verify(notificationService, times(1)).handleProposalSubmitted(proposalEventDTO);
    }

    /**
     * Test: Database exception handling
     */
    @Test
    void shouldRethrowException_whenDatabaseErrorOccurs() {
        // Arrange
        RuntimeException dbException = new RuntimeException("Database connection failed");
        doThrow(dbException).when(notificationService).handleProposalSubmitted(any(ProposalEventDTO.class));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                proposalEventListener.handleProposalSubmitted(proposalEventDTO)
        );

        assertEquals("Database connection failed", exception.getMessage());
        verify(notificationService, times(1)).handleProposalSubmitted(proposalEventDTO);
    }

    /**
     * Test: Email service exception handling
     */
    @Test
    void shouldRethrowException_whenEmailServiceFails() {
        // Arrange
        RuntimeException emailException = new RuntimeException("Email service unavailable");
        doThrow(emailException).when(notificationService).handleProposalSubmitted(any(ProposalEventDTO.class));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                proposalEventListener.handleProposalSubmitted(proposalEventDTO)
        );

        assertEquals("Email service unavailable", exception.getMessage());
        verify(notificationService, times(1)).handleProposalSubmitted(proposalEventDTO);
    }

    /**
     * Test: Event with different status values
     */
    @Test
    void shouldHandleEvent_withDifferentStatusValues() {
        // Arrange
        proposalEventDTO.setStatus("ACCEPTED");
        doNothing().when(notificationService).handleProposalSubmitted(any(ProposalEventDTO.class));

        // Act
        assertDoesNotThrow(() -> proposalEventListener.handleProposalSubmitted(proposalEventDTO));

        // Assert
        verify(notificationService, times(1)).handleProposalSubmitted(proposalEventDTO);
    }

    /**
     * Test: Event with null status
     */
    @Test
    void shouldHandleEvent_withNullStatus() {
        // Arrange
        proposalEventDTO.setStatus(null);
        doNothing().when(notificationService).handleProposalSubmitted(any(ProposalEventDTO.class));

        // Act
        assertDoesNotThrow(() -> proposalEventListener.handleProposalSubmitted(proposalEventDTO));

        // Assert
        verify(notificationService, times(1)).handleProposalSubmitted(proposalEventDTO);
    }

    /**
     * Test: Event with decimal bid amount precision
     */
    @Test
    void shouldHandleEvent_withPreciseBidAmount() {
        // Arrange
        proposalEventDTO.setBidAmount(4567.8912345);
        doNothing().when(notificationService).handleProposalSubmitted(any(ProposalEventDTO.class));

        // Act
        assertDoesNotThrow(() -> proposalEventListener.handleProposalSubmitted(proposalEventDTO));

        // Assert
        verify(notificationService, times(1)).handleProposalSubmitted(proposalEventDTO);
    }

    /**
     * Test: Concurrent event processing simulation
     */
    @Test
    void shouldHandleConcurrentEvents_safely() {
        // Arrange
        doNothing().when(notificationService).handleProposalSubmitted(any(ProposalEventDTO.class));

        ProposalEventDTO event1 = createProposalEvent(1L, "Concurrent Proposal 1", 1000.0);
        ProposalEventDTO event2 = createProposalEvent(2L, "Concurrent Proposal 2", 2000.0);

        // Act
        assertDoesNotThrow(() -> {
            proposalEventListener.handleProposalSubmitted(event1);
            proposalEventListener.handleProposalSubmitted(event2);
        });

        // Assert
        verify(notificationService, times(2)).handleProposalSubmitted(any(ProposalEventDTO.class));
    }

    /**
     * Test: Event with same freelancer and client IDs
     */
    @Test
    void shouldHandleEvent_whenFreelancerAndClientAreSame() {
        // Arrange
        proposalEventDTO.setFreelancerId(300L);
        proposalEventDTO.setClientId(300L);
        doNothing().when(notificationService).handleProposalSubmitted(any(ProposalEventDTO.class));

        // Act
        assertDoesNotThrow(() -> proposalEventListener.handleProposalSubmitted(proposalEventDTO));

        // Assert
        verify(notificationService, times(1)).handleProposalSubmitted(proposalEventDTO);
    }

    /**
     * Test: Event with past submission date
     */
    @Test
    void shouldHandleEvent_withPastSubmissionDate() {
        // Arrange
        proposalEventDTO.setSubmittedAt(LocalDateTime.now().minusDays(30));
        doNothing().when(notificationService).handleProposalSubmitted(any(ProposalEventDTO.class));

        // Act
        assertDoesNotThrow(() -> proposalEventListener.handleProposalSubmitted(proposalEventDTO));

        // Assert
        verify(notificationService, times(1)).handleProposalSubmitted(proposalEventDTO);
    }

    /**
     * Test: Event with future submission date
     */
    @Test
    void shouldHandleEvent_withFutureSubmissionDate() {
        // Arrange
        proposalEventDTO.setSubmittedAt(LocalDateTime.now().plusDays(1));
        doNothing().when(notificationService).handleProposalSubmitted(any(ProposalEventDTO.class));

        // Act
        assertDoesNotThrow(() -> proposalEventListener.handleProposalSubmitted(proposalEventDTO));

        // Assert
        verify(notificationService, times(1)).handleProposalSubmitted(proposalEventDTO);
    }

    // Helper method
    private ProposalEventDTO createProposalEvent(Long proposalId, String freelancerName, Double bidAmount) {
        ProposalEventDTO event = new ProposalEventDTO();
        event.setProposalId(proposalId);
        event.setProjectId(100L);
        event.setProjectTitle("Test Project");
        event.setFreelancerId(200L);
        event.setFreelancerName(freelancerName);
        event.setFreelancerEmail("freelancer@example.com");
        event.setClientId(300L);
        event.setClientEmail("client@example.com");
        event.setBidAmount(bidAmount);
        event.setStatus("SUBMITTED");
        event.setSubmittedAt(LocalDateTime.now());
        return event;
    }
}