package com.freelancenexus.freelancer.service;

import com.freelancenexus.freelancer.dto.FreelancerDTO;
import com.freelancenexus.freelancer.dto.FreelancerProfileDTO;
import com.freelancenexus.freelancer.dto.PortfolioDTO;
import com.freelancenexus.freelancer.dto.RatingDTO;
import com.freelancenexus.freelancer.dto.SkillDTO;
import com.freelancenexus.freelancer.exception.FreelancerAlreadyExistsException;
import com.freelancenexus.freelancer.exception.ResourceNotFoundException;
import com.freelancenexus.freelancer.model.Freelancer;
import com.freelancenexus.freelancer.model.Skill;
import com.freelancenexus.freelancer.repository.FreelancerRepository;
import com.freelancenexus.freelancer.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service class for managing freelancer profiles.
 * Provides operations to create, update, retrieve, and search freelancers.
 *
 * <p>This class interacts with {@link FreelancerRepository} and {@link RatingRepository}
 * to perform database operations and manage freelancer details including skills and ratings.</p>
 *
 * @author YourName
 * @version 1.0
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class FreelancerService {

    private final FreelancerRepository freelancerRepository;
    private final RatingRepository ratingRepository;

    /**
     * Creates a new freelancer profile.
     *
     * @param freelancerDTO the freelancer details to create
     * @return the created freelancer as {@link FreelancerDTO}
     * @throws FreelancerAlreadyExistsException if a freelancer profile already exists for the given user ID
     */

    @Transactional
    public FreelancerDTO createFreelancer(FreelancerDTO freelancerDTO) {
        log.info("Creating freelancer profile for user ID: {}", freelancerDTO.getUserId());
        
        if (freelancerRepository.existsByUserId(freelancerDTO.getUserId())) {
            throw new FreelancerAlreadyExistsException("Freelancer profile already exists for user ID: " + freelancerDTO.getUserId());
        }
        
        Freelancer freelancer = mapToEntity(freelancerDTO);
        
        // Add skills if provided
        if (freelancerDTO.getSkills() != null) {
            for (SkillDTO skillDTO : freelancerDTO.getSkills()) {
                Skill skill = new Skill();
                skill.setSkillName(skillDTO.getSkillName());
                skill.setProficiencyLevel(skillDTO.getProficiencyLevel());
                skill.setYearsExperience(skillDTO.getYearsExperience());
                freelancer.addSkill(skill);
            }
        }
        
        Freelancer saved = freelancerRepository.save(freelancer);
        log.info("Freelancer profile created with ID: {}", saved.getId());
        
        return mapToDTO(saved);
    }

    /**
     * Retrieves a freelancer by ID along with their skills.
     *
     * @param id the freelancer ID
     * @return the freelancer details as {@link FreelancerDTO}
     * @throws ResourceNotFoundException if the freelancer does not exist
     */

    @Transactional(readOnly = true)
    public FreelancerDTO getFreelancerById(Long id) {
        log.info("Fetching freelancer by ID: {}", id);
        Freelancer freelancer = freelancerRepository.findByIdWithSkills(id)
            .orElseThrow(() -> new ResourceNotFoundException("Freelancer not found with ID: " + id));
        return mapToDTO(freelancer);
    }

    /**
     * Retrieves a complete freelancer profile including details, skills, and ratings.
     *
     * @param id the freelancer ID
     * @return the freelancer profile as {@link FreelancerProfileDTO}
     * @throws ResourceNotFoundException if the freelancer does not exist
     */

    @Transactional(readOnly = true)
    public FreelancerProfileDTO getFreelancerProfile(Long id) {
        log.info("Fetching complete freelancer profile for ID: {}", id);
        Freelancer freelancer = freelancerRepository.findByIdWithDetails(id)
            .orElseThrow(() -> new ResourceNotFoundException("Freelancer not found with ID: " + id));
        
        return mapToProfileDTO(freelancer);
    }

    /**
     * Retrieves a freelancer by user ID.
     *
     * @param userId the user ID
     * @return the freelancer details as {@link FreelancerDTO}
     * @throws ResourceNotFoundException if the freelancer does not exist
     */

    @Transactional(readOnly = true)
    public FreelancerDTO getFreelancerByUserId(Long userId) {
        log.info("Fetching freelancer by user ID: {}", userId);
        Freelancer freelancer = freelancerRepository.findByUserId(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Freelancer not found for user ID: " + userId));
        return mapToDTO(freelancer);
    }

    /**
     * Updates an existing freelancer profile.
     *
     * @param id the freelancer ID
     * @param freelancerDTO the updated freelancer details
     * @return the updated freelancer as {@link FreelancerDTO}
     * @throws ResourceNotFoundException if the freelancer does not exist
     */

    @Transactional
    public FreelancerDTO updateFreelancer(Long id, FreelancerDTO freelancerDTO) {
        log.info("Updating freelancer with ID: {}", id);
        
        Freelancer freelancer = freelancerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Freelancer not found with ID: " + id));
        
        // Update basic fields
        if (freelancerDTO.getTitle() != null) {
            freelancer.setTitle(freelancerDTO.getTitle());
        }
        if (freelancerDTO.getBio() != null) {
            freelancer.setBio(freelancerDTO.getBio());
        }
        if (freelancerDTO.getHourlyRate() != null) {
            freelancer.setHourlyRate(freelancerDTO.getHourlyRate());
        }
        if (freelancerDTO.getAvailability() != null) {
            freelancer.setAvailability(freelancerDTO.getAvailability());
        }
        
        // Update skills if provided
        if (freelancerDTO.getSkills() != null) {
            freelancer.getSkills().clear();
            for (SkillDTO skillDTO : freelancerDTO.getSkills()) {
                Skill skill = new Skill();
                skill.setSkillName(skillDTO.getSkillName());
                skill.setProficiencyLevel(skillDTO.getProficiencyLevel());
                skill.setYearsExperience(skillDTO.getYearsExperience());
                freelancer.addSkill(skill);
            }
        }
        
        Freelancer updated = freelancerRepository.save(freelancer);
        log.info("Freelancer updated successfully: {}", id);
        
        return mapToDTO(updated);
    }

    /**
     * Searches freelancers based on filters like hourly rate, rating, and availability.
     *
     * @param minRate minimum hourly rate
     * @param maxRate maximum hourly rate
     * @param minRating minimum average rating
     * @param availability availability status
     * @return a list of matching freelancers as {@link FreelancerDTO}
     */

    @Transactional(readOnly = true)
    public List<FreelancerDTO> searchFreelancers(BigDecimal minRate, BigDecimal maxRate, 
                                                   BigDecimal minRating, String availability) {
        log.info("Searching freelancers with filters - minRate: {}, maxRate: {}, minRating: {}, availability: {}", 
                 minRate, maxRate, minRating, availability);
        
        List<Freelancer> freelancers = freelancerRepository.searchFreelancers(minRate, maxRate, minRating, availability);
        
        return freelancers.stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    /**
     * Retrieves freelancers who have specific skills.
     *
     * @param skills list of skill names
     * @return a list of freelancers as {@link FreelancerDTO}
     */

    @Transactional(readOnly = true)
    public List<FreelancerDTO> getFreelancersBySkills(List<String> skills) {
        log.info("Finding freelancers with skills: {}", skills);
        
        List<String> lowerCaseSkills = skills.stream()
            .map(String::toLowerCase)
            .collect(Collectors.toList());
        
        List<Freelancer> freelancers = freelancerRepository.findBySkills(lowerCaseSkills);
        
        return freelancers.stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    /**
     * Retrieves all freelancers.
     *
     * @return a list of all freelancers as {@link FreelancerDTO}
     */

    @Transactional(readOnly = true)
    public List<FreelancerDTO> getAllFreelancers() {
        log.info("Fetching all freelancers");
        return freelancerRepository.findAll().stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    /**
     * Updates freelancer statistics such as average rating.
     *
     * @param freelancerId the ID of the freelancer whose stats need to be updated
     * @throws ResourceNotFoundException if the freelancer does not exist
     */

    @Transactional
    public void updateFreelancerStats(Long freelancerId) {
        log.info("Updating stats for freelancer: {}", freelancerId);
        
        Freelancer freelancer = freelancerRepository.findById(freelancerId)
            .orElseThrow(() -> new ResourceNotFoundException("Freelancer not found with ID: " + freelancerId));
        
        // Recalculate average rating
        BigDecimal avgRating = ratingRepository.calculateAverageRating(freelancerId);
        freelancer.setAverageRating(avgRating != null ? avgRating : BigDecimal.ZERO);
        
        freelancerRepository.save(freelancer);
        log.info("Stats updated for freelancer: {}", freelancerId);
    }

    /**
     * Converts a {@link Freelancer} entity to a {@link FreelancerDTO}.
     *
     * @param freelancer the freelancer entity
     * @return the corresponding {@link FreelancerDTO}
     */

    private FreelancerDTO mapToDTO(Freelancer freelancer) {
        FreelancerDTO dto = new FreelancerDTO();
        dto.setId(freelancer.getId());
        dto.setUserId(freelancer.getUserId());
        dto.setTitle(freelancer.getTitle());
        dto.setBio(freelancer.getBio());
        dto.setHourlyRate(freelancer.getHourlyRate());
        dto.setAvailability(freelancer.getAvailability());
        dto.setTotalEarnings(freelancer.getTotalEarnings());
        dto.setCompletedProjects(freelancer.getCompletedProjects());
        dto.setAverageRating(freelancer.getAverageRating());
        dto.setCreatedAt(freelancer.getCreatedAt());
        dto.setUpdatedAt(freelancer.getUpdatedAt());
        
        if (freelancer.getSkills() != null) {
            List<SkillDTO> skillDTOs = freelancer.getSkills().stream()
                .map(this::mapSkillToDTO)
                .collect(Collectors.toList());
            dto.setSkills(skillDTOs);
        }
        
        return dto;
    }

    /**
     * Converts a {@link Freelancer} entity to a detailed {@link FreelancerProfileDTO},
     * including skills, portfolios, and recent ratings.
     *
     * @param freelancer the freelancer entity
     * @return the corresponding {@link FreelancerProfileDTO}
     */

    private FreelancerProfileDTO mapToProfileDTO(Freelancer freelancer) {
        FreelancerProfileDTO dto = new FreelancerProfileDTO();
        dto.setId(freelancer.getId());
        dto.setUserId(freelancer.getUserId());
        dto.setTitle(freelancer.getTitle());
        dto.setBio(freelancer.getBio());
        dto.setHourlyRate(freelancer.getHourlyRate());
        dto.setAvailability(freelancer.getAvailability());
        dto.setTotalEarnings(freelancer.getTotalEarnings());
        dto.setCompletedProjects(freelancer.getCompletedProjects());
        dto.setAverageRating(freelancer.getAverageRating());
        dto.setCreatedAt(freelancer.getCreatedAt());
        dto.setUpdatedAt(freelancer.getUpdatedAt());
        
        if (freelancer.getSkills() != null) {
            dto.setSkills(freelancer.getSkills().stream()
                .map(this::mapSkillToDTO)
                .collect(Collectors.toList()));
        }
        
        if (freelancer.getPortfolios() != null) {
            dto.setPortfolios(freelancer.getPortfolios().stream()
                .map(portfolio -> {
                    PortfolioDTO pDto = new PortfolioDTO();
                    pDto.setId(portfolio.getId());
                    pDto.setTitle(portfolio.getTitle());
                    pDto.setDescription(portfolio.getDescription());
                    pDto.setProjectUrl(portfolio.getProjectUrl());
                    pDto.setImageUrl(portfolio.getImageUrl());
                    pDto.setTechnologiesUsed(portfolio.getTechnologiesUsed());
                    pDto.setCompletionDate(portfolio.getCompletionDate());
                    pDto.setCreatedAt(portfolio.getCreatedAt());
                    return pDto;
                })
                .collect(Collectors.toList()));
        }
        
        if (freelancer.getRatings() != null) {
            dto.setRecentRatings(freelancer.getRatings().stream()
                .sorted((r1, r2) -> r2.getCreatedAt().compareTo(r1.getCreatedAt()))
                .limit(5)
                .map(rating -> {
                    RatingDTO rDto = new RatingDTO();
                    rDto.setId(rating.getId());
                    rDto.setClientId(rating.getClientId());
                    rDto.setProjectId(rating.getProjectId());
                    rDto.setRating(rating.getRating());
                    rDto.setReview(rating.getReview());
                    rDto.setCreatedAt(rating.getCreatedAt());
                    return rDto;
                })
                .collect(Collectors.toList()));
        }
        
        return dto;
    }

    /**
     * Converts a {@link Skill} entity to a {@link SkillDTO}.
     *
     * @param skill the skill entity
     * @return the corresponding {@link SkillDTO}
     */

    private SkillDTO mapSkillToDTO(Skill skill) {
        SkillDTO dto = new SkillDTO();
        dto.setId(skill.getId());
        dto.setSkillName(skill.getSkillName());
        dto.setProficiencyLevel(skill.getProficiencyLevel());
        dto.setYearsExperience(skill.getYearsExperience());
        return dto;
    }

    /**
     * Converts a {@link FreelancerDTO} to a {@link Freelancer} entity.
     *
     * @param dto the freelancer DTO
     * @return the corresponding {@link Freelancer} entity
     */

    private Freelancer mapToEntity(FreelancerDTO dto) {
        Freelancer freelancer = new Freelancer();
        freelancer.setUserId(dto.getUserId());
        freelancer.setTitle(dto.getTitle());
        freelancer.setBio(dto.getBio());
        freelancer.setHourlyRate(dto.getHourlyRate());
        freelancer.setAvailability(dto.getAvailability() != null ? dto.getAvailability() : "AVAILABLE");
        return freelancer;
    }
}