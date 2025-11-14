package com.freelancenexus.projectservice.controller;

import com.freelancenexus.projectservice.dto.AIRecommendationDTO;
import com.freelancenexus.projectservice.dto.ProjectSummaryDTO;
import com.freelancenexus.projectservice.dto.RankedProposalDTO;
import com.freelancenexus.projectservice.service.AIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AIRecommendationController
 *
 * <p>REST API controller for AI-powered recommendation and analysis features in the Project Service.
 * Provides endpoints for freelancers to get project recommendations, clients to rank proposals,
 * and both roles to view AI-generated project summaries.</p>
 *
 * <p>All endpoints implement role-based access control using {@code @PreAuthorize} annotations.</p>
 *
 * @since 1.0
 */
@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
@Slf4j
public class AIRecommendationController {

    private final AIService aiService;

    /**
     * Get AI-recommended projects for a specific freelancer.
     *
     * <p>Requires FREELANCER role. Uses the freelancer's skills and optional bio to
     * generate personalized project recommendations via the AI service.</p>
     *
     * @param freelancerId the unique identifier of the freelancer
     * @param skills list of skills to match against projects
     * @param bio optional biography/description of the freelancer
     * @return ResponseEntity with HTTP 200 (OK) status and a list of {@link AIRecommendationDTO}
     */
    @GetMapping("/recommendations/freelancer/{freelancerId}")
    @PreAuthorize("hasRole('FREELANCER')")
    public ResponseEntity<List<AIRecommendationDTO>> getRecommendations(
            @PathVariable Long freelancerId,
            @RequestParam List<String> skills,
            @RequestParam(required = false) String bio) {
        log.info("GET /api/ai/recommendations/freelancer/{} - Getting AI recommendations", freelancerId);
        
        List<AIRecommendationDTO> recommendations = aiService.recommendProjectsForFreelancer(
                freelancerId, skills, bio);
        
        return ResponseEntity.ok(recommendations);
    }

    /**
     * Get AI-ranked proposals for a specific project.
     *
     * <p>Requires CLIENT role. Analyzes all proposals submitted for a project and
     * returns them ranked by AI-determined compatibility and quality.</p>
     *
     * @param projectId the unique identifier of the project
     * @return ResponseEntity with HTTP 200 (OK) status and a list of {@link RankedProposalDTO}
     *         sorted by AI ranking score
     */
    @GetMapping("/proposals/rank/{projectId}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<RankedProposalDTO>> rankProposals(@PathVariable Long projectId) {
        log.info("GET /api/ai/proposals/rank/{} - Ranking proposals with AI", projectId);
        
        List<RankedProposalDTO> rankedProposals = aiService.rankProposalsForProject(projectId);
        
        return ResponseEntity.ok(rankedProposals);
    }

    /**
     * Get an AI-generated summary of a project.
     *
     * <p>Requires CLIENT or FREELANCER role. Generates a concise AI-powered summary
     * of the project's description, requirements, and other key details.</p>
     *
     * @param projectId the unique identifier of the project
     * @return ResponseEntity with HTTP 200 (OK) status and the {@link ProjectSummaryDTO}
     */
    @GetMapping("/summary/project/{projectId}")
    @PreAuthorize("hasAnyRole('CLIENT', 'FREELANCER')")
    public ResponseEntity<ProjectSummaryDTO> getProjectSummary(@PathVariable Long projectId) {
        log.info("GET /api/ai/summary/project/{} - Generating AI summary", projectId);
        
        ProjectSummaryDTO summary = aiService.generateProjectSummary(projectId);
        
        return ResponseEntity.ok(summary);
    }

    /**
     * Global exception handler for RuntimeException in this controller.
     *
     * @param ex the thrown {@link RuntimeException}
     * @return ResponseEntity with HTTP 500 (Internal Server Error) status and error message
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        log.error("Error in AIRecommendationController", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("AI service error: " + ex.getMessage());
    }
}