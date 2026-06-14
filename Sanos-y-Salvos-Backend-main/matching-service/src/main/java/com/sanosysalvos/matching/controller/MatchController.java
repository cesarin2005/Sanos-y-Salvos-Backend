package com.sanosysalvos.matching.controller;

import com.sanosysalvos.matching.model.Match;
import com.sanosysalvos.matching.service.MatchingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private final MatchingService matchingService;

    public MatchController(MatchingService matchingService) {
        this.matchingService = matchingService;
    }

    @GetMapping
    public ResponseEntity<List<Match>> getAll() {
        return ResponseEntity.ok(matchingService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Match> getById(@PathVariable Long id) {
        return ResponseEntity.ok(matchingService.getById(id));
    }

    @GetMapping("/lost-pet/{lostPetId}")
    public ResponseEntity<List<Match>> getByLostPet(@PathVariable Long lostPetId) {
        return ResponseEntity.ok(matchingService.getByLostPetId(lostPetId));
    }

    @PostMapping("/run")
    public ResponseEntity<List<Match>> runMatching() {
        return ResponseEntity.ok(matchingService.runMatching());
    }

    @PatchMapping("/{id}/confirm")
    public ResponseEntity<Void> confirm(@PathVariable Long id) {
        matchingService.confirmMatch(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<Void> reject(@PathVariable Long id) {
        matchingService.rejectMatch(id);
        return ResponseEntity.ok().build();
    }
}
