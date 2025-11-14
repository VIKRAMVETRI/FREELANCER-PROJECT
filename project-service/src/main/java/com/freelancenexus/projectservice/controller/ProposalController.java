package com.freelancenexus.projectservice.controller;

import com.freelancenexus.projectservice.dto.ProposalDTO;
import com.freelancenexus.projectservice.dto.ProposalSubmitDTO;
import com.freelancenexus.projectservice.service.ProposalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ProposalController
 *
 * <p>REST API controller for proposal management in the Project Service.
 * Provides endpoints for freelancers to submit proposals, clients to view and accept/reject proposals,
 * and both roles to retrieve proposal information. Implements role-based access control for sensitive operations.</p>
 *
 * <p>Endpoints are categorized by operation type:
 * <ul>
 *   <li><strong>Submission</strong> — freelancers submit proposals for projects</li>
 *   <li><strong>Retrieval</strong> — view proposals by project, freelancer, or id</li>
 *   <li><strong>Ranking</strong> — clients can request AI-ranked proposals</li>
 *   <li><strong>Decision</strong> — clients accept or reject proposals</li>
 * </ul>
 * </p>
 *
 * @since 1.0
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ProposalController {

    private final ProposalService proposalService;

    /**
     * Submit a new proposal for a project.
     *
     * <p>Requires FREELANCER role. Allows a freelancer to submit their proposal
     * (including bid amount, cover letter, etc.) for a specific project.</p>
     *
     * @param projectId the unique identifier of the project
     * @param submitDTO proposal submission details
     * @return ResponseEntity with HTTP 201 (Created) status and the created {@link ProposalDTO}
     */
    @PostMapping("/projects/{projectId}/proposals")
    @PreAuthorize("hasRole('FREELANCER')")
    public ResponseEntity<ProposalDTO> submitProposal(
            @PathVariable Long projectId,
            @Valid @RequestBody ProposalSubmitDTO submitDTO) {
        log.info("POST /api/projects/{}/proposals - Submitting proposal", projectId);
        ProposalDTO proposal = proposalService.submitProposal(projectId, submitDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(proposal);
    }

    /**
     * Retrieve all proposals for a specific project.
     *
     * <p>Requires CLIENT role. Optionally returns AI-ranked proposals if the
     * {@code ranked} query parameter is set to true.</p>
     *
     * @param projectId the unique identifier of the project
     * @param ranked if true, returns proposals ranked by AI; otherwise returns in submission order
     * @return ResponseEntity with HTTP 200 (OK) status and a list of {@link ProposalDTO}
     */
    @GetMapping("/projects/{projectId}/proposals")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<ProposalDTO>> getProjectProposals(
            @PathVariable Long projectId,
            @RequestParam(defaultValue = "false") boolean ranked) {
        log.info("GET /api/projects/{}/proposals - Fetching proposals", projectId);
        
        List<ProposalDTO> proposals;
        if (ranked) {
            proposals = proposalService.getRankedProposalsByProjectId(projectId);
        } else {
            proposals = proposalService.getProposalsByProjectId(projectId);
        }
        
        return ResponseEntity.ok(proposals);
    }

    /**
     * Retrieve a specific proposal by id.
     *
     * <p>Requires CLIENT or FREELANCER role. Returns detailed proposal information.</p>
     *
     * @param id the unique identifier of the proposal
     * @return ResponseEntity with HTTP 200 (OK) status and the {@link ProposalDTO}
     */
    @GetMapping("/proposals/{id}")
    @PreAuthorize("hasAnyRole('CLIENT', 'FREELANCER')")
    public ResponseEntity<ProposalDTO> getProposal(@PathVariable Long id) {
        log.info("GET /api/proposals/{} - Fetching proposal", id);
        ProposalDTO proposal = proposalService.getProposalById(id);
        return ResponseEntity.ok(proposal);
    }

    /**
     * Retrieve all proposals submitted by a specific freelancer.
     *
     * <p>Requires FREELANCER role. Returns the freelancer's submission history.</p>
     *
     * @param freelancerId the unique identifier of the freelancer
     * @return ResponseEntity with HTTP 200 (OK) status and a list of {@link ProposalDTO}
     */
    @GetMapping("/proposals/freelancer/{freelancerId}")
    @PreAuthorize("hasRole('FREELANCER')")
    public ResponseEntity<List<ProposalDTO>> getFreelancerProposals(@PathVariable Long freelancerId) {
        log.info("GET /api/proposals/freelancer/{} - Fetching freelancer proposals", freelancerId);
        List<ProposalDTO> proposals = proposalService.getProposalsByFreelancerId(freelancerId);
        return ResponseEntity.ok(proposals);
    }

    /**
     * Accept a proposal for a project.
     *
     * <p>Requires CLIENT role. Only the client who created the project can accept proposals.
     * Accepting a proposal typically results in the freelancer being assigned to the project.</p>
     *
     * @param id the unique identifier of the proposal to accept
     * @return ResponseEntity with HTTP 200 (OK) status and the updated {@link ProposalDTO}
     */
    @PutMapping("/proposals/{id}/accept")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ProposalDTO> acceptProposal(@PathVariable Long id) {
        log.info("PUT /api/proposals/{}/accept - Accepting proposal", id);
        ProposalDTO proposal = proposalService.acceptProposal(id);
        return ResponseEntity.ok(proposal);
    }

    /**
     * Reject a proposal for a project.
     *
     * <p>Requires CLIENT role. Only the client who created the project can reject proposals.
     * Rejected proposals will not be further considered for the project.</p>
     *
     * @param id the unique identifier of the proposal to reject
     * @return ResponseEntity with HTTP 200 (OK) status and the updated {@link ProposalDTO}
     */
    @PutMapping("/proposals/{id}/reject")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ProposalDTO> rejectProposal(@PathVariable Long id) {
        log.info("PUT /api/proposals/{}/reject - Rejecting proposal", id);
        ProposalDTO proposal = proposalService.rejectProposal(id);
        return ResponseEntity.ok(proposal);
    }

    /**
     * Global exception handler for RuntimeException in this controller.
     *
     * @param ex the thrown {@link RuntimeException}
     * @return ResponseEntity with HTTP 400 (Bad Request) status and error message
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        log.error("Error in ProposalController", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}