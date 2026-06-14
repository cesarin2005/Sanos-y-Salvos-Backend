package com.sanosysalvos.foundpet.service;

import com.sanosysalvos.foundpet.model.FoundPet;
import com.sanosysalvos.foundpet.repository.FoundPetRepository;
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
@DisplayName("FoundPetService - Pruebas unitarias")
class FoundPetServiceTest {

    @Mock
    private FoundPetRepository foundPetRepository;

    @InjectMocks
    private FoundPetService foundPetService;

    private FoundPet mascota;

    @BeforeEach
    void setUp() {
        mascota = new FoundPet();
        mascota.setId(1L);
        mascota.setSpecies("Perro");
        mascota.setBreed("Labrador");
        mascota.setColor("Amarillo");
        mascota.setFoundLocation("Providencia");
        mascota.setFoundByUserId(5L);
        mascota.setFinderPhone("+56987654321");
        mascota.setStatus(FoundPet.Status.ACTIVE);
    }

    @Test
    @DisplayName("Debe retornar todas las mascotas encontradas")
    void debeRetornarTodasLasMascotas() {
        when(foundPetRepository.findAll()).thenReturn(List.of(mascota));
        List<FoundPet> resultado = foundPetService.getAll();
        assertEquals(1, resultado.size());
        verify(foundPetRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe retornar mascota encontrada por ID")
    void debeRetornarMascotaPorId() {
        when(foundPetRepository.findById(1L)).thenReturn(Optional.of(mascota));
        FoundPet resultado = foundPetService.getById(1L);
        assertNotNull(resultado);
        assertEquals("Perro", resultado.getSpecies());
    }

    @Test
    @DisplayName("Debe lanzar excepcion si mascota encontrada no existe")
    void debeLanzarExcepcionSiNoExiste() {
        when(foundPetRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> foundPetService.getById(99L));
    }

    @Test
    @DisplayName("Debe crear mascota encontrada con status ACTIVE")
    void debeCrearMascotaConStatusActive() {
        when(foundPetRepository.save(any(FoundPet.class))).thenReturn(mascota);
        FoundPet nueva = new FoundPet();
        nueva.setSpecies("Gato");
        foundPetService.create(nueva);
        assertEquals(FoundPet.Status.ACTIVE, nueva.getStatus());
        verify(foundPetRepository, times(1)).save(nueva);
    }

    @Test
    @DisplayName("Debe retornar mascotas encontradas activas")
    void debeRetornarMascotasActivas() {
        when(foundPetRepository.findByStatus(FoundPet.Status.ACTIVE)).thenReturn(List.of(mascota));
        List<FoundPet> resultado = foundPetService.getActive();
        assertEquals(1, resultado.size());
        assertEquals(FoundPet.Status.ACTIVE, resultado.get(0).getStatus());
    }

    @Test
    @DisplayName("Debe retornar mascotas encontradas por userId")
    void debeRetornarMascotasPorUsuario() {
        when(foundPetRepository.findByFoundByUserId(5L)).thenReturn(List.of(mascota));
        List<FoundPet> resultado = foundPetService.getByUserId(5L);
        assertEquals(1, resultado.size());
        assertEquals(5L, resultado.get(0).getFoundByUserId());
    }

    @Test
    @DisplayName("Debe actualizar el status de mascota encontrada")
    void debeActualizarStatus() {
        when(foundPetRepository.findById(1L)).thenReturn(Optional.of(mascota));
        when(foundPetRepository.save(any(FoundPet.class))).thenReturn(mascota);
        foundPetService.updateStatus(1L, FoundPet.Status.MATCHED);
        assertEquals(FoundPet.Status.MATCHED, mascota.getStatus());
        verify(foundPetRepository, times(1)).save(mascota);
    }

    @Test
    @DisplayName("Debe eliminar mascota encontrada por ID")
    void debeEliminarMascota() {
        doNothing().when(foundPetRepository).deleteById(1L);
        foundPetService.delete(1L);
        verify(foundPetRepository, times(1)).deleteById(1L);
    }
}
