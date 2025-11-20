package com.equalpath.controller;

import com.equalpath.dto.AuthRequestDTO;
import com.equalpath.dto.AuthResponseDTO;
import com.equalpath.security.JwtTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(
        name = "Autenticação",
        description = "Fluxo de autenticação JWT da EqualPath."
)
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    @PostMapping("/login")
    @Operation(
            summary = "Autenticar usuário",
            description = "Valida as credenciais informadas e emite um token JWT para consumo dos endpoints protegidos."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autenticação realizada com sucesso. Token JWT gerado."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (payload inconsistente)."),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas ou usuário não autorizado."),
            @ApiResponse(responseCode = "500", description = "Erro interno ao processar autenticação.")
    })
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtTokenService.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(new AuthResponseDTO(token));
    }
}
