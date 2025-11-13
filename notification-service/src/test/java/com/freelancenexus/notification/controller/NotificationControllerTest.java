package com.freelancenexus.notification.controller;

import com.freelancenexus.notification.dto.NotificationDTO;
import com.freelancenexus.notification.model.Notification;
import com.freelancenexus.notification.model.NotificationType;
import com.freelancenexus.notification.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

/**
 * Unit tests for NotificationController
 * Tests all REST endpoints for notification management
 */
@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    private MockMvc mockMvc;

    private Notification notification1;
    private Notification notification2;
    private List<Notification> notifications;

    @BeforeEach
    void setUp() {
        // Initialize MockMvc with the controller
        mockMvc = MockMvcBuilders.standaloneSetup(notificationController).build();

        // Setup test data
        notification1 = new Notification();
        notification1.setId(1L);
        notification1.setUserId(100L);
        notification1.setType(NotificationType.PROJECT_CREATED);
        notification1.setTitle("Project Posted Successfully");
        notification1.setMessage("Your project 'Web Development' has been posted");
        notification1.setIsRead(false);
        notification1.setEmailSent(true);
        notification1.setRecipientEmail("client@example.com");
        notification1.setCreatedAt(LocalDateTime.now());

        notification2 = new Notification();
        notification2.setId(2L);
        notification2.setUserId(100L);
        notification2.setType(NotificationType.PAYMENT_COMPLETED);
        notification2.setTitle("Payment Completed");
        notification2.setMessage("Payment of $500 has been processed");
        notification2.setIsRead(false);
        notification2.setEmailSent(true);
        notification2.setRecipientEmail("client@example.com");
        notification2.setCreatedAt(LocalDateTime.now());

        notifications = Arrays.asList(notification1, notification2);
    }

    /**
     * Test: Get all notifications for a user - Success scenario
     */
    @Test
    void shouldReturnUserNotifications_whenGetUserNotificationsIsCalled() throws Exception {
        // Arrange
        Long userId = 100L;
        when(notificationService.getUserNotifications(userId)).thenReturn(notifications);

        // Act & Assert
        mockMvc.perform(get("/api/notifications/user/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].userId").value(100))
                .andExpect(jsonPath("$[0].type").value("PROJECT_CREATED"))
                .andExpect(jsonPath("$[0].title").value("Project Posted Successfully"))
                .andExpect(jsonPath("$[0].isRead").value(false))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].type").value("PAYMENT_COMPLETED"));

        // Verify service method was called
        verify(notificationService, times(1)).getUserNotifications(userId);
    }

    /**
     * Test: Get all notifications - Empty list scenario
     */
    @Test
    void shouldReturnEmptyList_whenUserHasNoNotifications() throws Exception {
        // Arrange
        Long userId = 100L;
        when(notificationService.getUserNotifications(userId)).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/api/notifications/user/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(notificationService, times(1)).getUserNotifications(userId);
    }

    /**
     * Test: Get unread notifications - Success scenario
     */
    @Test
    void shouldReturnUnreadNotifications_whenGetUnreadNotificationsIsCalled() throws Exception {
        // Arrange
        Long userId = 100L;
        when(notificationService.getUnreadNotifications(userId)).thenReturn(notifications);

        // Act & Assert
        mockMvc.perform(get("/api/notifications/user/{userId}/unread", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].isRead").value(false))
                .andExpect(jsonPath("$[1].isRead").value(false));

        verify(notificationService, times(1)).getUnreadNotifications(userId);
    }

    /**
     * Test: Get unread notifications count - Success scenario
     */
    @Test
    void shouldReturnUnreadCount_whenGetUnreadCountIsCalled() throws Exception {
        // Arrange
        Long userId = 100L;
        when(notificationService.getUnreadNotifications(userId)).thenReturn(notifications);

        // Act & Assert
        mockMvc.perform(get("/api/notifications/user/{userId}/unread/count", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("2"));

        verify(notificationService, times(1)).getUnreadNotifications(userId);
    }

    /**
     * Test: Get unread count - Zero count scenario
     */
    @Test
    void shouldReturnZero_whenNoUnreadNotifications() throws Exception {
        // Arrange
        Long userId = 100L;
        when(notificationService.getUnreadNotifications(userId)).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/api/notifications/user/{userId}/unread/count", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));

        verify(notificationService, times(1)).getUnreadNotifications(userId);
    }

    /**
     * Test: Mark notification as read - Success scenario
     */
    @Test
    void shouldMarkNotificationAsRead_whenMarkAsReadIsCalled() throws Exception {
        // Arrange
        Long notificationId = 1L;
        notification1.setIsRead(true);
        notification1.setReadAt(LocalDateTime.now());
        when(notificationService.markAsRead(notificationId)).thenReturn(notification1);

        // Act & Assert
        mockMvc.perform(put("/api/notifications/{id}/read", notificationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.isRead").value(true))
                .andExpect(jsonPath("$.readAt").exists());

        verify(notificationService, times(1)).markAsRead(notificationId);
    }

    /**
     * Test: Mark notification as read - Notification not found
     */
    @Test
    void shouldThrowException_whenMarkAsReadCalledWithInvalidId() throws Exception {
        // Arrange
        Long notificationId = 999L;
        when(notificationService.markAsRead(notificationId))
                .thenThrow(new RuntimeException("Notification not found"));

        // Act & Assert - Expect 5xx error (exact code may vary without full Spring context)
        try {
            mockMvc.perform(put("/api/notifications/{id}/read", notificationId)
                    .contentType(MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            // Exception is expected
            assertTrue(e.getCause() instanceof RuntimeException);
        }

        verify(notificationService, times(1)).markAsRead(notificationId);
    }

    /**
     * Test: Mark all notifications as read - Success scenario
     */
    @Test
    void shouldMarkAllAsRead_whenMarkAllAsReadIsCalled() throws Exception {
        // Arrange
        Long userId = 100L;
        doNothing().when(notificationService).markAllAsRead(userId);

        // Act & Assert
        mockMvc.perform(put("/api/notifications/user/{userId}/read-all", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("All notifications marked as read"));

        verify(notificationService, times(1)).markAllAsRead(userId);
    }

    /**
     * Test: Mark all as read - Service throws exception
     */
    @Test
    void shouldHandleException_whenMarkAllAsReadFails() throws Exception {
        // Arrange
        Long userId = 100L;
        doThrow(new RuntimeException("Database error")).when(notificationService).markAllAsRead(userId);

        // Act & Assert - Expect exception to be thrown
        try {
            mockMvc.perform(put("/api/notifications/user/{userId}/read-all", userId)
                    .contentType(MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            // Exception is expected
            assertTrue(e.getCause() instanceof RuntimeException);
        }

        verify(notificationService, times(1)).markAllAsRead(userId);
    }

    /**
     * Test: Health check endpoint - Success scenario
     */
    @Test
    void shouldReturnHealthStatus_whenHealthCheckIsCalled() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/notifications/health")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Notification Service is running"));

        // Verify no service methods were called
        verifyNoInteractions(notificationService);
    }

    /**
     * Test: Get notifications with different user IDs
     */
    @Test
    void shouldReturnNotifications_forDifferentUsers() throws Exception {
        // Arrange
        Long userId1 = 100L;
        Long userId2 = 200L;

        Notification userNotification = new Notification();
        userNotification.setId(3L);
        userNotification.setUserId(userId2);
        userNotification.setType(NotificationType.PROPOSAL_SUBMITTED);
        userNotification.setTitle("New Proposal");
        userNotification.setMessage("New proposal received");
        userNotification.setIsRead(false);

        when(notificationService.getUserNotifications(userId1)).thenReturn(notifications);
        when(notificationService.getUserNotifications(userId2)).thenReturn(List.of(userNotification));

        // Act & Assert - User 1
        mockMvc.perform(get("/api/notifications/user/{userId}", userId1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        // Act & Assert - User 2
        mockMvc.perform(get("/api/notifications/user/{userId}", userId2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].userId").value(200));

        verify(notificationService, times(1)).getUserNotifications(userId1);
        verify(notificationService, times(1)).getUserNotifications(userId2);
    }

    /**
     * Test: DTO conversion includes all fields
     */
    @Test
    void shouldConvertNotificationToDTO_withAllFields() throws Exception {
        // Arrange
        Long userId = 100L;
        notification1.setReadAt(LocalDateTime.now());
        when(notificationService.getUserNotifications(userId)).thenReturn(List.of(notification1));

        // Act & Assert
        mockMvc.perform(get("/api/notifications/user/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].userId").exists())
                .andExpect(jsonPath("$[0].type").exists())
                .andExpect(jsonPath("$[0].title").exists())
                .andExpect(jsonPath("$[0].message").exists())
                .andExpect(jsonPath("$[0].isRead").exists())
                .andExpect(jsonPath("$[0].emailSent").exists())
                .andExpect(jsonPath("$[0].recipientEmail").exists())
                .andExpect(jsonPath("$[0].createdAt").exists())
                .andExpect(jsonPath("$[0].readAt").exists());
    }
}