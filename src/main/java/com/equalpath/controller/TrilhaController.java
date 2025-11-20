package com.equalpath.controller;

import com.equalpath.dto.TrilhaRequestDTO;
import com.equalpath.dto.TrilhaResponseDTO;
import com.equalpath.service.TrilhaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/trilhas")
@RequiredArgsConstructor
@Tag(
        name = "Trilha",
        description = "Gestão das trilhas de carreira disponíveis na EqualPath."
)
@SecurityRequirement(name = "bearerAuth")
public class TrilhaController {

    private final TrilhaService trilhaService;

    @PostMapping
    @Operation(
            summary = "Criar trilha",
            description = "Registra uma nova trilha de carreira, vinculada a skills e perfil de usuário-alvo."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Trilha criada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para criação da trilha."),
            @ApiResponse(responseCode = "500", description = "Erro interno ao criar trilha.")
    })
    public ResponseEntity<TrilhaResponseDTO> criar(@Valid @RequestBody TrilhaRequestDTO dto) {
        TrilhaResponseDTO response = trilhaService.criar(dto);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar trilha por ID",
            description = "Consulta os dados de uma trilha específica, incluindo metadados principais."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Trilha encontrada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Trilha não encontrada."),
            @ApiResponse(responseCode = "500", description = "Erro interno ao consultar trilha.")
    })
    public ResponseEntity<TrilhaResponseDTO> buscarPorId(@PathVariable Long id) {
        TrilhaResponseDTO response = trilhaService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(
            summary = "Listar trilhas",
            description = "Retorna o catálogo de trilhas, com suporte a paginação e ordenação."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de trilhas recuperada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Parâmetros de paginação/ordenação inválidos."),
            @ApiResponse(responseCode = "500", description = "Erro interno ao listar trilhas.")
    })
    public ResponseEntity<Page<TrilhaResponseDTO>> listar(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable
    ) {
        Page<TrilhaResponseDTO> page = trilhaService.listar(pageable);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar trilha",
            description = "Ajusta as informações de uma trilha existente (nome, descrição, nível, etc.)."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Trilha atualizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização."),
            @ApiResponse(responseCode = "404", description = "Trilha não encontrada."),
            @ApiResponse(responseCode = "500", description = "Erro interno ao atualizar trilha.")
    })
    public ResponseEntity<TrilhaResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody TrilhaRequestDTO dto
    ) {
        TrilhaResponseDTO response = trilhaService.atualizar(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir trilha",
            description = "Remove uma trilha do catálogo, conforme regra de negócio."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Trilha removida com sucesso."),
            @ApiResponse(responseCode = "404", description = "Trilha não encontrada."),
            @ApiResponse(responseCode = "500", description = "Erro interno ao excluir trilha.")
    })
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        trilhaService.excluir(id);
        return ResponseEntity.ok(
                Map.of(
                        "status", "sucesso",
                        "mensagem", "Trilha removida com sucesso.",
                        "idExcluido", id
                )
        );
    }
}
