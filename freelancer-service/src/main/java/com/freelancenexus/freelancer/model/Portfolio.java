package com.freelancenexus.freelancer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entity representing a portfolio item associated with a freelancer.
 */
@Entity
@Table(name = "portfolios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Portfolio {
    
    /**
     * The unique identifier of the portfolio item.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * The freelancer associated with this portfolio item.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "freelancer_id", nullable = false)
    private Freelancer freelancer;
    
    /**
     * The title of the portfolio item.
     */
    @Column(nullable = false, length = 255)
    private String title;
    
    /**
     * A description of the portfolio item.
     */
    @Column(columnDefinition = "TEXT")
    private String description;
    
    /**
     * The URL of the project associated with the portfolio item.
     */
    @Column(name = "project_url", length = 500)
    private String projectUrl;
    
    /**
     * The URL of the image associated with the portfolio item.
     */
    @Column(name = "image_url", length = 500)
    private String imageUrl;
    
    /**
     * A description of the technologies used in the portfolio item.
     */
    @Column(name = "technologies_used", columnDefinition = "TEXT")
    private String technologiesUsed;
    
    /**
     * The completion date of the portfolio item.
     */
    @Column(name = "completion_date")
    private LocalDate completionDate;
    
    /**
     * The timestamp when the portfolio item was created.
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    /**
     * Sets the creation timestamp before persisting the entity.
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}