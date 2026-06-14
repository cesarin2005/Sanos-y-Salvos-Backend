package com.sanosysalvos.adoption.dto;

import java.time.LocalDateTime;

import com.sanosysalvos.adoption.model.Adoption.AdoptionStatus;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AdoptionDtos {

    public static class AdoptionRequest {
        @NotNull
        private Long foundPetId;
        @NotNull
        private Long adopterUserId;
        @NotBlank
        private String adopterName;
        @NotBlank
        @Email
        private String adopterEmail;
        @NotBlank
        private String adopterPhone;
        private String reason;

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
    }

    public static class ReviewRequest {
        private String reviewerNotes;
        public String getReviewerNotes() { return reviewerNotes; }
        public void setReviewerNotes(String reviewerNotes) { this.reviewerNotes = reviewerNotes; }
    }

    public static class AdoptionResponse {
        private Long id;
        private Long foundPetId;
        private Long adopterUserId;
        private String adopterName;
        private String adopterEmail;
        private String adopterPhone;
        private String reason;
        private AdoptionStatus status;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String reviewerNotes;

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
}