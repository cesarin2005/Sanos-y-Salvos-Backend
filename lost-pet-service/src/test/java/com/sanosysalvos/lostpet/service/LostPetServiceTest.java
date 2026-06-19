package com.sanosysalvos.lostpet.service;

import com.sanosysalvos.lostpet.model.LostPet;
import com.sanosysalvos.lostpet.repository.LostPetRepository;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("LostPetService - Pruebas unitarias")
class LostPetServiceTest {

    @Mock
    private LostPetRepository lostPetRepository;

    @InjectMocks
    private LostPetService lostPetService;

    private LostPet mascota;

    @BeforeEach
    void setUp() {
        mascota = new LostPet();
        mascota.setId(1L);
        mascota.setName("Firulais");
        mascota.setSpecies("Perro");
        mascota.setBreed("Labrador");
        mascota.setColor("Amarillo");
        mascota.setLastSeenLocation("Santiago Centro");
        mascota.setOwnerId(10L);
        mascota.setOwnerPhone("+56912345678");
        mascota.setStatus(LostPet.Status.ACTIVE);
    }

    @Test
    @DisplayName("Debe retornar todas las mascotas perdidas")
    void debeRetornarTodasLasMascotas() {
        when(lostPetRepository.findAll()).thenReturn(List.of(mascota));
        List<LostPet> resultado = lostPetService.getAll();
        assertEquals(1, resultado.size());
        verify(lostPetRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe retornar mascota por ID existente")
    void debeRetornarMascotaPorId() {
        when(lostPetRepository.findById(1L)).thenReturn(Optional.of(mascota));
        LostPet resultado = lostPetService.getById(1L);
        assertNotNull(resultado);
        assertEquals("Firulais", resultado.getName());
    }

    @Test
    @DisplayName("Debe lanzar excepcion si mascota no existe")
    void debeLanzarExcepcionSiMascotaNoExiste() {
        when(lostPetRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> lostPetService.getById(99L));
    }

    @Test
    @DisplayName("Debe crear mascota con status ACTIVE")
    void debeCrearMascotaConStatusActive() {
        when(lostPetRepository.save(any(LostPet.class))).thenReturn(mascota);
        LostPet nueva = new LostPet();
        nueva.setName("Firulais");
        LostPet resultado = lostPetService.create(nueva);
        assertEquals(LostPet.Status.ACTIVE, nueva.getStatus());
        verify(lostPetRepository, times(1)).save(nueva);
    }

    @Test
    @DisplayName("Debe retornar mascotas activas")
    void debeRetornarMascotasActivas() {
        when(lostPetRepository.findByStatus(LostPet.Status.ACTIVE)).thenReturn(List.of(mascota));
        List<LostPet> resultado = lostPetService.getActive();
        assertEquals(1, resultado.size());
        assertEquals(LostPet.Status.ACTIVE, resultado.get(0).getStatus());
    }

    @Test
    @DisplayName("Debe retornar mascotas por ownerId")
    void debeRetornarMascotasPorOwner() {
        when(lostPetRepository.findByOwnerId(10L)).thenReturn(List.of(mascota));
        List<LostPet> resultado = lostPetService.getByOwnerId(10L);
        assertEquals(1, resultado.size());
        assertEquals(10L, resultado.get(0).getOwnerId());
    }

    @Test
    @DisplayName("Debe actualizar el status de una mascota")
    void debeActualizarStatus() {
        when(lostPetRepository.findById(1L)).thenReturn(Optional.of(mascota));
        when(lostPetRepository.save(any(LostPet.class))).thenReturn(mascota);
        lostPetService.updateStatus(1L, LostPet.Status.FOUND);
        assertEquals(LostPet.Status.FOUND, mascota.getStatus());
        verify(lostPetRepository, times(1)).save(mascota);
    }

    @Test
    @DisplayName("Debe eliminar mascota por ID")
    void debeEliminarMascota() {
        doNothing().when(lostPetRepository).deleteById(1L);
        lostPetService.delete(1L);
        verify(lostPetRepository, times(1)).deleteById(1L);
    }
}
