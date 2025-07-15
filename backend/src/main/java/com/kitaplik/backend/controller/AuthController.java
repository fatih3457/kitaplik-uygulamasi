package com.kitaplik.backend.controller;

import com.kitaplik.backend.dto.AuthRequest;
import com.kitaplik.backend.dto.AuthResponse; // Bu import önemli
import com.kitaplik.backend.dto.UserResponse;
import com.kitaplik.backend.model.User;
import com.kitaplik.backend.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException; // Bu import önemli
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody AuthRequest request) {
        try {
            User registeredUser = authService.register(request.getUsername(), request.getPassword());
            UserResponse response = new UserResponse(registeredUser.getId(), registeredUser.getUsername());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        try {
            String token = authService.login(request.getUsername(), request.getPassword());
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (AuthenticationException e) { // Kırmızı ise bu import eksiktir
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}