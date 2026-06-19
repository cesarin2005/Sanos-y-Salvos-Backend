package com.sanosysalvos.adoption.controller;

import com.sanosysalvos.adoption.dto.AdoptionDtos.*;
import com.sanosysalvos.adoption.model.Adoption.AdoptionStatus;
import com.sanosysalvos.adoption.service.AdoptionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/adoptions")
public class AdoptionController {

    private final AdoptionService adoptionService;

    public AdoptionController(AdoptionService adoptionService) {
        this.adoptionService = adoptionService;
    }

    @PostMapping
    public ResponseEntity<AdoptionResponse> crearSolicitud(@Valid @RequestBody AdoptionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adoptionService.crearSolicitud(request));
    }

    @GetMapping
    public ResponseEntity<List<AdoptionResponse>> listarTodas() {
        return ResponseEntity.ok(adoptionService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdoptionResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(adoptionService.obtenerPorId(id));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<AdoptionResponse>> listarPorEstado(@PathVariable AdoptionStatus status) {
        return ResponseEntity.ok(adoptionService.listarPorEstado(status));
    }

    @GetMapping("/adopter/{adopterUserId}")
    public ResponseEntity<List<AdoptionResponse>> listarPorAdoptante(@PathVariable Long adopterUserId) {
        return ResponseEntity.ok(adoptionService.listarPorAdoptante(adopterUserId));
    }

    @GetMapping("/pet/{foundPetId}")
    public ResponseEntity<List<AdoptionResponse>> listarPorMascota(@PathVariable Long foundPetId) {
        return ResponseEntity.ok(adoptionService.listarPorMascota(foundPetId));
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<AdoptionResponse> aprobar(@PathVariable Long id, @RequestBody ReviewRequest request) {
        return ResponseEntity.ok(adoptionService.aprobarSolicitud(id, request));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<AdoptionResponse> rechazar(@PathVariable Long id, @RequestBody ReviewRequest request) {
        return ResponseEntity.ok(adoptionService.rechazarSolicitud(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        adoptionService.eliminarSolicitud(id);
        return ResponseEntity.noContent().build();
    }
}
