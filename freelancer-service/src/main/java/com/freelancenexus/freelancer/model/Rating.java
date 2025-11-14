package com.freelancenexus.freelancer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity representing a rating given to a freelancer.
 */
@Entity
@Table(name = "ratings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    
    /**
     * The unique identifier of the rating.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * The freelancer associated with this rating.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "freelancer_id", nullable = false)
    private Freelancer freelancer;
    
    /**
     * The ID of the client who provided the rating.
     */
    @Column(name = "client_id", nullable = false)
    private Long clientId;
    
    /**
     * The ID of the project associated with the rating.
     */
    @Column(name = "project_id")
    private Long projectId;
    
    /**
     * The rating value.
     */
    @Column(nullable = false)
    private Integer rating;
    
    /**
     * The review text provided by the client.
     */
    @Column(columnDefinition = "TEXT")
    private String review;
    
    /**
     * The timestamp when the rating was created.
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