package com.freelancenexus.freelancer.controller;

import com.freelancenexus.freelancer.dto.FreelancerDTO;
import com.freelancenexus.freelancer.dto.FreelancerProfileDTO;
import com.freelancenexus.freelancer.service.FreelancerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * REST controller for managing freelancers.
 * Provides endpoints for creating, retrieving, updating, and searching freelancer profiles.
 */
@RestController
@RequestMapping("/api/freelancers")
@RequiredArgsConstructor
@Slf4j
public class FreelancerController {
    
    private final FreelancerService freelancerService;
    
    /**
     * Creates a freelancer profile.
     *
     * @param freelancerDTO the freelancer profile to be created
     * @return the created freelancer profile
     */
    @PostMapping
    @PreAuthorize("hasRole('FREELANCER')")
    public ResponseEntity<FreelancerDTO> createFreelancer(@Valid @RequestBody FreelancerDTO freelancerDTO) {
        log.info("REST request to create freelancer for user ID: {}", freelancerDTO.getUserId());
        FreelancerDTO created = freelancerService.createFreelancer(freelancerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    /**
     * Retrieves a freelancer profile by its ID.
     *
     * @param id the ID of the freelancer
     * @return the freelancer profile
     */
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<FreelancerDTO> getFreelancerById(@PathVariable Long id) {
        log.info("REST request to get freelancer by ID: {}", id);
        FreelancerDTO freelancer = freelancerService.getFreelancerById(id);
        return ResponseEntity.ok(freelancer);
    }
    
    /**
     * Retrieves the complete profile of a freelancer.
     *
     * @param id the ID of the freelancer
     * @return the freelancer's complete profile
     */
    @GetMapping("/{id}/profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<FreelancerProfileDTO> getFreelancerProfile(@PathVariable Long id) {
        log.info("REST request to get complete freelancer profile for ID: {}", id);
        FreelancerProfileDTO profile = freelancerService.getFreelancerProfile(id);
        return ResponseEntity.ok(profile);
    }
    
    /**
     * Retrieves a freelancer profile by the user ID.
     *
     * @param userId the user ID associated with the freelancer
     * @return the freelancer profile
     */
    @GetMapping("/user/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<FreelancerDTO> getFreelancerByUserId(@PathVariable Long userId) {
        log.info("REST request to get freelancer by user ID: {}", userId);
        FreelancerDTO freelancer = freelancerService.getFreelancerByUserId(userId);
        return ResponseEntity.ok(freelancer);
    }
    
    /**
     * Updates a freelancer profile.
     *
     * @param id the ID of the freelancer
     * @param freelancerDTO the updated freelancer profile
     * @return the updated freelancer profile
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('FREELANCER')")
    public ResponseEntity<FreelancerDTO> updateFreelancer(
            @PathVariable Long id,
            @Valid @RequestBody FreelancerDTO freelancerDTO) {
        log.info("REST request to update freelancer with ID: {}", id);
        FreelancerDTO updated = freelancerService.updateFreelancer(id, freelancerDTO);
        return ResponseEntity.ok(updated);
    }
    
    /**
     * Searches for freelancers based on various criteria.
     *
     * @param minRate the minimum hourly rate
     * @param maxRate the maximum hourly rate
     * @param minRating the minimum rating
     * @param availability the availability status
     * @param skills the list of skills
     * @return the list of freelancers matching the criteria
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<FreelancerDTO>> searchFreelancers(
            @RequestParam(required = false) BigDecimal minRate,
            @RequestParam(required = false) BigDecimal maxRate,
            @RequestParam(required = false) BigDecimal minRating,
            @RequestParam(required = false) String availability,
            @RequestParam(required = false) List<String> skills) {
        log.info("REST request to search freelancers");
        
        List<FreelancerDTO> freelancers;
        
        if (skills != null && !skills.isEmpty()) {
            freelancers = freelancerService.getFreelancersBySkills(skills);
        } else if (minRate != null || maxRate != null || minRating != null || availability != null) {
            freelancers = freelancerService.searchFreelancers(minRate, maxRate, minRating, availability);
        } else {
            freelancers = freelancerService.getAllFreelancers();
        }
        
        return ResponseEntity.ok(freelancers);
    }
}