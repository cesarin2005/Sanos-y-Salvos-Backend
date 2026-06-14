package com.sanosysalvos.foundpet.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "found_pets")
public class FoundPet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String species;

    private String breed;
    private String color;
    private String description;

    @Column(nullable = false)
    private String foundLocation;

    @Column(nullable = false)
    private Long foundByUserId;

    private String finderPhone;
    private String imageUrl;
    private Double latitude;
    private Double longitude;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum Status { ACTIVE, MATCHED, CLOSED }

    public FoundPet() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }
    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getFoundLocation() { return foundLocation; }
    public void setFoundLocation(String foundLocation) { this.foundLocation = foundLocation; }
    public Long getFoundByUserId() { return foundByUserId; }
    public void setFoundByUserId(Long foundByUserId) { this.foundByUserId = foundByUserId; }
    public String getFinderPhone() { return finderPhone; }
    public void setFinderPhone(String finderPhone) { this.finderPhone = finderPhone; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}