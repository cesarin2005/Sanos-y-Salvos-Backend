package com.sanosysalvos.user.controller;

import com.sanosysalvos.user.dto.AuthDtos.LoginRequest;
import com.sanosysalvos.user.dto.AuthDtos.AuthResponse;
import com.sanosysalvos.user.dto.AuthDtos.RegisterRequest;
import com.sanosysalvos.user.security.JwtService;
import com.sanosysalvos.user.model.User;
import com.sanosysalvos.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(java.util.Map.of("message", "El correo ya está registrado."));
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setCity(request.getCity());
        user.setRole(User.Role.USER);

        // Guardamos primero para que la base de datos le asigne un ID
        User savedUser = userRepository.save(user);
        
        // Corregido: Pasamos los 3 parámetros que espera tu JwtService (id, email, rol en texto)
        String token = jwtService.generateToken(savedUser.getId(), savedUser.getEmail(), savedUser.getRole().name());

        return ResponseEntity.ok(java.util.Map.of(
            "token", token,
            "name", savedUser.getName()
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isEmpty() || !passwordEncoder.matches(request.getPassword(), userOpt.get().getPassword())) {
            return ResponseEntity.status(401).body(java.util.Map.of("message", "Credenciales inválidas."));
        }

        User user = userOpt.get();
        
        // Corregido: Pasamos los 3 parámetros aquí también
        String token = jwtService.generateToken(user.getId(), user.getEmail(), user.getRole().name());

        return ResponseEntity.ok(java.util.Map.of(
            "token", token,
            "name", user.getName()
        ));
    }
}