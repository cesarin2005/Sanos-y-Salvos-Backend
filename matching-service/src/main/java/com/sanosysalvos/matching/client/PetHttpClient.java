package com.sanosysalvos.matching.client;

import com.sanosysalvos.matching.dto.PetDtos;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class PetHttpClient {

    private final RestTemplate restTemplate;

    @Value("${services.lost-pet-url}")
    private String lostPetUrl;

    @Value("${services.found-pet-url}")
    private String foundPetUrl;

    public PetHttpClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<PetDtos.LostPetDto> getActiveLostPets() {
        PetDtos.LostPetDto[] pets = restTemplate.getForObject(
                lostPetUrl + "/api/lost-pets/active",
                PetDtos.LostPetDto[].class);
        return pets != null ? Arrays.asList(pets) : List.of();
    }

    public List<PetDtos.FoundPetDto> getActiveFoundPets() {
        PetDtos.FoundPetDto[] pets = restTemplate.getForObject(
                foundPetUrl + "/api/found-pets/active",
                PetDtos.FoundPetDto[].class);
        return pets != null ? Arrays.asList(pets) : List.of();
    }
}
