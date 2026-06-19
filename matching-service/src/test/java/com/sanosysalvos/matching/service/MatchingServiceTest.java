package com.sanosysalvos.matching.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sanosysalvos.matching.client.PetHttpClient;
import com.sanosysalvos.matching.dto.PetDtos;
import com.sanosysalvos.matching.model.Match;
import com.sanosysalvos.matching.repository.MatchRepository;

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
        match.setConfidenceScore(0.7);
        match.setStatus(Match.Status.PENDING);

        lostPet = new PetDtos.LostPetDto();
        lostPet.setId(10L);
        lostPet.setSpecies("Perro");
        lostPet.setBreed("Labrador");
        lostPet.setColor("Amarillo");
        lostPet.setOwnerPhone("+56912345678");

        foundPet = new PetDtos.FoundPetDto();
        foundPet.setId(20L);
        foundPet.setSpecies("Perro");
        foundPet.setBreed("Labrador");
        foundPet.setColor("Amarillo");
        foundPet.setFinderPhone("+56987654321");
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
    @DisplayName("Debe retornar match por ID")
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
    @DisplayName("Debe retornar matches por lostPetId")
    void debeRetornarMatchesPorLostPetId() {
        when(matchRepository.findByLostPetId(10L)).thenReturn(List.of(match));
        List<Match> resultado = matchingService.getByLostPetId(10L);
        assertEquals(1, resultado.size());
        assertEquals(10L, resultado.get(0).getLostPetId());
    }

    @Test
    @DisplayName("Debe crear match cuando hay coincidencia de especie, raza y color")
    void debeCrearMatchCuandoHayCoincidencia() {
        when(petHttpClient.getActiveLostPets()).thenReturn(List.of(lostPet));
        when(petHttpClient.getActiveFoundPets()).thenReturn(List.of(foundPet));
        when(matchRepository.existsByLostPetIdAndFoundPetId(10L, 20L)).thenReturn(false);
        when(matchRepository.save(any(Match.class))).thenReturn(match);
        doNothing().when(petHttpClient).notifyMatch(anyString(), anyString());

        List<Match> resultado = matchingService.runMatching();
        assertEquals(1, resultado.size());
        verify(matchRepository, times(1)).save(any(Match.class));
    }

    @Test
    @DisplayName("No debe crear match duplicado")
    void noDebeCrearMatchDuplicado() {
        when(petHttpClient.getActiveLostPets()).thenReturn(List.of(lostPet));
        when(petHttpClient.getActiveFoundPets()).thenReturn(List.of(foundPet));
        when(matchRepository.existsByLostPetIdAndFoundPetId(10L, 20L)).thenReturn(true);

        List<Match> resultado = matchingService.runMatching();
        assertEquals(0, resultado.size());
        verify(matchRepository, never()).save(any(Match.class));
    }

    @Test
    @DisplayName("No debe crear match si ninguna caracteristica coincide")
    void noDebeCrearMatchSiNadaCoincide() {
        foundPet.setSpecies("Gato");
        foundPet.setBreed("Siamés");
        foundPet.setColor("Negro");
        when(petHttpClient.getActiveLostPets()).thenReturn(List.of(lostPet));
        when(petHttpClient.getActiveFoundPets()).thenReturn(List.of(foundPet));

        List<Match> resultado = matchingService.runMatching();
        assertEquals(0, resultado.size());
    }

    @Test
    @DisplayName("Debe confirmar match")
    void debeConfirmarMatch() {
        when(matchRepository.findById(1L)).thenReturn(Optional.of(match));
        when(matchRepository.save(any(Match.class))).thenReturn(match);
        matchingService.confirmMatch(1L);
        assertEquals(Match.Status.CONFIRMED, match.getStatus());
    }

    @Test
    @DisplayName("Debe rechazar match")
    void debeRechazarMatch() {
        when(matchRepository.findById(1L)).thenReturn(Optional.of(match));
        when(matchRepository.save(any(Match.class))).thenReturn(match);
        matchingService.rejectMatch(1L);
        assertEquals(Match.Status.REJECTED, match.getStatus());
    }
}