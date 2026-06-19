package com.sanosysalvos.matching.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long lostPetId;

    @Column(nullable = false)
    private Long foundPetId;

    private Double confidenceScore;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum Status { PENDING, CONFIRMED, REJECTED }

    public Match() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getLostPetId() { return lostPetId; }
    public void setLostPetId(Long lostPetId) { this.lostPetId = lostPetId; }
    public Long getFoundPetId() { return foundPetId; }
    public void setFoundPetId(Long foundPetId) { this.foundPetId = foundPetId; }
    public Double getConfidenceScore() { return confidenceScore; }
    public void setConfidenceScore(Double confidenceScore) { this.confidenceScore = confidenceScore; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
