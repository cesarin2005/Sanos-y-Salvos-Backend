package com.sanosysalvos.matching.service;

import com.sanosysalvos.matching.client.PetHttpClient;
import com.sanosysalvos.matching.dto.PetDtos;
import com.sanosysalvos.matching.model.Match;
import com.sanosysalvos.matching.repository.MatchRepository;
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
@DisplayName("MatchingService - Pruebas unitarias")
class MatchingServiceTest {

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private PetHttpClient petHttpClient;

    @InjectMocks
    private MatchingService matchingService;

    private Match match;
    private PetDtos.LostPetDto lostPet;
    private PetDtos.FoundPetDto foundPet;

    @BeforeEach
    void setUp() {
        match = new Match();
        match.setId(1L);
        match.setLostPetId(10L);
        match.setFoundPetId(20L);
        match.setConfidenceScore(0.9);
        match.setStatus(Match.Status.PENDING);

        lostPet = new PetDtos.LostPetDto();
        lostPet.setId(10L);
        lostPet.setSpecies("Perro");
        lostPet.setBreed("Labrador");
        lostPet.setColor("Amarillo");

        foundPet = new PetDtos.FoundPetDto();
        foundPet.setId(20L);
        foundPet.setSpecies("Perro");
        foundPet.setBreed("Labrador");
        foundPet.setColor("Amarillo");
    }

    @Test
    @DisplayName("Debe retornar todos los matches")
    void debeRetornarTodosLosMatches() {
        when(matchRepository.findAll()).thenReturn(List.of(match));

        List<Match> resultado = matchingService.getAll();

        assertEquals(1, resultado.size());
        verify(matchRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe retornar match por ID existente")
    void debeRetornarMatchPorId() {
        when(matchRepository.findById(1L)).thenReturn(Optional.of(match));

        Match resultado = matchingService.getById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    @DisplayName("Debe lanzar excepcion si match no existe")
    void debeLanzarExcepcionSiMatchNoExiste() {
        when(matchRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> matchingService.getById(99L));
    }

    @Test
    @DisplayName("Debe retornar matches por ID de mascota perdida")
    void debeRetornarMatchesPorLostPetId() {
        when(matchRepository.findByLostPetId(10L)).thenReturn(List.of(match));

        List<Match> resultado = matchingService.getByLostPetId(10L);

        assertEquals(1, resultado.size());
        assertEquals(10L, resultado.get(0).getLostPetId());
        verify(matchRepository, times(1)).findByLostPetId(10L);
    }

    @Test
    @DisplayName("Debe confirmar un match cambiando su status a CONFIRMED")
    void debeConfirmarMatch() {
        when(matchRepository.findById(1L)).thenReturn(Optional.of(match));
        when(matchRepository.save(any(Match.class))).thenReturn(match);

        matchingService.confirmMatch(1L);

        assertEquals(Match.Status.CONFIRMED, match.getStatus());
        verify(matchRepository, times(1)).save(match);
    }

    @Test
    @DisplayName("Debe rechazar un match cambiando su status a REJECTED")
    void debeRechazarMatch() {
        when(matchRepository.findById(1L)).thenReturn(Optional.of(match));
        when(matchRepository.save(any(Match.class))).thenReturn(match);

        matchingService.rejectMatch(1L);

        assertEquals(Match.Status.REJECTED, match.getStatus());
        verify(matchRepository, times(1)).save(match);
    }

    @Test
    @DisplayName("Debe ejecutar matching y crear match cuando score es mayor a 0.5")
    void debeEjecutarMatchingYCrearMatch() {
        when(petHttpClient.getActiveLostPets()).thenReturn(List.of(lostPet));
        when(petHttpClient.getActiveFoundPets()).thenReturn(List.of(foundPet));
        when(matchRepository.existsByLostPetIdAndFoundPetId(10L, 20L)).thenReturn(false);
        when(matchRepository.save(any(Match.class))).thenReturn(match);

        List<Match> resultado = matchingService.runMatching();

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        verify(matchRepository, times(1)).save(any(Match.class));
    }

    @Test
    @DisplayName("No debe crear match duplicado si ya existe")
    void noDebeCrearMatchDuplicado() {
        when(petHttpClient.getActiveLostPets()).thenReturn(List.of(lostPet));
        when(petHttpClient.getActiveFoundPets()).thenReturn(List.of(foundPet));
        when(matchRepository.existsByLostPetIdAndFoundPetId(10L, 20L)).thenReturn(true);

        List<Match> resultado = matchingService.runMatching();

        assertTrue(resultado.isEmpty());
        verify(matchRepository, never()).save(any(Match.class));
    }

    @Test
    @DisplayName("No debe crear match si el score es menor a 0.5")
    void noDebeCrearMatchConScoreBajo() {
        lostPet.setSpecies("Perro");
        lostPet.setBreed("Poodle");
        lostPet.setColor("Blanco");

        foundPet.setSpecies("Gato");
        foundPet.setBreed("Siames");
        foundPet.setColor("Negro");

        when(petHttpClient.getActiveLostPets()).thenReturn(List.of(lostPet));
        when(petHttpClient.getActiveFoundPets()).thenReturn(List.of(foundPet));

        List<Match> resultado = matchingService.runMatching();

        assertTrue(resultado.isEmpty());
        verify(matchRepository, never()).save(any(Match.class));
    }
}
