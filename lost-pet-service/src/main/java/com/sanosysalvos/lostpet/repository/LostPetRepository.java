package com.sanosysalvos.lostpet.repository;

import com.sanosysalvos.lostpet.model.LostPet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LostPetRepository extends JpaRepository<LostPet, Long> {
    List<LostPet> findByOwnerId(Long ownerId);
    List<LostPet> findByStatus(LostPet.Status status);
    List<LostPet> findBySpeciesAndStatus(String species, LostPet.Status status);
}
