package com.sanosysalvos.adoption.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "adoptions")
public class Adoption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "found_pet_id", nullable = false)
    private Long foundPetId;

    @NotNull
    @Column(name = "adopter_user_id", nullable = false)
    private Long adopterUserId;

    @NotBlank
    @Column(name = "adopter_name", nullable = false)
    private String adopterName;

    @NotBlank
    @Column(name = "adopter_email", nullable = false)
    private String adopterEmail;

    @NotBlank
    @Column(name = "adopter_phone", nullable = false)
    private String adopterPhone;

    @Column(name = "reason", length = 1000)
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdoptionStatus status = AdoptionStatus.PENDIENTE;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "reviewer_notes", length = 500)
    private String reviewerNotes;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum AdoptionStatus {
        PENDIENTE, APROBADA, RECHAZADA
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getFoundPetId() { return foundPetId; }
    public void setFoundPetId(Long foundPetId) { this.foundPetId = foundPetId; }
    public Long getAdopterUserId() { return adopterUserId; }
    public void setAdopterUserId(Long adopterUserId) { this.adopterUserId = adopterUserId; }
    public String getAdopterName() { return adopterName; }
    public void setAdopterName(String adopterName) { this.adopterName = adopterName; }
    public String getAdopterEmail() { return adopterEmail; }
    public void setAdopterEmail(String adopterEmail) { this.adopterEmail = adopterEmail; }
    public String getAdopterPhone() { return adopterPhone; }
    public void setAdopterPhone(String adopterPhone) { this.adopterPhone = adopterPhone; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public AdoptionStatus getStatus() { return status; }
    public void setStatus(AdoptionStatus status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public String getReviewerNotes() { return reviewerNotes; }
    public void setReviewerNotes(String reviewerNotes) { this.reviewerNotes = reviewerNotes; }
}