package com.freelancenexus.projectservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.freelancenexus.projectservice.config.GeminiConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.*;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GeminiIntegrationServiceTest {

    @Mock
    private WebClient geminiWebClient;

    @Mock
    private GeminiConfig geminiConfig;

    // Use REAL ObjectMapper instead of mock
    private ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private GeminiIntegrationService geminiService;

    @Mock
    private RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    private RequestBodySpec requestBodySpec;

    @Mock
    private RequestHeadersSpec requestHeadersSpec;

    @Mock
    private ResponseSpec responseSpec;

    @BeforeEach
    void setUp() {
        // Use lenient for config mocks
        lenient().when(geminiConfig.getModel()).thenReturn("test-model");
        lenient().when(geminiConfig.getApiKey()).thenReturn("test-key");
        lenient().when(geminiConfig.getTimeout()).thenReturn(5000);
        lenient().when(geminiConfig.getMaxRetries()).thenReturn(2);
        
        // Manually inject the real ObjectMapper
        geminiService = new GeminiIntegrationService(geminiWebClient, geminiConfig, objectMapper);
    }

    @Test
    void shouldCallGeminiSuccessfully() {
        String prompt = "Test prompt";
        String mockResponse = "{\"candidates\":[{\"content\":{\"parts\":[{\"text\":\"response text\"}]}}]}";

        when(geminiWebClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just(mockResponse));

        String result = geminiService.callGemini(prompt);

        assertEquals("response text", result);
    }

    @Test
    void shouldReturnErrorMessageWhenGeminiReturnsError() {
        String prompt = "Test prompt";
        String errorResponse = "{\"error\":\"Something went wrong\"}";

        when(geminiWebClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just(errorResponse));

        String result = geminiService.callGemini(prompt);

        assertTrue(result.contains("Error from API"));
    }

    @Test
    void shouldCallGeminiForJsonSuccessfully() throws Exception {
        String prompt = "Test prompt";
        // Use escaped quotes to create proper nested JSON string
        String mockResponse = "{\"candidates\":[{\"content\":{\"parts\":[{\"text\":\"{\\\"key\\\":\\\"value\\\"}\"}]}}]}";

        when(geminiWebClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just(mockResponse));

        JsonNode result = geminiService.callGeminiForJson(prompt);

        assertNotNull(result);
        assertTrue(result.has("key"));
        assertEquals("value", result.get("key").asText());
    }

    @Test
    void shouldReturnEmptyJsonNodeWhenExceptionOccurs() {
        String prompt = "Test prompt";
        String mockResponse = "{\"candidates\":[{\"content\":{\"parts\":[{\"text\":\"invalid json content\"}]}}]}";
        
        when(geminiWebClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just(mockResponse));

        JsonNode result = geminiService.callGeminiForJson(prompt);

        assertNotNull(result);
        assertTrue(result.isEmpty()); // Empty ObjectNode
    }

    @Test
    void shouldBuildGeminiRequestCorrectly() throws Exception {
        // Using reflection to access private method
        java.lang.reflect.Method method = GeminiIntegrationService.class
                .getDeclaredMethod("buildGeminiRequest", String.class);
        method.setAccessible(true);

        Object request = method.invoke(geminiService, "Test prompt");
        assertNotNull(request);
    }

    @Test
    void shouldExtractTextFromResponseCorrectly() throws Exception {
        java.lang.reflect.Method method = GeminiIntegrationService.class
                .getDeclaredMethod("extractTextFromResponse", String.class);
        method.setAccessible(true);

        String response = "{\"candidates\":[{\"content\":{\"parts\":[{\"text\":\"Hello World\"}]}}]}";
        String result = (String) method.invoke(geminiService, response);

        assertEquals("Hello World", result);
    }
}