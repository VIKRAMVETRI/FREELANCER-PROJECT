package com.freelancenexus.freelancer.controller;

import com.freelancenexus.freelancer.dto.RatingDTO;
import com.freelancenexus.freelancer.service.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST controller for managing freelancer ratings.
 * Provides endpoints for adding and retrieving ratings.
 */
@RestController
@RequestMapping("/api/freelancers")
@RequiredArgsConstructor
@Slf4j
public class RatingController {

    private final RatingService ratingService;

    /**
     * Adds a rating for a freelancer.
     *
     * @param id the ID of the freelancer
     * @param ratingDTO the rating to be added
     * @return the created rating
     */
    // Only clients can add ratings (after project completion)
    @PostMapping("/{id}/ratings")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<RatingDTO> addRating(
            @PathVariable Long id,
            @Valid @RequestBody RatingDTO ratingDTO) {
        log.info("REST request to add rating for freelancer ID: {}", id);
        RatingDTO created = ratingService.addRating(id, ratingDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Retrieves all ratings for a freelancer.
     *
     * @param id the ID of the freelancer
     * @return the list of ratings
     */
    // Anyone authenticated can view ratings
    @GetMapping("/{id}/ratings")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<RatingDTO>> getFreelancerRatings(@PathVariable Long id) {
        log.info("REST request to get ratings for freelancer ID: {}", id);
        List<RatingDTO> ratings = ratingService.getFreelancerRatings(id);
        return ResponseEntity.ok(ratings);
    }
}