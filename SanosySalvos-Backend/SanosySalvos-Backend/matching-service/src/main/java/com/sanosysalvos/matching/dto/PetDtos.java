package com.sanosysalvos.matching.dto;

public class PetDtos {

    public static class LostPetDto {
        private Long id;
        private String name;
        private String species;
        private String breed;
        private String color;
        private String lastSeenLocation;
        private Long ownerId;
        private String ownerPhone;
        private String status;

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
        public String getLastSeenLocation() { return lastSeenLocation; }
        public void setLastSeenLocation(String v) { this.lastSeenLocation = v; }
        public Long getOwnerId() { return ownerId; }
        public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }
        public String getOwnerPhone() { return ownerPhone; }
        public void setOwnerPhone(String ownerPhone) { this.ownerPhone = ownerPhone; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    public static class FoundPetDto {
        private Long id;
        private String species;
        private String breed;
        private String color;
        private String foundLocation;
        private Long foundByUserId;
        private String finderPhone;
        private String status;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getSpecies() { return species; }
        public void setSpecies(String species) { this.species = species; }
        public String getBreed() { return breed; }
        public void setBreed(String breed) { this.breed = breed; }
        public String getColor() { return color; }
        public void setColor(String color) { this.color = color; }
        public String getFoundLocation() { return foundLocation; }
        public void setFoundLocation(String v) { this.foundLocation = v; }
        public Long getFoundByUserId() { return foundByUserId; }
        public void setFoundByUserId(Long v) { this.foundByUserId = v; }
        public String getFinderPhone() { return finderPhone; }
        public void setFinderPhone(String finderPhone) { this.finderPhone = finderPhone; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }
}
