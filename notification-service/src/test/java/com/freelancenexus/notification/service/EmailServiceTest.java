package com.freelancenexus.notification.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for EmailService
 * Tests all email sending functionality
 */
@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    private static final String CLIENT_EMAIL = "client@example.com";
    private static final String FREELANCER_EMAIL = "freelancer@example.com";
    private static final String PROJECT_TITLE = "Web Development Project";
    private static final String FREELANCER_NAME = "John Doe";
    private static final Double AMOUNT = 4500.0;
    private static final String TRANSACTION_ID = "TXN123456";
    private static final String CURRENCY = "USD";

    @BeforeEach
    void setUp() {
        // Setup is handled by @Mock and @InjectMocks
    }

    /**
     * Test: Send project created email - Success scenario
     */
    @Test
    void shouldSendEmail_whenProjectCreatedEmailIsSent() {
        // Arrange
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        // Act
        emailService.sendProjectCreatedEmail(CLIENT_EMAIL, PROJECT_TITLE);

        // Assert
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(messageCaptor.capture());

        SimpleMailMessage sentMessage = messageCaptor.getValue();
        assertNotNull(sentMessage);
        assertEquals(CLIENT_EMAIL, sentMessage.getTo()[0]);
        assertTrue(sentMessage.getSubject().contains(PROJECT_TITLE));
        assertTrue(sentMessage.getSubject().contains("Posted Successfully"));
        assertTrue(sentMessage.getText().contains(PROJECT_TITLE));
        assertTrue(sentMessage.getText().contains("Dear Client"));
        assertTrue(sentMessage.getText().contains("Sent at:"));
    }

    /**
     * Test: Send project created email - Mail sender throws exception
     */
    @Test
    void shouldHandleException_whenProjectCreatedEmailFails() {
        // Arrange
        doThrow(new RuntimeException("Mail server error")).when(mailSender).send(any(SimpleMailMessage.class));

        // Act - Should not throw exception, should log error
        assertDoesNotThrow(() -> emailService.sendProjectCreatedEmail(CLIENT_EMAIL, PROJECT_TITLE));

        // Assert
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    /**
     * Test: Send proposal received email - Success scenario
     */
    @Test
    void shouldSendEmail_whenProposalReceivedEmailIsSent() {
        // Arrange
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        // Act
        emailService.sendProposalReceivedEmail(CLIENT_EMAIL, FREELANCER_NAME, PROJECT_TITLE);

        // Assert
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(messageCaptor.capture());

        SimpleMailMessage sentMessage = messageCaptor.getValue();
        assertNotNull(sentMessage);
        assertEquals(CLIENT_EMAIL, sentMessage.getTo()[0]);
        assertTrue(sentMessage.getSubject().contains("New Proposal Received"));
        assertTrue(sentMessage.getSubject().contains(PROJECT_TITLE));
        assertTrue(sentMessage.getText().contains(FREELANCER_NAME));
        assertTrue(sentMessage.getText().contains(PROJECT_TITLE));
        assertTrue(sentMessage.getText().contains("Dear Client"));
    }

    /**
     * Test: Send proposal received email - Exception handling
     */
    @Test
    void shouldHandleException_whenProposalReceivedEmailFails() {
        // Arrange
        doThrow(new RuntimeException("Mail server error")).when(mailSender).send(any(SimpleMailMessage.class));

        // Act & Assert
        assertDoesNotThrow(() -> emailService.sendProposalReceivedEmail(CLIENT_EMAIL, FREELANCER_NAME, PROJECT_TITLE));
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    /**
     * Test: Send proposal accepted email - Success scenario
     */
    @Test
    void shouldSendEmail_whenProposalAcceptedEmailIsSent() {
        // Arrange
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        // Act
        emailService.sendProposalAcceptedEmail(FREELANCER_EMAIL, PROJECT_TITLE);

        // Assert
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(messageCaptor.capture());

        SimpleMailMessage sentMessage = messageCaptor.getValue();
        assertNotNull(sentMessage);
        assertEquals(FREELANCER_EMAIL, sentMessage.getTo()[0]);
        assertTrue(sentMessage.getSubject().contains("Congratulations"));
        assertTrue(sentMessage.getSubject().contains(PROJECT_TITLE));
        assertTrue(sentMessage.getSubject().contains("Accepted"));
        assertTrue(sentMessage.getText().contains("Dear Freelancer"));
        assertTrue(sentMessage.getText().contains(PROJECT_TITLE));
        assertTrue(sentMessage.getText().contains("Next Steps:"));
    }

    /**
     * Test: Send proposal accepted email - Exception handling
     */
    @Test
    void shouldHandleException_whenProposalAcceptedEmailFails() {
        // Arrange
        doThrow(new RuntimeException("Mail server error")).when(mailSender).send(any(SimpleMailMessage.class));

        // Act & Assert
        assertDoesNotThrow(() -> emailService.sendProposalAcceptedEmail(FREELANCER_EMAIL, PROJECT_TITLE));
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    /**
     * Test: Send payment completed email - Success scenario
     */
    @Test
    void shouldSendEmail_whenPaymentCompletedEmailIsSent() {
        // Arrange
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        // Act
        emailService.sendPaymentCompletedEmail(CLIENT_EMAIL, AMOUNT, TRANSACTION_ID, CURRENCY);

        // Assert
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(messageCaptor.capture());

        SimpleMailMessage sentMessage = messageCaptor.getValue();
        assertNotNull(sentMessage);
        assertEquals(CLIENT_EMAIL, sentMessage.getTo()[0]);
        assertTrue(sentMessage.getSubject().contains("Payment Confirmation"));
        assertTrue(sentMessage.getSubject().contains(TRANSACTION_ID));
        assertTrue(sentMessage.getText().contains("Dear User"));
        assertTrue(sentMessage.getText().contains(AMOUNT.toString()));
        assertTrue(sentMessage.getText().contains(CURRENCY));
        assertTrue(sentMessage.getText().contains(TRANSACTION_ID));
        assertTrue(sentMessage.getText().contains("COMPLETED"));
    }

    /**
     * Test: Send payment completed email - Exception handling
     */
    @Test
    void shouldHandleException_whenPaymentCompletedEmailFails() {
        // Arrange
        doThrow(new RuntimeException("Mail server error")).when(mailSender).send(any(SimpleMailMessage.class));

        // Act & Assert
        assertDoesNotThrow(() -> 
            emailService.sendPaymentCompletedEmail(CLIENT_EMAIL, AMOUNT, TRANSACTION_ID, CURRENCY)
        );
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    /**
     * Test: Send payment received email - Success scenario
     */
    @Test
    void shouldSendEmail_whenPaymentReceivedEmailIsSent() {
        // Arrange
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        // Act
        emailService.sendPaymentReceivedEmail(FREELANCER_EMAIL, AMOUNT, TRANSACTION_ID, PROJECT_TITLE, CURRENCY);

        // Assert
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(messageCaptor.capture());

        SimpleMailMessage sentMessage = messageCaptor.getValue();
        assertNotNull(sentMessage);
        assertEquals(FREELANCER_EMAIL, sentMessage.getTo()[0]);
        assertTrue(sentMessage.getSubject().contains("Payment Received"));
        assertTrue(sentMessage.getSubject().contains(AMOUNT.toString()));
        assertTrue(sentMessage.getSubject().contains(CURRENCY));
        assertTrue(sentMessage.getText().contains("Dear Freelancer"));
        assertTrue(sentMessage.getText().contains("Great news"));
        assertTrue(sentMessage.getText().contains(AMOUNT.toString()));
        assertTrue(sentMessage.getText().contains(PROJECT_TITLE));
        assertTrue(sentMessage.getText().contains(TRANSACTION_ID));
    }

    /**
     * Test: Send payment received email - Exception handling
     */
    @Test
    void shouldHandleException_whenPaymentReceivedEmailFails() {
        // Arrange
        doThrow(new RuntimeException("Mail server error")).when(mailSender).send(any(SimpleMailMessage.class));

        // Act & Assert
        assertDoesNotThrow(() -> 
            emailService.sendPaymentReceivedEmail(FREELANCER_EMAIL, AMOUNT, TRANSACTION_ID, PROJECT_TITLE, CURRENCY)
        );
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    /**
     * Test: Send generic email - Success scenario
     */
    @Test
    void shouldSendEmail_whenGenericEmailIsSent() {
        // Arrange
        String subject = "Test Subject";
        String bodyContent = "Test body content";
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        // Act
        emailService.sendGenericEmail(CLIENT_EMAIL, subject, bodyContent);

        // Assert
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(messageCaptor.capture());

        SimpleMailMessage sentMessage = messageCaptor.getValue();
        assertNotNull(sentMessage);
        assertEquals(CLIENT_EMAIL, sentMessage.getTo()[0]);
        assertEquals(subject, sentMessage.getSubject());
        assertTrue(sentMessage.getText().contains(bodyContent));
        assertTrue(sentMessage.getText().contains("Sent at:"));
    }

    /**
     * Test: Send generic email - Exception handling
     */
    @Test
    void shouldHandleException_whenGenericEmailFails() {
        // Arrange
        String subject = "Test Subject";
        String bodyContent = "Test body content";
        doThrow(new RuntimeException("Mail server error")).when(mailSender).send(any(SimpleMailMessage.class));

        // Act & Assert
        assertDoesNotThrow(() -> emailService.sendGenericEmail(CLIENT_EMAIL, subject, bodyContent));
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    /**
     * Test: Email contains correct sender address
     */
    @Test
    void shouldSetCorrectFromAddress_whenSendingEmail() {
        // Arrange
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        // Act
        emailService.sendProjectCreatedEmail(CLIENT_EMAIL, PROJECT_TITLE);

        // Assert
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(messageCaptor.capture());

        SimpleMailMessage sentMessage = messageCaptor.getValue();
        assertNotNull(sentMessage.getFrom());
        assertTrue(sentMessage.getFrom().contains("suchitranv5@gmail.com"));
    }

    /**
     * Test: Email content includes timestamp
     */
    @Test
    void shouldIncludeTimestamp_inEmailBody() {
        // Arrange
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        // Act
        emailService.sendProjectCreatedEmail(CLIENT_EMAIL, PROJECT_TITLE);

        // Assert
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(messageCaptor.capture());

        SimpleMailMessage sentMessage = messageCaptor.getValue();
        assertTrue(sentMessage.getText().contains("Sent at:"));
        // Verify timestamp exists (more flexible pattern)
        assertTrue(sentMessage.getText().matches("(?s).*Sent at: \\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}.*"),
                "Email should contain timestamp in format yyyy-MM-dd HH:mm:ss");
    }

    /**
     * Test: Multiple emails can be sent sequentially
     */
    @Test
    void shouldSendMultipleEmails_whenCalledMultipleTimes() {
        // Arrange
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        // Act
        emailService.sendProjectCreatedEmail(CLIENT_EMAIL, PROJECT_TITLE);
        emailService.sendProposalReceivedEmail(CLIENT_EMAIL, FREELANCER_NAME, PROJECT_TITLE);
        emailService.sendPaymentCompletedEmail(CLIENT_EMAIL, AMOUNT, TRANSACTION_ID, CURRENCY);

        // Assert
        verify(mailSender, times(3)).send(any(SimpleMailMessage.class));
    }

    /**
     * Test: Email with null recipient should handle gracefully
     */
    @Test
    void shouldHandleNullRecipient_whenSendingEmail() {
        // Arrange
        doThrow(new IllegalArgumentException("Recipient cannot be null"))
                .when(mailSender).send(any(SimpleMailMessage.class));

        // Act & Assert
        assertDoesNotThrow(() -> emailService.sendProjectCreatedEmail(null, PROJECT_TITLE));
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    /**
     * Test: Email body formatting for project created email
     */
    @Test
    void shouldFormatEmailCorrectly_forProjectCreated() {
        // Arrange
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        // Act
        emailService.sendProjectCreatedEmail(CLIENT_EMAIL, PROJECT_TITLE);

        // Assert
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(messageCaptor.capture());

        SimpleMailMessage sentMessage = messageCaptor.getValue();
        String emailBody = sentMessage.getText();
        
        // Verify email structure
        assertTrue(emailBody.contains("Dear Client"));
        assertTrue(emailBody.contains("successfully posted"));
        assertTrue(emailBody.contains("What's Next?"));
        assertTrue(emailBody.contains("Best regards"));
        assertTrue(emailBody.contains("Freelance Nexus Team"));
    }

    /**
     * Test: Email body formatting for payment received email
     */
    @Test
    void shouldFormatEmailCorrectly_forPaymentReceived() {
        // Arrange
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        // Act
        emailService.sendPaymentReceivedEmail(FREELANCER_EMAIL, AMOUNT, TRANSACTION_ID, PROJECT_TITLE, CURRENCY);

        // Assert
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(messageCaptor.capture());

        SimpleMailMessage sentMessage = messageCaptor.getValue();
        String emailBody = sentMessage.getText();
        
        // Verify email structure
        assertTrue(emailBody.contains("Dear Freelancer"));
        assertTrue(emailBody.contains("Payment Details:"));
        assertTrue(emailBody.contains("Amount:"));
        assertTrue(emailBody.contains("Project:"));
        assertTrue(emailBody.contains("Transaction ID:"));
        assertTrue(emailBody.contains("funds are now available"));
    }
}