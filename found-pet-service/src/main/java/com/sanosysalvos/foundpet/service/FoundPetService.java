package com.sanosysalvos.foundpet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sanosysalvos.foundpet.model.FoundPet;
import com.sanosysalvos.foundpet.repository.FoundPetRepository;

@Service
public class FoundPetService {

    private final FoundPetRepository foundPetRepository;
    private final RestTemplate restTemplate;

    @Value("${services.matching-url}")
    private String matchingUrl;

    public FoundPetService(FoundPetRepository foundPetRepository, RestTemplate restTemplate) {
        this.foundPetRepository = foundPetRepository;
        this.restTemplate = restTemplate;
    }

    public List<FoundPet> getAll() {
        return foundPetRepository.findAll();
    }

    public FoundPet getById(Long id) {
        return foundPetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota encontrada no encontrada con id: " + id));
    }

    public List<FoundPet> getByUserId(Long userId) {
        return foundPetRepository.findByFoundByUserId(userId);
    }

    public List<FoundPet> getActive() {
        return foundPetRepository.findByStatus(FoundPet.Status.ACTIVE);
    }

    public FoundPet create(FoundPet foundPet) {
        foundPet.setStatus(FoundPet.Status.ACTIVE);
        FoundPet saved = foundPetRepository.save(foundPet);
        System.out.println(">>> [FoundPetService] Mascota guardada con id: " + saved.getId());
        System.out.println(">>> [FoundPetService] Llamando matching-service en: " + matchingUrl);
        try {
            restTemplate.postForObject(matchingUrl + "/api/matches/run", null, Object.class);
            System.out.println(">>> [FoundPetService] Matching ejecutado correctamente");
        } catch (Exception e) {
            System.out.println(">>> [FoundPetService] Error llamando matching: " + e.getMessage());
        }
        return saved;
    }

    public FoundPet update(Long id, FoundPet updated) {
        FoundPet existing = getById(id);
        existing.setSpecies(updated.getSpecies());
        existing.setBreed(updated.getBreed());
        existing.setColor(updated.getColor());
        existing.setDescription(updated.getDescription());
        existing.setFoundLocation(updated.getFoundLocation());
        existing.setFinderPhone(updated.getFinderPhone());
        existing.setImageUrl(updated.getImageUrl());
        return foundPetRepository.save(existing);
    }

    public void updateStatus(Long id, FoundPet.Status status) {
        FoundPet pet = getById(id);
        pet.setStatus(status);
        foundPetRepository.save(pet);
    }

    public void delete(Long id) {
        foundPetRepository.deleteById(id);
    }
}