package com.sanosysalvos.adoption.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sanosysalvos.adoption.model.Adoption;
import com.sanosysalvos.adoption.model.Adoption.AdoptionStatus;

@Repository
public interface AdoptionRepository extends JpaRepository<Adoption, Long> {
    List<Adoption> findByStatus(AdoptionStatus status);
    List<Adoption> findByAdopterUserId(Long adopterUserId);
    List<Adoption> findByFoundPetId(Long foundPetId);
}