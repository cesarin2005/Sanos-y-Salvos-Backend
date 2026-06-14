package com.sanosysalvos.user.service;

import com.sanosysalvos.user.dto.AuthDtos;
import com.sanosysalvos.user.model.User;
import com.sanosysalvos.user.repository.UserRepository;
import com.sanosysalvos.user.security.JwtService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthDtos.AuthResponse register(AuthDtos.RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        User user = User.builder()
                .name(req.getName()).email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .phone(req.getPhone()).address(req.getAddress())
                .city(req.getCity()).role(User.Role.USER).build();
        user = userRepository.save(user);
        String token = jwtService.generateToken(user.getId(), user.getEmail(), user.getRole().name());
        return AuthDtos.AuthResponse.builder()
                .token(token).userId(user.getId())
                .name(user.getName()).email(user.getEmail())
                .role(user.getRole().name()).build();
    }

    public AuthDtos.AuthResponse login(AuthDtos.LoginRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Credenciales inválidas"));
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Credenciales inválidas");
        }
        if (!user.isActive()) throw new IllegalStateException("Cuenta desactivada");
        String token = jwtService.generateToken(user.getId(), user.getEmail(), user.getRole().name());
        return AuthDtos.AuthResponse.builder()
                .token(token).userId(user.getId())
                .name(user.getName()).email(user.getEmail())
                .role(user.getRole().name()).build();
    }
}