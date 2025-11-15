package com.freelancenexus.freelancer.repository;

import com.freelancenexus.freelancer.model.Freelancer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Freelancer} entities.
 */
@Repository
public interface FreelancerRepository extends JpaRepository<Freelancer, Long>, JpaSpecificationExecutor<Freelancer> {
    
    /**
     * Finds a freelancer by their user ID.
     *
     * @param userId the user ID
     * @return an optional containing the freelancer if found
     */
    Optional<Freelancer> findByUserId(Long userId);
    
    /**
     * Checks if a freelancer exists with the given user ID.
     *
     * @param userId the user ID
     * @return true if the freelancer exists, false otherwise
     */
    boolean existsByUserId(Long userId);
    
    /**
     * Finds a freelancer by their ID, including their skills.
     *
     * @param id the ID of the freelancer
     * @return an optional containing the freelancer with skills if found
     */
    @Query("SELECT f FROM Freelancer f LEFT JOIN FETCH f.skills WHERE f.id = :id")
    Optional<Freelancer> findByIdWithSkills(@Param("id") Long id);
    
    /**
     * Finds a freelancer by their ID, including their skills and portfolios.
     *
     * @param id the ID of the freelancer
     * @return an optional containing the freelancer with details if found
     */
    @Query("SELECT f FROM Freelancer f LEFT JOIN FETCH f.skills LEFT JOIN FETCH f.portfolios WHERE f.id = :id")
    Optional<Freelancer> findByIdWithDetails(@Param("id") Long id);
    
    /**
     * Finds freelancers by a list of skills.
     *
     * @param skills the list of skill names
     * @return a list of freelancers matching the skills
     */
    @Query("SELECT DISTINCT f FROM Freelancer f JOIN f.skills s WHERE LOWER(s.skillName) IN :skills")
    List<Freelancer> findBySkills(@Param("skills") List<String> skills);
    
    /**
     * Finds freelancers by their availability status.
     *
     * @param availability the availability status
     * @return a list of freelancers
     */
    List<Freelancer> findByAvailability(String availability);
    
    /**
     * Finds freelancers within a specific hourly rate range.
     *
     * @param minRate the minimum hourly rate
     * @param maxRate the maximum hourly rate
     * @return a list of freelancers
     */
    List<Freelancer> findByHourlyRateBetween(BigDecimal minRate, BigDecimal maxRate);
    
    /**
     * Finds freelancers with an average rating greater than or equal to the specified value.
     *
     * @param minRating the minimum average rating
     * @return a list of freelancers
     */
    List<Freelancer> findByAverageRatingGreaterThanEqual(BigDecimal minRating);
    
    /**
     * Searches for freelancers based on multiple criteria.
     *
     * @param minRate the minimum hourly rate
     * @param maxRate the maximum hourly rate
     * @param minRating the minimum average rating
     * @param availability the availability status
     * @return a list of freelancers matching the criteria
     */
    @Query("SELECT f FROM Freelancer f WHERE " +
           "(:minRate IS NULL OR f.hourlyRate >= :minRate) AND " +
           "(:maxRate IS NULL OR f.hourlyRate <= :maxRate) AND " +
           "(:minRating IS NULL OR f.averageRating >= :minRating) AND " +
           "(:availability IS NULL OR f.availability = :availability)")
    List<Freelancer> searchFreelancers(
        @Param("minRate") BigDecimal minRate,
        @Param("maxRate") BigDecimal maxRate,
        @Param("minRating") BigDecimal minRating,
        @Param("availability") String availability
    );
}