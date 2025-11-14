package com.freelancenexus.freelancer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a freelancer.
 */
@Entity
@Table(name = "freelancers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Freelancer {
    
    /**
     * The unique identifier of the freelancer.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * The user ID associated with the freelancer.
     */
    @Column(name = "user_id", unique = true, nullable = false)
    private Long userId;
    
    /**
     * The title or headline of the freelancer profile.
     */
    @Column(nullable = false, length = 255)
    private String title;
    
    /**
     * The biography or description of the freelancer.
     */
    @Column(columnDefinition = "TEXT")
    private String bio;
    
    /**
     * The hourly rate of the freelancer.
     */
    @Column(name = "hourly_rate", precision = 10, scale = 2)
    private BigDecimal hourlyRate;
    
    /**
     * The availability status of the freelancer.
     */
    @Column(length = 50)
    private String availability;
    
    /**
     * The total earnings of the freelancer.
     */
    @Column(name = "total_earnings", precision = 12, scale = 2)
    private BigDecimal totalEarnings = BigDecimal.ZERO;
    
    /**
     * The number of completed projects by the freelancer.
     */
    @Column(name = "completed_projects")
    private Integer completedProjects = 0;
    
    /**
     * The average rating of the freelancer.
     */
    @Column(name = "average_rating", precision = 3, scale = 2)
    private BigDecimal averageRating = BigDecimal.ZERO;
    
    /**
     * The list of skills associated with the freelancer.
     */
    @OneToMany(mappedBy = "freelancer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Skill> skills = new ArrayList<>();
    
    /**
     * The list of portfolio items associated with the freelancer.
     */
    @OneToMany(mappedBy = "freelancer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Portfolio> portfolios = new ArrayList<>();
    
    /**
     * The list of ratings received by the freelancer.
     */
    @OneToMany(mappedBy = "freelancer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings = new ArrayList<>();
    
    /**
     * The timestamp when the freelancer profile was created.
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    /**
     * The timestamp when the freelancer profile was last updated.
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    /**
     * Sets the creation and update timestamps before persisting the entity.
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    /**
     * Sets the update timestamp before updating the entity.
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    /**
     * Adds a skill to the freelancer.
     *
     * @param skill the skill to add
     */
    public void addSkill(Skill skill) {
        skills.add(skill);
        skill.setFreelancer(this);
    }
    
    /**
     * Removes a skill from the freelancer.
     *
     * @param skill the skill to remove
     */
    public void removeSkill(Skill skill) {
        skills.remove(skill);
        skill.setFreelancer(null);
    }
    
    /**
     * Adds a portfolio item to the freelancer.
     *
     * @param portfolio the portfolio item to add
     */
    public void addPortfolio(Portfolio portfolio) {
        portfolios.add(portfolio);
        portfolio.setFreelancer(this);
    }
    
    /**
     * Removes a portfolio item from the freelancer.
     *
     * @param portfolio the portfolio item to remove
     */
    public void removePortfolio(Portfolio portfolio) {
        portfolios.remove(portfolio);
        portfolio.setFreelancer(null);
    }
    
    /**
     * Adds a rating to the freelancer.
     *
     * @param rating the rating to add
     */
    public void addRating(Rating rating) {
        ratings.add(rating);
        rating.setFreelancer(this);
    }
}