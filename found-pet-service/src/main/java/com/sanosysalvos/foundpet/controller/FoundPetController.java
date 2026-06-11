package com.sanosysalvos.foundpet.controller;

import com.sanosysalvos.foundpet.model.FoundPet;
import com.sanosysalvos.foundpet.service.FoundPetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/found-pets")
public class FoundPetController {

    private final FoundPetService foundPetService;

    public FoundPetController(FoundPetService foundPetService) {
        this.foundPetService = foundPetService;
    }

    @GetMapping
    public ResponseEntity<List<FoundPet>> getAll() {
        return ResponseEntity.ok(foundPetService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoundPet> getById(@PathVariable Long id) {
        return ResponseEntity.ok(foundPetService.getById(id));
    }

    @GetMapping("/active")
    public ResponseEntity<List<FoundPet>> getActive() {
        return ResponseEntity.ok(foundPetService.getActive());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FoundPet>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(foundPetService.getByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<FoundPet> create(@RequestBody FoundPet foundPet) {
        return ResponseEntity.ok(foundPetService.create(foundPet));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoundPet> update(@PathVariable Long id, @RequestBody FoundPet foundPet) {
        return ResponseEntity.ok(foundPetService.update(id, foundPet));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id,
                                              @RequestParam FoundPet.Status status) {
        foundPetService.updateStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        foundPetService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
