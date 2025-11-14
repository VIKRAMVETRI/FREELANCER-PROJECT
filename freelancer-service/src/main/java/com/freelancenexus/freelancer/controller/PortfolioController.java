package com.freelancenexus.freelancer.controller;

import com.freelancenexus.freelancer.dto.PortfolioDTO;
import com.freelancenexus.freelancer.service.PortfolioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST controller for managing freelancer portfolios.
 * Provides endpoints for adding, retrieving, updating, and deleting portfolio items.
 */
@RestController
@RequestMapping("/api/freelancers")
@RequiredArgsConstructor
@Slf4j
public class PortfolioController {

    private final PortfolioService portfolioService;

    /**
     * Adds a portfolio item for a freelancer.
     *
     * @param id the ID of the freelancer
     * @param portfolioDTO the portfolio item to be added
     * @return the created portfolio item
     */
    @PostMapping("/{id}/portfolio")
    @PreAuthorize("hasRole('FREELANCER')")
    public ResponseEntity<PortfolioDTO> addPortfolio(
            @PathVariable Long id,
            @Valid @RequestBody PortfolioDTO portfolioDTO) {
        log.info("REST request to add portfolio for freelancer ID: {}", id);
        PortfolioDTO created = portfolioService.addPortfolio(id, portfolioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Retrieves all portfolio items for a freelancer.
     *
     * @param id the ID of the freelancer
     * @return the list of portfolio items
     */
    @GetMapping("/{id}/portfolio")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<PortfolioDTO>> getFreelancerPortfolios(@PathVariable Long id) {
        log.info("REST request to get portfolios for freelancer ID: {}", id);
        List<PortfolioDTO> portfolios = portfolioService.getFreelancerPortfolios(id);
        return ResponseEntity.ok(portfolios);
    }

    /**
     * Retrieves a portfolio item by its ID.
     *
     * @param portfolioId the ID of the portfolio item
     * @return the portfolio item
     */
    @GetMapping("/portfolio/{portfolioId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PortfolioDTO> getPortfolioById(@PathVariable Long portfolioId) {
        log.info("REST request to get portfolio by ID: {}", portfolioId);
        PortfolioDTO portfolio = portfolioService.getPortfolioById(portfolioId);
        return ResponseEntity.ok(portfolio);
    }

    /**
     * Updates a portfolio item.
     *
     * @param portfolioId the ID of the portfolio item to be updated
     * @param portfolioDTO the updated portfolio item
     * @return the updated portfolio item
     */
    @PutMapping("/portfolio/{portfolioId}")
    @PreAuthorize("hasRole('FREELANCER')")
    public ResponseEntity<PortfolioDTO> updatePortfolio(
            @PathVariable Long portfolioId,
            @Valid @RequestBody PortfolioDTO portfolioDTO) {
        log.info("REST request to update portfolio with ID: {}", portfolioId);
        PortfolioDTO updated = portfolioService.updatePortfolio(portfolioId, portfolioDTO);
        return ResponseEntity.ok(updated);
    }

    /**
     * Deletes a portfolio item.
     *
     * @param portfolioId the ID of the portfolio item to be deleted
     * @return a response entity with no content
     */
    @DeleteMapping("/portfolio/{portfolioId}")
    @PreAuthorize("hasRole('FREELANCER')")
    public ResponseEntity<Void> deletePortfolio(@PathVariable Long portfolioId) {
        log.info("REST request to delete portfolio with ID: {}", portfolioId);
        portfolioService.deletePortfolio(portfolioId);
        return ResponseEntity.noContent().build();
    }
}