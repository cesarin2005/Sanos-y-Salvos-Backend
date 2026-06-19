package com.sanosysalvos.foundpet.controller;

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
import com.sanosysalvos.foundpet.model.FoundPet;
import com.sanosysalvos.foundpet.service.FoundPetService;

@ExtendWith(MockitoExtension.class)
@DisplayName("FoundPetController - Pruebas unitarias")
class FoundPetControllerTest {

    @Mock
    private FoundPetService foundPetService;

    @InjectMocks
    private FoundPetController foundPetController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private FoundPet mascota;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(foundPetController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

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
    @DisplayName("GET /api/found-pets debe retornar lista de mascotas")
    void debeRetornarTodasLasMascotas() throws Exception {
        when(foundPetService.getAll()).thenReturn(List.of(mascota));
        mockMvc.perform(get("/api/found-pets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].species").value("Perro"));
    }

    @Test
    @DisplayName("GET /api/found-pets/{id} debe retornar mascota por ID")
    void debeRetornarMascotaPorId() throws Exception {
        when(foundPetService.getById(1L)).thenReturn(mascota);
        mockMvc.perform(get("/api/found-pets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.species").value("Perro"));
    }

    @Test
    @DisplayName("GET /api/found-pets/active debe retornar mascotas activas")
    void debeRetornarMascotasActivas() throws Exception {
        when(foundPetService.getActive()).thenReturn(List.of(mascota));
        mockMvc.perform(get("/api/found-pets/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("ACTIVE"));
    }

    @Test
    @DisplayName("GET /api/found-pets/user/{userId} debe retornar mascotas por usuario")
    void debeRetornarMascotasPorUsuario() throws Exception {
        when(foundPetService.getByUserId(5L)).thenReturn(List.of(mascota));
        mockMvc.perform(get("/api/found-pets/user/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].species").value("Perro"));
    }

    @Test
    @DisplayName("POST /api/found-pets debe crear mascota")
    void debeCrearMascota() throws Exception {
        when(foundPetService.create(any(FoundPet.class))).thenReturn(mascota);
        mockMvc.perform(post("/api/found-pets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mascota)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.species").value("Perro"));
    }

    @Test
    @DisplayName("PATCH /api/found-pets/{id}/status debe actualizar status")
    void debeActualizarStatus() throws Exception {
        doNothing().when(foundPetService).updateStatus(1L, FoundPet.Status.MATCHED);
        mockMvc.perform(patch("/api/found-pets/1/status")
                .param("status", "MATCHED"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /api/found-pets/{id} debe eliminar mascota")
    void debeEliminarMascota() throws Exception {
        doNothing().when(foundPetService).delete(1L);
        mockMvc.perform(delete("/api/found-pets/1"))
                .andExpect(status().isNoContent());
    }
}