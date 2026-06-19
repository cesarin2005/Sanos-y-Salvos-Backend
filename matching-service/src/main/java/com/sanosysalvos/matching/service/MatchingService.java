package com.sanosysalvos.matching.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sanosysalvos.matching.client.PetHttpClient;
import com.sanosysalvos.matching.dto.PetDtos;
import com.sanosysalvos.matching.model.Match;
import com.sanosysalvos.matching.repository.MatchRepository;

@Service
public class MatchingService {

    private final MatchRepository matchRepository;
    private final PetHttpClient petHttpClient;

    public MatchingService(MatchRepository matchRepository, PetHttpClient petHttpClient) {
        this.matchRepository = matchRepository;
        this.petHttpClient = petHttpClient;
    }

    public List<Match> getAll() {
        return matchRepository.findAll();
    }

    public Match getById(Long id) {
        return matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Match no encontrado con id: " + id));
    }

    public List<Match> getByLostPetId(Long lostPetId) {
        return matchRepository.findByLostPetId(lostPetId);
    }

    public List<Match> runMatching() {
        List<PetDtos.LostPetDto> lostPets = petHttpClient.getActiveLostPets();
        List<PetDtos.FoundPetDto> foundPets = petHttpClient.getActiveFoundPets();
        List<Match> newMatches = new ArrayList<>();

        for (PetDtos.LostPetDto lost : lostPets) {
            for (PetDtos.FoundPetDto found : foundPets) {
                double score = calculateScore(lost, found);
                if (score >= 0.5 && !matchRepository.existsByLostPetIdAndFoundPetId(lost.getId(), found.getId())) {
                    Match match = new Match();
                    match.setLostPetId(lost.getId());
                    match.setFoundPetId(found.getId());
                    match.setConfidenceScore(score);
                    match.setStatus(Match.Status.PENDING);
                    newMatches.add(matchRepository.save(match));

                    // Notificar al dueño de la mascota perdida
                    String ownerPhone = lost.getOwnerPhone() != null ? lost.getOwnerPhone() : "no disponible";
                    String finderPhone = found.getFinderPhone() != null ? found.getFinderPhone() : "no disponible";
                    petHttpClient.notifyMatch(ownerPhone, finderPhone);
                }
            }
        }
        return newMatches;
    }

    public void confirmMatch(Long id) {
        Match match = getById(id);
        match.setStatus(Match.Status.CONFIRMED);
        matchRepository.save(match);
    }

    public void rejectMatch(Long id) {
        Match match = getById(id);
        match.setStatus(Match.Status.REJECTED);
        matchRepository.save(match);
    }

    private double calculateScore(PetDtos.LostPetDto lost, PetDtos.FoundPetDto found) {
        double score = 0.0;
        if (lost.getSpecies() != null && lost.getSpecies().equalsIgnoreCase(found.getSpecies())) score += 0.4;
        if (lost.getBreed() != null && lost.getBreed().equalsIgnoreCase(found.getBreed())) score += 0.3;
        if (lost.getColor() != null && lost.getColor().equalsIgnoreCase(found.getColor())) score += 0.3;
        return score;
    }
}