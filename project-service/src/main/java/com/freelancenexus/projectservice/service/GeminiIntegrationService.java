package com.freelancenexus.projectservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freelancenexus.projectservice.config.GeminiConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * GeminiIntegrationService
 *
 * <p>Integration layer responsible for communicating with the Google Gemini API.
 * Handles building request payloads, invoking the remote model via a configured
 * {@link WebClient}, extracting text responses, and parsing JSON content embedded
 * in markdown code blocks. Implements simple retry and timeout behavior and returns
 * structured results for higher-level AI services.</p>
 *
 * <p>All network errors are logged and returned as error payloads to allow callers
 * to implement graceful fallbacks.</p>
 *
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GeminiIntegrationService {

    /**
     * WebClient configured for calling the Gemini API.
     */
    private final WebClient geminiWebClient;

    /**
     * Configuration properties (API key, model, timeouts, retries) for Gemini.
     */
    private final GeminiConfig geminiConfig;

    /**
     * Jackson ObjectMapper used to parse JSON responses and extract structured content.
     */
    private final ObjectMapper objectMapper;

    /**
     * Call the Gemini API with a plain text prompt and return the raw response text.
     *
     * <p>This method constructs the request body, posts to the Gemini endpoint, applies
     * timeout and retry policies, and extracts the generated text from the JSON response.
     * Network or parsing errors are logged and returned as an error string.</p>
     *
     * @param prompt the prompt text to send to Gemini
     * @return the extracted response text or an error description
     */
    public String callGemini(String prompt) {
        try {
            log.info("Calling Gemini API with prompt length: {}", prompt.length());

            Map<String, Object> requestBody = buildGeminiRequest(prompt);
            String url = String.format("/%s:generateContent?key=%s", 
                    geminiConfig.getModel(), geminiConfig.getApiKey());

            String response = geminiWebClient.post()
                    .uri(url)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofMillis(geminiConfig.getTimeout()))
                    .retryWhen(Retry.backoff(geminiConfig.getMaxRetries(), Duration.ofSeconds(2))
                            .filter(throwable -> throwable instanceof Exception))
                    .onErrorResume(error -> {
                        log.error("Gemini API call failed: {}", error.getMessage());
                        return Mono.just("{\"error\": \"" + error.getMessage() + "\"}");
                    })
                    .block();

            return extractTextFromResponse(response);

        } catch (Exception e) {
            log.error("Error calling Gemini API", e);
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Call the Gemini API with a prompt and return a parsed JSON tree.
     *
     * <p>If the model returns JSON wrapped in markdown code fences (```json ... ```),
     * this method extracts the JSON block before parsing. On parsing failure an empty
     * JSON object node is returned.</p>
     *
     * @param prompt the prompt text to send to Gemini
     * @return {@link JsonNode} parsed from the Gemini response (may be empty on error)
     */
    public JsonNode callGeminiForJson(String prompt) {
        try {
            String response = callGemini(prompt);
            // Extract JSON from markdown code blocks if present
            String jsonStr = response;
            if (response.contains("```json")) {
                jsonStr = response.substring(response.indexOf("```json") + 7);
                jsonStr = jsonStr.substring(0, jsonStr.indexOf("```"));
            } else if (response.contains("```")) {
                jsonStr = response.substring(response.indexOf("```") + 3);
                jsonStr = jsonStr.substring(0, jsonStr.indexOf("```"));
            }
            return objectMapper.readTree(jsonStr.trim());
        } catch (Exception e) {
            log.error("Error parsing JSON from Gemini response", e);
            return objectMapper.createObjectNode();
        }
    }

    /**
     * Build the request body map required by the Gemini generateContent API.
     *
     * <p>Includes the prompt content and a generationConfig map (temperature, topK, topP,
     * maxOutputTokens). Callers may adjust configuration via {@link GeminiConfig} if needed.</p>
     *
     * @param prompt the prompt text to include in the request
     * @return a map representing the JSON request body to send to Gemini
     */
    private Map<String, Object> buildGeminiRequest(String prompt) {
        Map<String, Object> request = new HashMap<>();
        
        Map<String, Object> content = new HashMap<>();
        Map<String, String> part = new HashMap<>();
        part.put("text", prompt);
        content.put("parts", new Object[]{part});
        
        request.put("contents", new Object[]{content});
        
        // Configuration for better responses
        Map<String, Object> generationConfig = new HashMap<>();
        generationConfig.put("temperature", 0.7);
        generationConfig.put("topK", 40);
        generationConfig.put("topP", 0.95);
        generationConfig.put("maxOutputTokens", 2048);
        request.put("generationConfig", generationConfig);
        
        return request;
    }

    /**
     * Extract the textual content from the Gemini API JSON response.
     *
     * <p>Parses the API response, checks for an error field, and navigates the
     * "candidates -> content -> parts" structure to return the generated text.
     * Returns a descriptive message if no content is present or parsing fails.</p>
     *
     * @param jsonResponse the raw JSON string returned by the Gemini API
     * @return extracted generated text or an error message
     */
    private String extractTextFromResponse(String jsonResponse) {
        try {
            JsonNode root = objectMapper.readTree(jsonResponse);
            
            if (root.has("error")) {
                log.error("Gemini API error: {}", root.get("error"));
                return "Error from API: " + root.get("error").asText();
            }
            
            JsonNode candidates = root.path("candidates");
            if (candidates.isArray() && candidates.size() > 0) {
                JsonNode content = candidates.get(0).path("content");
                JsonNode parts = content.path("parts");
                if (parts.isArray() && parts.size() > 0) {
                    return parts.get(0).path("text").asText();
                }
            }
            
            return "No response from Gemini API";
        } catch (Exception e) {
            log.error("Error extracting text from Gemini response", e);
            return "Error parsing response: " + e.getMessage();
        }
    }
}