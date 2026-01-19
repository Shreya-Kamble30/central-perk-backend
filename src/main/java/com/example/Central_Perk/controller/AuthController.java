package com.example.Central_Perk.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Central_Perk.config.JwtUtil;
import com.example.Central_Perk.dto.LoginRequest;
import com.example.Central_Perk.entity.User;
import com.example.Central_Perk.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository repo;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
            );

            User user = repo.findByUsername(req.getUsername());
            if(user == null) return ResponseEntity.status(404).body("User not found");

            String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "role", user.getRole(),
                    "username", user.getUsername()
            ));

        } catch (Exception ex) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

    @GetMapping("/user")
public List<User> getAllUsers() {
    return repo.findAll();
}

@DeleteMapping("/users/{id}")
public ResponseEntity<String> deleteUser(@PathVariable Long id) {
    User user = repo.findById(id).orElse(null);
    if(user == null) return ResponseEntity.status(404).body("User not found");

    repo.deleteById(id);
    return ResponseEntity.ok("User deleted successfully");
}

        @GetMapping("/profile")
    public String profile() {
        return "This is a protected profile";
    }

}

