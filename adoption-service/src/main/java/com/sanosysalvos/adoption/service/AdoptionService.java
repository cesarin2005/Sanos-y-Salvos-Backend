package com.sanosysalvos.adoption.service;

import com.sanosysalvos.adoption.dto.AdoptionDtos.*;
import com.sanosysalvos.adoption.model.Adoption;
import com.sanosysalvos.adoption.model.Adoption.AdoptionStatus;
import com.sanosysalvos.adoption.repository.AdoptionRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdoptionService {

    private final AdoptionRepository adoptionRepository;

    public AdoptionService(AdoptionRepository adoptionRepository) {
        this.adoptionRepository = adoptionRepository;
    }

    public AdoptionResponse crearSolicitud(AdoptionRequest request) {
        Adoption adoption = new Adoption();
        adoption.setFoundPetId(request.getFoundPetId());
        adoption.setAdopterUserId(request.getAdopterUserId());
        adoption.setAdopterName(request.getAdopterName());
        adoption.setAdopterEmail(request.getAdopterEmail());
        adoption.setAdopterPhone(request.getAdopterPhone());
        adoption.setReason(request.getReason());
        adoption.setStatus(AdoptionStatus.PENDIENTE);
        return toResponse(adoptionRepository.save(adoption));
    }

    public List<AdoptionResponse> listarTodas() {
        return adoptionRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public AdoptionResponse obtenerPorId(Long id) {
        Adoption adoption = adoptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud de adopción no encontrada con id: " + id));
        return toResponse(adoption);
    }

    public List<AdoptionResponse> listarPorEstado(AdoptionStatus status) {
        return adoptionRepository.findByStatus(status).stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<AdoptionResponse> listarPorAdoptante(Long adopterUserId) {
        return adoptionRepository.findByAdopterUserId(adopterUserId).stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<AdoptionResponse> listarPorMascota(Long foundPetId) {
        return adoptionRepository.findByFoundPetId(foundPetId).stream().map(this::toResponse).collect(Collectors.toList());
    }

    public AdoptionResponse aprobarSolicitud(Long id, ReviewRequest request) {
        Adoption adoption = adoptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud de adopción no encontrada con id: " + id));
        adoption.setStatus(AdoptionStatus.APROBADA);
        adoption.setReviewerNotes(request.getReviewerNotes());
        return toResponse(adoptionRepository.save(adoption));
    }

    public AdoptionResponse rechazarSolicitud(Long id, ReviewRequest request) {
        Adoption adoption = adoptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud de adopción no encontrada con id: " + id));
        adoption.setStatus(AdoptionStatus.RECHAZADA);
        adoption.setReviewerNotes(request.getReviewerNotes());
        return toResponse(adoptionRepository.save(adoption));
    }

    public void eliminarSolicitud(Long id) {
        if (!adoptionRepository.existsById(id)) {
            throw new RuntimeException("Solicitud de adopción no encontrada con id: " + id);
        }
        adoptionRepository.deleteById(id);
    }

    private AdoptionResponse toResponse(Adoption adoption) {
        AdoptionResponse response = new AdoptionResponse();
        response.setId(adoption.getId());
        response.setFoundPetId(adoption.getFoundPetId());
        response.setAdopterUserId(adoption.getAdopterUserId());
        response.setAdopterName(adoption.getAdopterName());
        response.setAdopterEmail(adoption.getAdopterEmail());
        response.setAdopterPhone(adoption.getAdopterPhone());
        response.setReason(adoption.getReason());
        response.setStatus(adoption.getStatus());
        response.setCreatedAt(adoption.getCreatedAt());
        response.setUpdatedAt(adoption.getUpdatedAt());
        response.setReviewerNotes(adoption.getReviewerNotes());
        return response;
    }
}