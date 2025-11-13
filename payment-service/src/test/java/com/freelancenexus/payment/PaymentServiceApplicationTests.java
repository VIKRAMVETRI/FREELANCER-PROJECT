package com.freelancenexus.payment_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = com.freelancenexus.payment.PaymentServiceApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class PaymentServiceApplicationTests {

    @Test
    void contextLoads() {
        // This test verifies that the Spring application context loads successfully
    }
}