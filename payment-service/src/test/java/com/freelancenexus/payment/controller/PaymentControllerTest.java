package com.freelancenexus.payment.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freelancenexus.payment.dto.PaymentHistoryDTO;
import com.freelancenexus.payment.dto.PaymentRequestDTO;
import com.freelancenexus.payment.dto.PaymentResponseDTO;
import com.freelancenexus.payment.dto.TransactionStatusDTO;
import com.freelancenexus.payment.model.PaymentStatus;
import com.freelancenexus.payment.service.PaymentService;

@ExtendWith(MockitoExtension.class)
class PaymentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    private ObjectMapper objectMapper;

    private PaymentRequestDTO paymentRequest;
    private PaymentResponseDTO paymentResponse;
    private TransactionStatusDTO transactionStatus;

    // Global exception handler for tests
    @RestControllerAdvice
    static class TestExceptionHandler {
        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }

        @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
            return ResponseEntity.internalServerError().body(Map.of("error", ex.getMessage()));
        }
    }

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules(); // Register JavaTimeModule for LocalDateTime
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController)
                .setControllerAdvice(new TestExceptionHandler())
                .build();

        paymentRequest = PaymentRequestDTO.builder()
                .projectId(1L)
                .payerId(2L)
                .payeeId(3L)
                .amount(BigDecimal.valueOf(100))
                .currency("INR")
                .paymentMethod("UPI")
                .upiId("test@upi")
                .description("Test payment")
                .build();

        paymentResponse = PaymentResponseDTO.builder()
                .id(1L)
                .transactionId("TXN123")
                .projectId(1L)
                .payerId(2L)
                .payeeId(3L)
                .amount(BigDecimal.valueOf(100))
                .currency("INR")
                .paymentMethod("UPI")
                .upiId("test@upi")
                .status(PaymentStatus.COMPLETED)
                .paymentDate(LocalDateTime.now())
                .description("Test payment")
                .build();

        transactionStatus = TransactionStatusDTO.builder()
                .transactionId("TXN123")
                .status(PaymentStatus.COMPLETED)
                .amount(BigDecimal.valueOf(100))
                .currency("INR")
                .timestamp(LocalDateTime.now())
                .message("Payment verified")
                .build();
    }

    @Test
    void shouldInitiatePaymentSuccessfully() throws Exception {
        when(paymentService.initiatePayment(any(PaymentRequestDTO.class))).thenReturn(paymentResponse);

        mockMvc.perform(post("/api/payments/initiate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.transactionId").value("TXN123"))
                .andExpect(jsonPath("$.amount").value(100));
    }

    @Test
    void shouldFailInitiatePayment_whenInvalidRequest() throws Exception {
        when(paymentService.initiatePayment(any(PaymentRequestDTO.class)))
                .thenThrow(new IllegalArgumentException("Invalid request"));

        mockMvc.perform(post("/api/payments/initiate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid request"));
    }

    @Test
    void shouldVerifyPaymentSuccessfully() throws Exception {
        when(paymentService.verifyPayment("TXN123")).thenReturn(transactionStatus);

        mockMvc.perform(post("/api/payments/verify")
                        .param("transactionId", "TXN123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("COMPLETED"))
                .andExpect(jsonPath("$.transactionId").value("TXN123"));
    }

    @Test
    void shouldGetPaymentByIdSuccessfully() throws Exception {
        when(paymentService.getPaymentById(1L)).thenReturn(paymentResponse);

        mockMvc.perform(get("/api/payments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldReturnNotFound_whenPaymentByIdDoesNotExist() throws Exception {
        when(paymentService.getPaymentById(1L)).thenThrow(new RuntimeException("Not found"));

        mockMvc.perform(get("/api/payments/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetPaymentByTransactionIdSuccessfully() throws Exception {
        when(paymentService.getPaymentByTransactionId("TXN123")).thenReturn(paymentResponse);

        mockMvc.perform(get("/api/payments/transaction/TXN123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value("TXN123"));
    }

    @Test
    void shouldReturnNotFound_whenPaymentByTransactionIdDoesNotExist() throws Exception {
        when(paymentService.getPaymentByTransactionId("TXN123")).thenThrow(new RuntimeException("Not found"));

        mockMvc.perform(get("/api/payments/transaction/TXN123"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetPaymentsByProject() throws Exception {
        when(paymentService.getPaymentsByProject(1L)).thenReturn(List.of(paymentResponse));

        mockMvc.perform(get("/api/payments/project/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void shouldGetUserPaymentHistory() throws Exception {
        when(paymentService.getUserPaymentHistory(2L)).thenReturn(List.of(paymentResponse));

        mockMvc.perform(get("/api/payments/user/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].payerId").value(2));
    }

    @Test
    void shouldRefundPaymentSuccessfully() throws Exception {
        when(paymentService.refundPayment(1L, "Customer request")).thenReturn(paymentResponse);

        mockMvc.perform(post("/api/payments/1/refund")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"reason\":\"Customer request\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldReturnBadRequest_whenRefundFails() throws Exception {
        when(paymentService.refundPayment(1L, "Customer request")).thenThrow(new IllegalStateException("Cannot refund"));

        mockMvc.perform(post("/api/payments/1/refund")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"reason\":\"Customer request\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetTransactionHistory() throws Exception {
        PaymentHistoryDTO historyDTO = PaymentHistoryDTO.builder().id(1L).build();
        when(paymentService.getTransactionHistory(1L)).thenReturn(List.of(historyDTO));

        mockMvc.perform(get("/api/payments/1/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void shouldGetUserTransactionHistory() throws Exception {
        PaymentHistoryDTO historyDTO = PaymentHistoryDTO.builder().id(1L).build();
        when(paymentService.getUserTransactionHistory(2L)).thenReturn(List.of(historyDTO));

        mockMvc.perform(get("/api/payments/history/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void shouldReturnHealthCheckStatus() throws Exception {
        mockMvc.perform(get("/api/payments/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.service").value("Payment Service"));
    }
}