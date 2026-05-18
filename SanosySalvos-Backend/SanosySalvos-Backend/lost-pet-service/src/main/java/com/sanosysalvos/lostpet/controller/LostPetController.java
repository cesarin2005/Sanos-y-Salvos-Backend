package com.sanosysalvos.lostpet.controller;

import com.sanosysalvos.lostpet.model.LostPet;
import com.sanosysalvos.lostpet.service.LostPetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/lost-pets")
public class LostPetController {

    private final LostPetService lostPetService;

    public LostPetController(LostPetService lostPetService) {
        this.lostPetService = lostPetService;
    }

    @GetMapping
    public ResponseEntity<List<LostPet>> getAll() {
        return ResponseEntity.ok(lostPetService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LostPet> getById(@PathVariable Long id) {
        return ResponseEntity.ok(lostPetService.getById(id));
    }

    @GetMapping("/active")
    public ResponseEntity<List<LostPet>> getActive() {
        return ResponseEntity.ok(lostPetService.getActive());
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<LostPet>> getByOwner(@PathVariable Long ownerId) {
        return ResponseEntity.ok(lostPetService.getByOwnerId(ownerId));
    }

    @PostMapping
    public ResponseEntity<LostPet> create(@RequestBody LostPet lostPet) {
        return ResponseEntity.ok(lostPetService.create(lostPet));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LostPet> update(@PathVariable Long id, @RequestBody LostPet lostPet) {
        return ResponseEntity.ok(lostPetService.update(id, lostPet));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id,
                                              @RequestParam LostPet.Status status) {
        lostPetService.updateStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        lostPetService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
