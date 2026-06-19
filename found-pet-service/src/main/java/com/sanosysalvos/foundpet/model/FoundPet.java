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

    private String name;

    @Column(nullable = false)
    private String species;

    private String breed;
    private String color;
    private String gender;
    private String description;

    @Column(nullable = false)
    private String foundLocation;

    @Column(nullable = false)
    private Long foundByUserId;

    private String finderPhone;
    private String imageUrl;

    @Column(columnDefinition = "LONGTEXT")
    private String photoBase64;

    private Double latitude;
    private Double longitude;

    private String foundDate;
    private String foundTime;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum Status { ACTIVE, MATCHED, CLOSED }

    public FoundPet() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }
    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
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
    public String getPhotoBase64() { return photoBase64; }
    public void setPhotoBase64(String photoBase64) { this.photoBase64 = photoBase64; }
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public String getFoundDate() { return foundDate; }
    public void setFoundDate(String foundDate) { this.foundDate = foundDate; }
    public String getFoundTime() { return foundTime; }
    public void setFoundTime(String foundTime) { this.foundTime = foundTime; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}