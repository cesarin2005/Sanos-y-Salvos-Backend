package com.sanosysalvos.foundpet.service;

import com.sanosysalvos.foundpet.model.FoundPet;
import com.sanosysalvos.foundpet.repository.FoundPetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoundPetService {

    private final FoundPetRepository foundPetRepository;

    public FoundPetService(FoundPetRepository foundPetRepository) {
        this.foundPetRepository = foundPetRepository;
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
        return foundPetRepository.save(foundPet);
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
