package com.equalpath.controller;

import com.equalpath.security.JwtTokenService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtTokenService tokenService;

    public record AuthRequest(@NotBlank String username, @NotBlank String password) {}
    public record AuthResponse(String token) {}

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.username(), request.password());
        authManager.authenticate(authentication);

        String token = tokenService.generateToken(request.username());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
