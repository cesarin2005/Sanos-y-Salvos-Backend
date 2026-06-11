package com.sanosysalvos.user.service;

import com.sanosysalvos.user.dto.AuthDtos;
import com.sanosysalvos.user.model.User;
import com.sanosysalvos.user.repository.UserRepository;
import com.sanosysalvos.user.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthService - Pruebas unitarias")
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    private AuthDtos.RegisterRequest registerRequest;
    private AuthDtos.LoginRequest loginRequest;
    private User usuarioGuardado;

    @BeforeEach
    void setUp() {
        // Datos de registro de prueba
        registerRequest = new AuthDtos.RegisterRequest();
        registerRequest.setName("Juan Pérez");
        registerRequest.setEmail("juan@ejemplo.com");
        registerRequest.setPassword("123456");
        registerRequest.setPhone("+56912345678");
        registerRequest.setCity("Santiago");

        // Datos de login de prueba
        loginRequest = new AuthDtos.LoginRequest();
        loginRequest.setEmail("juan@ejemplo.com");
        loginRequest.setPassword("123456");

        // Usuario simulado que retorna la BD
        usuarioGuardado = User.builder()
                .name("Juan Pérez")
                .email("juan@ejemplo.com")
                .password("$2a$10$hashedpassword")
                .phone("+56912345678")
                .city("Santiago")
                .role(User.Role.USER)
                .active(true)
                .build();
    }

    // ─────────────────────────────────────────────
    // REGISTER
    // ─────────────────────────────────────────────

    @Test
    @DisplayName("Registro exitoso debe retornar AuthResponse con token")
    void registroExitosoDebeRetornarToken() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("$2a$10$hashedpassword");
        when(userRepository.save(any(User.class))).thenReturn(usuarioGuardado);
        when(jwtService.generateToken(any(), anyString(), anyString()))
                .thenReturn("jwt-token-mock");

        AuthDtos.AuthResponse response = authService.register(registerRequest);

        assertNotNull(response, "La respuesta no debe ser nula");
        assertEquals("jwt-token-mock", response.getToken(), "Debe retornar el token generado");
        assertEquals("Juan Pérez", response.getName(), "Debe retornar el nombre del usuario");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Registro con email duplicado debe lanzar IllegalArgumentException")
    void registroConEmailDuplicadoDebeLanzarExcepcion() {
        when(userRepository.existsByEmail("juan@ejemplo.com")).thenReturn(true);

        assertThrows(IllegalArgumentException.class,
                () -> authService.register(registerRequest),
                "Debe lanzar excepción si el email ya existe");

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Registro debe encodear la contraseña antes de guardar")
    void registroDebeEncodearPassword() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode("123456")).thenReturn("$2a$10$hashedpassword");
        when(userRepository.save(any(User.class))).thenReturn(usuarioGuardado);
        when(jwtService.generateToken(any(), anyString(), anyString()))
                .thenReturn("jwt-token-mock");

        authService.register(registerRequest);

        verify(passwordEncoder, times(1)).encode("123456");
    }

    // ─────────────────────────────────────────────
    // LOGIN
    // ─────────────────────────────────────────────

    @Test
    @DisplayName("Login exitoso debe retornar AuthResponse con token")
    void loginExitosoDebeRetornarToken() {
        when(userRepository.findByEmail("juan@ejemplo.com"))
                .thenReturn(Optional.of(usuarioGuardado));
        when(passwordEncoder.matches("123456", "$2a$10$hashedpassword"))
                .thenReturn(true);
        when(jwtService.generateToken(any(), anyString(), anyString()))
                .thenReturn("jwt-token-mock");

        AuthDtos.AuthResponse response = authService.login(loginRequest);

        assertNotNull(response, "La respuesta no debe ser nula");
        assertEquals("jwt-token-mock", response.getToken(), "Debe retornar el token generado");
        assertEquals("USER", response.getRole(), "Debe retornar el rol correcto");
    }

    @Test
    @DisplayName("Login con email inexistente debe lanzar BadCredentialsException")
    void loginConEmailInexistenteDebeLanzarExcepcion() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(BadCredentialsException.class,
                () -> authService.login(loginRequest),
                "Debe lanzar excepción si el email no existe");
    }

    @Test
    @DisplayName("Login con contraseña incorrecta debe lanzar BadCredentialsException")
    void loginConPasswordIncorrectoDebeLanzarExcepcion() {
        when(userRepository.findByEmail("juan@ejemplo.com"))
                .thenReturn(Optional.of(usuarioGuardado));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertThrows(BadCredentialsException.class,
                () -> authService.login(loginRequest),
                "Debe lanzar excepción si la contraseña no coincide");
    }

    @Test
    @DisplayName("Login con cuenta desactivada debe lanzar IllegalStateException")
    void loginConCuentaDesactivadaDebeLanzarExcepcion() {
        usuarioGuardado.setActive(false);
        when(userRepository.findByEmail("juan@ejemplo.com"))
                .thenReturn(Optional.of(usuarioGuardado));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        assertThrows(IllegalStateException.class,
                () -> authService.login(loginRequest),
                "Debe lanzar excepción si la cuenta está desactivada");
    }
}
