package com.sanosysalvos.adoption.service;

import com.sanosysalvos.adoption.dto.AdoptionDtos.AdoptionRequest;
import com.sanosysalvos.adoption.dto.AdoptionDtos.AdoptionResponse;
import com.sanosysalvos.adoption.dto.AdoptionDtos.ReviewRequest;
import com.sanosysalvos.adoption.model.Adoption;
import com.sanosysalvos.adoption.model.Adoption.AdoptionStatus;
import com.sanosysalvos.adoption.repository.AdoptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AdoptionService - Pruebas unitarias")
class AdoptionServiceTest {

    @Mock
    private AdoptionRepository adoptionRepository;

    @InjectMocks
    private AdoptionService adoptionService;

    private Adoption adoption;
    private AdoptionRequest request;
    private ReviewRequest reviewRequest;

    @BeforeEach
    void setUp() {
        adoption = new Adoption();
        adoption.setId(1L);
        adoption.setFoundPetId(10L);
        adoption.setAdopterUserId(5L);
        adoption.setAdopterName("Juan Pérez");
        adoption.setAdopterEmail("juan@email.com");
        adoption.setAdopterPhone("+56912345678");
        adoption.setReason("Quiero darle un hogar");
        adoption.setStatus(AdoptionStatus.PENDIENTE);

        request = new AdoptionRequest();
        request.setFoundPetId(10L);
        request.setAdopterUserId(5L);
        request.setAdopterName("Juan Pérez");
        request.setAdopterEmail("juan@email.com");
        request.setAdopterPhone("+56912345678");
        request.setReason("Quiero darle un hogar");

        reviewRequest = new ReviewRequest();
        reviewRequest.setReviewerNotes("Aprobado por el equipo");
    }

    @Test
    @DisplayName("Debe crear solicitud de adopción con status PENDIENTE")
    void debeCrearSolicitudDeAdopcion() {
        when(adoptionRepository.save(any(Adoption.class))).thenReturn(adoption);

        AdoptionResponse resultado = adoptionService.crearSolicitud(request);

        assertNotNull(resultado);
        assertEquals(AdoptionStatus.PENDIENTE, resultado.getStatus());
        verify(adoptionRepository, times(1)).save(any(Adoption.class));
    }

    @Test
    @DisplayName("Debe retornar todas las solicitudes de adopción")
    void debeListarTodasLasSolicitudes() {
        when(adoptionRepository.findAll()).thenReturn(List.of(adoption));

        List<AdoptionResponse> resultado = adoptionService.listarTodas();

        assertEquals(1, resultado.size());
        verify(adoptionRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe retornar solicitud de adopción por ID")
    void debeObtenerSolicitudPorId() {
        when(adoptionRepository.findById(1L)).thenReturn(Optional.of(adoption));

        AdoptionResponse resultado = adoptionService.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    @DisplayName("Debe lanzar excepción si solicitud no existe")
    void debeLanzarExcepcionSiSolicitudNoExiste() {
        when(adoptionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> adoptionService.obtenerPorId(99L));
    }

    @Test
    @DisplayName("Debe retornar solicitudes por estado")
    void debeListarPorEstado() {
        when(adoptionRepository.findByStatus(AdoptionStatus.PENDIENTE)).thenReturn(List.of(adoption));

        List<AdoptionResponse> resultado = adoptionService.listarPorEstado(AdoptionStatus.PENDIENTE);

        assertEquals(1, resultado.size());
        assertEquals(AdoptionStatus.PENDIENTE, resultado.get(0).getStatus());
    }

    @Test
    @DisplayName("Debe retornar solicitudes por adoptante")
    void debeListarPorAdoptante() {
        when(adoptionRepository.findByAdopterUserId(5L)).thenReturn(List.of(adoption));

        List<AdoptionResponse> resultado = adoptionService.listarPorAdoptante(5L);

        assertEquals(1, resultado.size());
        assertEquals(5L, resultado.get(0).getAdopterUserId());
    }

    @Test
    @DisplayName("Debe retornar solicitudes por mascota")
    void debeListarPorMascota() {
        when(adoptionRepository.findByFoundPetId(10L)).thenReturn(List.of(adoption));

        List<AdoptionResponse> resultado = adoptionService.listarPorMascota(10L);

        assertEquals(1, resultado.size());
        assertEquals(10L, resultado.get(0).getFoundPetId());
    }

    @Test
    @DisplayName("Debe aprobar solicitud de adopción cambiando status a APROBADA")
    void debeAprobarSolicitud() {
        when(adoptionRepository.findById(1L)).thenReturn(Optional.of(adoption));
        when(adoptionRepository.save(any(Adoption.class))).thenReturn(adoption);

        AdoptionResponse resultado = adoptionService.aprobarSolicitud(1L, reviewRequest);

        assertNotNull(resultado);
        assertEquals(AdoptionStatus.APROBADA, adoption.getStatus());
        verify(adoptionRepository, times(1)).save(adoption);
    }

    @Test
    @DisplayName("Debe rechazar solicitud de adopción cambiando status a RECHAZADA")
    void debeRechazarSolicitud() {
        when(adoptionRepository.findById(1L)).thenReturn(Optional.of(adoption));
        when(adoptionRepository.save(any(Adoption.class))).thenReturn(adoption);

        AdoptionResponse resultado = adoptionService.rechazarSolicitud(1L, reviewRequest);

        assertNotNull(resultado);
        assertEquals(AdoptionStatus.RECHAZADA, adoption.getStatus());
        verify(adoptionRepository, times(1)).save(adoption);
    }

    @Test
    @DisplayName("Debe eliminar solicitud de adopción existente")
    void debeEliminarSolicitud() {
        when(adoptionRepository.existsById(1L)).thenReturn(true);
        doNothing().when(adoptionRepository).deleteById(1L);

        adoptionService.eliminarSolicitud(1L);

        verify(adoptionRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción al eliminar solicitud inexistente")
    void debeLanzarExcepcionAlEliminarSolicitudInexistente() {
        when(adoptionRepository.existsById(99L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> adoptionService.eliminarSolicitud(99L));
        verify(adoptionRepository, never()).deleteById(99L);
    }
}
