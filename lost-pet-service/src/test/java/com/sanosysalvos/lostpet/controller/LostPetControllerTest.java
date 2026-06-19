package com.sanosysalvos.lostpet.controller;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sanosysalvos.lostpet.model.LostPet;
import com.sanosysalvos.lostpet.service.LostPetService;

@ExtendWith(MockitoExtension.class)
@DisplayName("LostPetController - Pruebas unitarias")
class LostPetControllerTest {

    @Mock
    private LostPetService lostPetService;

    @InjectMocks
    private LostPetController lostPetController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private LostPet mascota;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(lostPetController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mascota = new LostPet();
        mascota.setId(1L);
        mascota.setName("Firulais");
        mascota.setSpecies("Perro");
        mascota.setBreed("Labrador");
        mascota.setColor("Amarillo");
        mascota.setLastSeenLocation("Santiago");
        mascota.setOwnerId(10L);
        mascota.setOwnerPhone("+56912345678");
        mascota.setStatus(LostPet.Status.ACTIVE);
    }

    @Test
    @DisplayName("GET /api/lost-pets debe retornar lista de mascotas")
    void debeRetornarTodasLasMascotas() throws Exception {
        when(lostPetService.getAll()).thenReturn(List.of(mascota));
        mockMvc.perform(get("/api/lost-pets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Firulais"));
    }

    @Test
    @DisplayName("GET /api/lost-pets/{id} debe retornar mascota por ID")
    void debeRetornarMascotaPorId() throws Exception {
        when(lostPetService.getById(1L)).thenReturn(mascota);
        mockMvc.perform(get("/api/lost-pets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Firulais"));
    }

    @Test
    @DisplayName("GET /api/lost-pets/active debe retornar mascotas activas")
    void debeRetornarMascotasActivas() throws Exception {
        when(lostPetService.getActive()).thenReturn(List.of(mascota));
        mockMvc.perform(get("/api/lost-pets/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("ACTIVE"));
    }

    @Test
    @DisplayName("GET /api/lost-pets/owner/{ownerId} debe retornar mascotas por dueño")
    void debeRetornarMascotasPorOwner() throws Exception {
        when(lostPetService.getByOwnerId(10L)).thenReturn(List.of(mascota));
        mockMvc.perform(get("/api/lost-pets/owner/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Firulais"));
    }

    @Test
    @DisplayName("POST /api/lost-pets debe crear mascota")
    void debeCrearMascota() throws Exception {
        when(lostPetService.create(any(LostPet.class))).thenReturn(mascota);
        mockMvc.perform(post("/api/lost-pets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mascota)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Firulais"));
    }

    @Test
    @DisplayName("PATCH /api/lost-pets/{id}/status debe actualizar status")
    void debeActualizarStatus() throws Exception {
        doNothing().when(lostPetService).updateStatus(1L, LostPet.Status.FOUND);
        mockMvc.perform(patch("/api/lost-pets/1/status")
                .param("status", "FOUND"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /api/lost-pets/{id} debe eliminar mascota")
    void debeEliminarMascota() throws Exception {
        doNothing().when(lostPetService).delete(1L);
        mockMvc.perform(delete("/api/lost-pets/1"))
                .andExpect(status().isNoContent());
    }
}