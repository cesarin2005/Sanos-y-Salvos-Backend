package com.sanosysalvos.user.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JwtService - Pruebas unitarias")
class JwtServiceTest {

    private JwtService jwtService;

    private static final String SECRET =
            "test-secret-key-for-unit-testing-must-be-256bits-long-enough";
    private static final long EXPIRATION = 86400000L; // 24 horas

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secret", SECRET);
        ReflectionTestUtils.setField(jwtService, "expiration", EXPIRATION);
    }

    @Test
    @DisplayName("Debe generar un token JWT no nulo para un usuario válido")
    void debeGenerarTokenNoNulo() {
        String token = jwtService.generateToken(1L, "juan@ejemplo.com", "USER");
        assertNotNull(token, "El token no debe ser nulo");
        assertFalse(token.isEmpty(), "El token no debe estar vacío");
    }

    @Test
    @DisplayName("El token generado debe tener formato JWT (3 partes separadas por punto)")
    void tokenDebeEstarEnFormatoJWT() {
        String token = jwtService.generateToken(1L, "juan@ejemplo.com", "USER");
        String[] partes = token.split("\\.");
        assertEquals(3, partes.length, "El token JWT debe tener 3 partes separadas por '.'");
    }

    @Test
    @DisplayName("El token generado debe ser válido")
    void tokenGeneradoDebeSerValido() {
        String token = jwtService.generateToken(1L, "juan@ejemplo.com", "USER");
        assertTrue(jwtService.isTokenValid(token), "El token recién generado debe ser válido");
    }

    @Test
    @DisplayName("Debe extraer correctamente el subject (userId) del token")
    void debeExtraerSubjectCorrectamente() {
        Long userId = 42L;
        String token = jwtService.generateToken(userId, "juan@ejemplo.com", "USER");
        String subject = jwtService.extractClaims(token).getSubject();
        assertEquals(String.valueOf(userId), subject,
                "El subject del token debe coincidir con el userId");
    }

    @Test
    @DisplayName("Debe extraer correctamente el email del token")
    void debeExtraerEmailCorrectamente() {
        String email = "juan@ejemplo.com";
        String token = jwtService.generateToken(1L, email, "USER");
        String emailExtraido = jwtService.extractClaims(token).get("email", String.class);
        assertEquals(email, emailExtraido, "El email extraído debe coincidir con el original");
    }

    @Test
    @DisplayName("Debe extraer correctamente el rol del token")
    void debeExtraerRolCorrectamente() {
        String rol = "ADMIN";
        String token = jwtService.generateToken(1L, "admin@ejemplo.com", rol);
        String rolExtraido = jwtService.extractClaims(token).get("role", String.class);
        assertEquals(rol, rolExtraido, "El rol extraído debe coincidir con el original");
    }

    @Test
    @DisplayName("Un token inválido o manipulado debe retornar false en isTokenValid")
    void tokenInvalidoDebeRetornarFalse() {
        String tokenFalso = "este.no.esuntoken";
        assertFalse(jwtService.isTokenValid(tokenFalso),
                "Un token manipulado no debe ser válido");
    }

    @Test
    @DisplayName("Tokens generados para distintos usuarios deben ser diferentes")
    void tokensDiferentesParaDistintosUsuarios() {
        String token1 = jwtService.generateToken(1L, "usuario1@ejemplo.com", "USER");
        String token2 = jwtService.generateToken(2L, "usuario2@ejemplo.com", "USER");
        assertNotEquals(token1, token2, "Tokens de distintos usuarios deben ser diferentes");
    }
}
