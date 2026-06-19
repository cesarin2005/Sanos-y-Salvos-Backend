package com.sanosysalvos.lostpet.service;

import com.sanosysalvos.lostpet.model.LostPet;
import com.sanosysalvos.lostpet.repository.LostPetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LostPetService {

    private final LostPetRepository lostPetRepository;

    public LostPetService(LostPetRepository lostPetRepository) {
        this.lostPetRepository = lostPetRepository;
    }

    public List<LostPet> getAll() {
        return lostPetRepository.findAll();
    }

    public LostPet getById(Long id) {
        return lostPetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota perdida no encontrada con id: " + id));
    }

    public List<LostPet> getByOwnerId(Long ownerId) {
        return lostPetRepository.findByOwnerId(ownerId);
    }

    public List<LostPet> getActive() {
        return lostPetRepository.findByStatus(LostPet.Status.ACTIVE);
    }

    public LostPet create(LostPet lostPet) {
        lostPet.setStatus(LostPet.Status.ACTIVE);
        return lostPetRepository.save(lostPet);
    }

    public LostPet update(Long id, LostPet updated) {
        LostPet existing = getById(id);
        existing.setName(updated.getName());
        existing.setSpecies(updated.getSpecies());
        existing.setBreed(updated.getBreed());
        existing.setColor(updated.getColor());
        existing.setDescription(updated.getDescription());
        existing.setLastSeenLocation(updated.getLastSeenLocation());
        existing.setOwnerPhone(updated.getOwnerPhone());
        existing.setImageUrl(updated.getImageUrl());
        return lostPetRepository.save(existing);
    }

    public void updateStatus(Long id, LostPet.Status status) {
        LostPet pet = getById(id);
        pet.setStatus(status);
        lostPetRepository.save(pet);
    }

    public void delete(Long id) {
        lostPetRepository.deleteById(id);
    }
}
