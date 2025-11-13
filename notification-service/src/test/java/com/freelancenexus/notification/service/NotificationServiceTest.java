package com.freelancenexus.notification.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freelancenexus.notification.dto.PaymentEventDTO;
import com.freelancenexus.notification.dto.ProjectEventDTO;
import com.freelancenexus.notification.dto.ProposalEventDTO;
import com.freelancenexus.notification.model.Notification;
import com.freelancenexus.notification.model.NotificationType;
import com.freelancenexus.notification.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Unit tests for NotificationService
 * Tests all business logic for notification processing
 */
@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private NotificationService notificationService;

    private ProjectEventDTO projectEventDTO;
    private ProposalEventDTO proposalEventDTO;
    private PaymentEventDTO paymentEventDTO;
    private Notification notification;

    @BeforeEach
    void setUp() {
        // Setup ProjectEventDTO
        projectEventDTO = new ProjectEventDTO();
        projectEventDTO.setProjectId(1L);
        projectEventDTO.setClientId(100L);
        projectEventDTO.setClientEmail("client@example.com");
        projectEventDTO.setProjectTitle("Web Development Project");
        projectEventDTO.setDescription("Build a web application");
        projectEventDTO.setBudget(5000.0);
        projectEventDTO.setStatus("OPEN");
        projectEventDTO.setAssignedFreelancerId(200L);
        projectEventDTO.setFreelancerName("John Doe");
        projectEventDTO.setFreelancerEmail("freelancer@example.com");
        projectEventDTO.setCreatedAt(LocalDateTime.now());

        // Setup ProposalEventDTO
        proposalEventDTO = new ProposalEventDTO();
        proposalEventDTO.setProposalId(1L);
        proposalEventDTO.setProjectId(1L);
        proposalEventDTO.setProjectTitle("Web Development Project");
        proposalEventDTO.setFreelancerId(200L);
        proposalEventDTO.setFreelancerName("John Doe");
        proposalEventDTO.setFreelancerEmail("freelancer@example.com");
        proposalEventDTO.setClientId(100L);
        proposalEventDTO.setClientEmail("client@example.com");
        proposalEventDTO.setBidAmount(4500.0);
        proposalEventDTO.setStatus("SUBMITTED");
        proposalEventDTO.setSubmittedAt(LocalDateTime.now());

        // Setup PaymentEventDTO
        paymentEventDTO = new PaymentEventDTO();
        paymentEventDTO.setPaymentId(1L);
        paymentEventDTO.setProjectId(1L);
        paymentEventDTO.setProjectTitle("Web Development Project");
        paymentEventDTO.setPayerId(100L);
        paymentEventDTO.setPayerEmail("client@example.com");
        paymentEventDTO.setReceiverId(200L);
        paymentEventDTO.setReceiverEmail("freelancer@example.com");
        paymentEventDTO.setAmount(4500.0);
        paymentEventDTO.setCurrency("USD");
        paymentEventDTO.setStatus("COMPLETED");
        paymentEventDTO.setTransactionId("TXN123456");
        paymentEventDTO.setCompletedAt(LocalDateTime.now());

        // Setup Notification
        notification = new Notification();
        notification.setId(1L);
        notification.setUserId(100L);
        notification.setType(NotificationType.PROJECT_CREATED);
        notification.setTitle("Test Notification");
        notification.setMessage("Test message");
        notification.setIsRead(false);
        notification.setEmailSent(false);
        notification.setRecipientEmail("test@example.com");
        notification.setCreatedAt(LocalDateTime.now());
    }

    /**
     * Test: Handle project created event - Success scenario
     */
    @Test
    void shouldCreateNotificationAndSendEmail_whenProjectCreatedEventIsHandled() throws Exception {
        // Arrange
        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);
        when(objectMapper.writeValueAsString(any())).thenReturn("{}");
        doNothing().when(emailService).sendProjectCreatedEmail(anyString(), anyString());

        // Act
        notificationService.handleProjectCreated(projectEventDTO);

        // Assert
        ArgumentCaptor<Notification> notificationCaptor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationRepository, times(2)).save(notificationCaptor.capture());

        List<Notification> savedNotifications = notificationCaptor.getAllValues();
        Notification savedNotification = savedNotifications.get(0);

        assertEquals(projectEventDTO.getClientId(), savedNotification.getUserId());
        assertEquals(NotificationType.PROJECT_CREATED, savedNotification.getType());
        assertEquals("Project Posted Successfully", savedNotification.getTitle());
        assertTrue(savedNotification.getMessage().contains(projectEventDTO.getProjectTitle()));
        assertTrue(savedNotification.getMessage().contains("5000.00"));
        assertEquals(projectEventDTO.getClientEmail(), savedNotification.getRecipientEmail());
        assertEquals("PROJECT", savedNotification.getRelatedEntityType());
        assertEquals(projectEventDTO.getProjectId(), savedNotification.getRelatedEntityId());

        verify(emailService, times(1)).sendProjectCreatedEmail(
                projectEventDTO.getClientEmail(),
                projectEventDTO.getProjectTitle()
        );
    }

    /**
     * Test: Handle project created - Exception thrown during processing
     */
    @Test
    void shouldThrowRuntimeException_whenProjectCreatedProcessingFails() {
        // Arrange
        when(notificationRepository.save(any(Notification.class)))
                .thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                notificationService.handleProjectCreated(projectEventDTO)
        );

        assertEquals("Notification processing failed", exception.getMessage());
        verify(notificationRepository, times(1)).save(any(Notification.class));
        verify(emailService, never()).sendProjectCreatedEmail(anyString(), anyString());
    }

    /**
     * Test: Handle project assigned event - Success scenario
     */
    @Test
    void shouldCreateNotificationAndSendEmail_whenProjectAssignedEventIsHandled() throws Exception {
        // Arrange
        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);
        when(objectMapper.writeValueAsString(any())).thenReturn("{}");
        doNothing().when(emailService).sendProposalAcceptedEmail(anyString(), anyString());

        // Act
        notificationService.handleProjectAssigned(projectEventDTO);

        // Assert
        ArgumentCaptor<Notification> notificationCaptor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationRepository, times(2)).save(notificationCaptor.capture());

        List<Notification> savedNotifications = notificationCaptor.getAllValues();
        Notification savedNotification = savedNotifications.get(0);

        assertEquals(projectEventDTO.getAssignedFreelancerId(), savedNotification.getUserId());
        assertEquals(NotificationType.PROJECT_ASSIGNED, savedNotification.getType());
        assertEquals("Project Assigned to You", savedNotification.getTitle());
        assertTrue(savedNotification.getMessage().contains(projectEventDTO.getProjectTitle()));
        assertEquals(projectEventDTO.getFreelancerEmail(), savedNotification.getRecipientEmail());

        verify(emailService, times(1)).sendProposalAcceptedEmail(
                projectEventDTO.getFreelancerEmail(),
                projectEventDTO.getProjectTitle()
        );
    }

    /**
     * Test: Handle project assigned - Exception scenario
     */
    @Test
    void shouldThrowRuntimeException_whenProjectAssignedProcessingFails() {
        // Arrange
        when(notificationRepository.save(any(Notification.class)))
                .thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                notificationService.handleProjectAssigned(projectEventDTO)
        );

        assertEquals("Notification processing failed", exception.getMessage());
    }

    /**
     * Test: Handle proposal submitted event - Creates notifications for both client and freelancer
     */
    @Test
    void shouldCreateTwoNotifications_whenProposalSubmittedEventIsHandled() throws Exception {
        // Arrange
        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);
        when(objectMapper.writeValueAsString(any())).thenReturn("{}");
        doNothing().when(emailService).sendProposalReceivedEmail(anyString(), anyString(), anyString());

        // Act
        notificationService.handleProposalSubmitted(proposalEventDTO);

        // Assert
        ArgumentCaptor<Notification> notificationCaptor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationRepository, times(3)).save(notificationCaptor.capture());

        List<Notification> savedNotifications = notificationCaptor.getAllValues();

        // Verify client notification
        Notification clientNotification = savedNotifications.get(0);
        assertEquals(proposalEventDTO.getClientId(), clientNotification.getUserId());
        assertEquals(NotificationType.PROPOSAL_SUBMITTED, clientNotification.getType());
        assertEquals("New Proposal Received", clientNotification.getTitle());
        assertTrue(clientNotification.getMessage().contains(proposalEventDTO.getFreelancerName()));
        assertTrue(clientNotification.getMessage().contains("4500.00"));

        // Verify freelancer notification
        Notification freelancerNotification = savedNotifications.get(2);
        assertEquals(proposalEventDTO.getFreelancerId(), freelancerNotification.getUserId());
        assertEquals(NotificationType.PROPOSAL_SUBMITTED, freelancerNotification.getType());
        assertEquals("Proposal Submitted Successfully", freelancerNotification.getTitle());

        verify(emailService, times(1)).sendProposalReceivedEmail(
                proposalEventDTO.getClientEmail(),
                proposalEventDTO.getFreelancerName(),
                proposalEventDTO.getProjectTitle()
        );
    }

    /**
     * Test: Handle proposal submitted - Exception scenario
     */
    @Test
    void shouldThrowRuntimeException_whenProposalSubmittedProcessingFails() {
        // Arrange
        when(notificationRepository.save(any(Notification.class)))
                .thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                notificationService.handleProposalSubmitted(proposalEventDTO)
        );

        assertEquals("Notification processing failed", exception.getMessage());
    }

    /**
     * Test: Handle payment completed event - Creates notifications for both payer and receiver
     */
    @Test
    void shouldCreateTwoNotifications_whenPaymentCompletedEventIsHandled() throws Exception {
        // Arrange
        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);
        when(objectMapper.writeValueAsString(any())).thenReturn("{}");
        doNothing().when(emailService).sendPaymentCompletedEmail(anyString(), anyDouble(), anyString(), anyString());
        doNothing().when(emailService).sendPaymentReceivedEmail(anyString(), anyDouble(), anyString(), anyString(), anyString());

        // Act
        notificationService.handlePaymentCompleted(paymentEventDTO);

        // Assert
        ArgumentCaptor<Notification> notificationCaptor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationRepository, times(4)).save(notificationCaptor.capture());

        List<Notification> savedNotifications = notificationCaptor.getAllValues();

        // Verify payer notification
        Notification payerNotification = savedNotifications.get(0);
        assertEquals(paymentEventDTO.getPayerId(), payerNotification.getUserId());
        assertEquals(NotificationType.PAYMENT_COMPLETED, payerNotification.getType());
        assertEquals("Payment Completed", payerNotification.getTitle());
        assertTrue(payerNotification.getMessage().contains("4500.00"));

        // Verify receiver notification
        Notification receiverNotification = savedNotifications.get(2);
        assertEquals(paymentEventDTO.getReceiverId(), receiverNotification.getUserId());
        assertEquals(NotificationType.PAYMENT_COMPLETED, receiverNotification.getType());
        assertEquals("Payment Received", receiverNotification.getTitle());

        verify(emailService, times(1)).sendPaymentCompletedEmail(
                paymentEventDTO.getPayerEmail(),
                paymentEventDTO.getAmount(),
                paymentEventDTO.getTransactionId(),
                paymentEventDTO.getCurrency()
        );

        verify(emailService, times(1)).sendPaymentReceivedEmail(
                paymentEventDTO.getReceiverEmail(),
                paymentEventDTO.getAmount(),
                paymentEventDTO.getTransactionId(),
                paymentEventDTO.getProjectTitle(),
                paymentEventDTO.getCurrency()
        );
    }

    /**
     * Test: Handle payment completed - Exception scenario
     */
    @Test
    void shouldThrowRuntimeException_whenPaymentCompletedProcessingFails() {
        // Arrange
        when(notificationRepository.save(any(Notification.class)))
                .thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                notificationService.handlePaymentCompleted(paymentEventDTO)
        );

        assertEquals("Notification processing failed", exception.getMessage());
    }

    /**
     * Test: Get user notifications - Success scenario
     */
    @Test
    void shouldReturnUserNotifications_whenGetUserNotificationsIsCalled() {
        // Arrange
        Long userId = 100L;
        List<Notification> notifications = Arrays.asList(notification, notification);
        when(notificationRepository.findByUserIdOrderByCreatedAtDesc(userId)).thenReturn(notifications);

        // Act
        List<Notification> result = notificationService.getUserNotifications(userId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(notificationRepository, times(1)).findByUserIdOrderByCreatedAtDesc(userId);
    }

    /**
     * Test: Get user notifications - Empty list
     */
    @Test
    void shouldReturnEmptyList_whenUserHasNoNotifications() {
        // Arrange
        Long userId = 100L;
        when(notificationRepository.findByUserIdOrderByCreatedAtDesc(userId)).thenReturn(Collections.emptyList());

        // Act
        List<Notification> result = notificationService.getUserNotifications(userId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(notificationRepository, times(1)).findByUserIdOrderByCreatedAtDesc(userId);
    }

    /**
     * Test: Get unread notifications - Success scenario
     */
    @Test
    void shouldReturnUnreadNotifications_whenGetUnreadNotificationsIsCalled() {
        // Arrange
        Long userId = 100L;
        List<Notification> unreadNotifications = Arrays.asList(notification);
        when(notificationRepository.findByUserIdAndIsReadFalseOrderByCreatedAtDesc(userId))
                .thenReturn(unreadNotifications);

        // Act
        List<Notification> result = notificationService.getUnreadNotifications(userId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertFalse(result.get(0).getIsRead());
        verify(notificationRepository, times(1)).findByUserIdAndIsReadFalseOrderByCreatedAtDesc(userId);
    }

    /**
     * Test: Mark notification as read - Success scenario
     */
    @Test
    void shouldMarkNotificationAsRead_whenMarkAsReadIsCalled() {
        // Arrange
        Long notificationId = 1L;
        when(notificationRepository.findById(notificationId)).thenReturn(Optional.of(notification));
        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);

        // Act
        Notification result = notificationService.markAsRead(notificationId);

        // Assert
        assertNotNull(result);
        assertTrue(result.getIsRead());
        assertNotNull(result.getReadAt());
        verify(notificationRepository, times(1)).findById(notificationId);
        verify(notificationRepository, times(1)).save(notification);
    }

    /**
     * Test: Mark notification as read - Notification not found
     */
    @Test
    void shouldThrowException_whenNotificationNotFound() {
        // Arrange
        Long notificationId = 999L;
        when(notificationRepository.findById(notificationId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                notificationService.markAsRead(notificationId)
        );

        assertEquals("Notification not found", exception.getMessage());
        verify(notificationRepository, times(1)).findById(notificationId);
        verify(notificationRepository, never()).save(any(Notification.class));
    }

    /**
     * Test: Mark all notifications as read - Success scenario
     */
    @Test
    void shouldMarkAllNotificationsAsRead_whenMarkAllAsReadIsCalled() {
        // Arrange
        Long userId = 100L;
        Notification notification1 = new Notification();
        notification1.setId(1L);
        notification1.setUserId(userId);
        notification1.setIsRead(false);

        Notification notification2 = new Notification();
        notification2.setId(2L);
        notification2.setUserId(userId);
        notification2.setIsRead(false);

        List<Notification> unreadNotifications = Arrays.asList(notification1, notification2);

        when(notificationRepository.findByUserIdAndIsReadFalseOrderByCreatedAtDesc(userId))
                .thenReturn(unreadNotifications);
        when(notificationRepository.saveAll(anyList())).thenReturn(unreadNotifications);

        // Act
        notificationService.markAllAsRead(userId);

        // Assert
        ArgumentCaptor<List<Notification>> captor = ArgumentCaptor.forClass(List.class);
        verify(notificationRepository, times(1)).saveAll(captor.capture());

        List<Notification> savedNotifications = captor.getValue();
        assertEquals(2, savedNotifications.size());
        assertTrue(savedNotifications.get(0).getIsRead());
        assertTrue(savedNotifications.get(1).getIsRead());
        assertNotNull(savedNotifications.get(0).getReadAt());
        assertNotNull(savedNotifications.get(1).getReadAt());
    }

    /**
     * Test: Mark all as read - No unread notifications
     */
    @Test
    void shouldDoNothing_whenNoUnreadNotificationsExist() {
        // Arrange
        Long userId = 100L;
        when(notificationRepository.findByUserIdAndIsReadFalseOrderByCreatedAtDesc(userId))
                .thenReturn(Collections.emptyList());

        // Act
        notificationService.markAllAsRead(userId);

        // Assert
        verify(notificationRepository, times(1)).findByUserIdAndIsReadFalseOrderByCreatedAtDesc(userId);
        verify(notificationRepository, times(1)).saveAll(Collections.emptyList());
    }

    /**
     * Test: Create metadata - Success scenario
     */
    @Test
    void shouldCreateMetadata_whenValidEventProvided() throws Exception {
        // Arrange
        when(objectMapper.writeValueAsString(any())).thenReturn("{\"timestamp\":\"2024-01-01\"}");
        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);
        doNothing().when(emailService).sendProjectCreatedEmail(anyString(), anyString());

        // Act
        notificationService.handleProjectCreated(projectEventDTO);

        // Assert
        verify(objectMapper, atLeastOnce()).writeValueAsString(any());
    }

    /**
     * Test: Create metadata - Exception handling
     */
    @Test
    void shouldReturnEmptyJson_whenMetadataCreationFails() throws Exception {
        // Arrange
        when(objectMapper.writeValueAsString(any())).thenThrow(new RuntimeException("JSON error"));
        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);
        doNothing().when(emailService).sendProjectCreatedEmail(anyString(), anyString());

        // Act
        notificationService.handleProjectCreated(projectEventDTO);

        // Assert - Should not throw exception, should handle gracefully
        verify(notificationRepository, atLeastOnce()).save(any(Notification.class));
    }

    /**
     * Test: Email sent flag is updated correctly
     */
    @Test
    void shouldSetEmailSentFlag_afterSendingEmail() throws Exception {
        // Arrange
        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);
        when(objectMapper.writeValueAsString(any())).thenReturn("{}");
        doNothing().when(emailService).sendProjectCreatedEmail(anyString(), anyString());

        // Act
        notificationService.handleProjectCreated(projectEventDTO);

        // Assert
        ArgumentCaptor<Notification> captor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationRepository, times(2)).save(captor.capture());

        // Second save should have emailSent = true
        Notification updatedNotification = captor.getAllValues().get(1);
        assertTrue(updatedNotification.getEmailSent());
    }
}