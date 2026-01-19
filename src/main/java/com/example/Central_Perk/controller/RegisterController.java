package com.example.Central_Perk.controller;

import com.example.Central_Perk.dto.RegisterRequest;
import com.example.Central_Perk.entity.User;
import com.example.Central_Perk.repository.UserRepository;
import com.example.Central_Perk.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RegisterController {

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
public ResponseEntity<?> register(@RequestBody RegisterRequest req) {

    // Basic validation
    if (req.getUsername() == null || req.getPassword() == null || req.getConfirmPassword() == null) {
        return ResponseEntity.badRequest().body("All fields are required");
    }

    // password confirm check
    if (!req.getPassword().equals(req.getConfirmPassword())) {
        return ResponseEntity.badRequest().body("Passwords do not match");
    }

    // check existing
    if (repo.findByUsername(req.getUsername()) != null) {
        return ResponseEntity.status(409).body("Username already exists");
    }

    // validate role
    String role = (req.getRole() != null &&
            (req.getRole().equalsIgnoreCase("USER") || req.getRole().equalsIgnoreCase("ADMIN")))
            ? req.getRole().toUpperCase()
            : "USER";

    User user = new User();
    user.setUsername(req.getUsername());
    user.setPassword(passwordEncoder.encode(req.getPassword()));
    user.setRole(role);

    User saved = repo.save(user);

    // generate token (optional)
    String token = jwtUtil.generateToken(saved.getUsername(), saved.getRole());

    return ResponseEntity.ok(Map.of(
            "message", "User registered",
            "username", saved.getUsername(),
            "role", saved.getRole(),
            "token", token
    ));
}

}