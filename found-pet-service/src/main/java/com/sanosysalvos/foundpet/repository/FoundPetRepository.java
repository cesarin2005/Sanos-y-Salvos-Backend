package com.sanosysalvos.foundpet.repository;

import com.sanosysalvos.foundpet.model.FoundPet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoundPetRepository extends JpaRepository<FoundPet, Long> {
    List<FoundPet> findByFoundByUserId(Long userId);
    List<FoundPet> findByStatus(FoundPet.Status status);
    List<FoundPet> findBySpeciesAndStatus(String species, FoundPet.Status status);
}
