package com.sanosysalvos.matching.repository;

import com.sanosysalvos.matching.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByLostPetId(Long lostPetId);
    List<Match> findByFoundPetId(Long foundPetId);
    List<Match> findByStatus(Match.Status status);
    boolean existsByLostPetIdAndFoundPetId(Long lostPetId, Long foundPetId);
}
