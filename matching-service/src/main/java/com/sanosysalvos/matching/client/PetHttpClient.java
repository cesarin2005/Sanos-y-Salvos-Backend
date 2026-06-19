package com.sanosysalvos.matching.client;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.sanosysalvos.matching.dto.PetDtos;

@Component
public class PetHttpClient {

    private final RestTemplate restTemplate;

    @Value("${services.lost-pet-url}")
    private String lostPetUrl;

    @Value("${services.found-pet-url}")
    private String foundPetUrl;

    @Value("${services.notification-url}")
    private String notificationUrl;

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

    public void notifyMatch(String ownerPhone, String finderPhone) {
        try {
            String url = notificationUrl + "/api/notifications/match?ownerEmail=" +
                    ownerPhone + "&finderPhone=" + finderPhone;
            restTemplate.postForObject(url, null, String.class);
        } catch (Exception e) {
            System.out.println("No se pudo enviar notificación: " + e.getMessage());
        }
    }
}