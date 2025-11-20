package com.equalpath.controller;

import com.equalpath.domain.enums.StatusPerfil;
import com.equalpath.dto.UsuarioRequestDTO;
import com.equalpath.dto.UsuarioResponseDTO;
import com.equalpath.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(
        name = "Usuário",
        description = "Gestão do cadastro de usuários da EqualPath."
)
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    @Operation(
            summary = "Criar usuário",
            description = "Realiza o onboarding de um novo usuário na EqualPath, gravando os dados básicos de perfil."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para criação do usuário."),
            @ApiResponse(responseCode = "409", description = "Conflito de dados (por exemplo, e-mail já cadastrado)."),
            @ApiResponse(responseCode = "500", description = "Erro interno ao criar usuário.")
    })
    public ResponseEntity<UsuarioResponseDTO> criar(@Valid @RequestBody UsuarioRequestDTO dto) {
        UsuarioResponseDTO response = usuarioService.criar(dto);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar usuário por ID",
            description = "Recupera os dados de um usuário específico a partir do identificador único."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno ao consultar usuário.")
    })
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        UsuarioResponseDTO response = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status")
    @Operation(
            summary = "Listar usuários por status do perfil",
            description = "Retorna todos os usuários filtrados pelo status do perfil (ATIVO, INATIVO ou PENDENTE). " +
                    "Caso o parâmetro não seja informado, retorna todos os usuários."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de usuários recuperada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Parâmetro de status inválido."),
            @ApiResponse(responseCode = "500", description = "Erro interno ao listar usuários.")
    })
    public ResponseEntity<List<UsuarioResponseDTO>> listarPorStatus(
            @RequestParam(required = false) StatusPerfil statusPerfil
    ) {
        List<UsuarioResponseDTO> response = usuarioService.listarPorStatus(statusPerfil);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar usuário",
            description = "Atualiza os dados cadastrais de um usuário já existente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno ao atualizar usuário.")
    })
    public ResponseEntity<UsuarioResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDTO dto
    ) {
        UsuarioResponseDTO response = usuarioService.atualizar(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir usuário",
            description = "Remove o usuário da plataforma e desvincula suas relações com trilhas, áreas e skills."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário removido com sucesso."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno ao excluir usuário.")
    })
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        usuarioService.excluir(id);

        return ResponseEntity.ok(
                Map.of(
                        "status", "sucesso",
                        "mensagem", "Usuário removido com sucesso.",
                        "idExcluido", id
                )
        );
    }
}
