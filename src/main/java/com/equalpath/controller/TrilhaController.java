package com.equalpath.controller;

import com.equalpath.domain.enums.StatusTrilha;
import com.equalpath.dto.TrilhaRequestDTO;
import com.equalpath.dto.TrilhaResponseDTO;
import com.equalpath.service.TrilhaService;
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
            description = "Registra uma nova trilha de carreira, vinculada a skills e perfil de usuário."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Trilha criada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para criação.")
    })
    public ResponseEntity<TrilhaResponseDTO> criar(@Valid @RequestBody TrilhaRequestDTO dto) {
        return ResponseEntity.status(201).body(trilhaService.criar(dto));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar trilha por ID",
            description = "Consulta os dados de uma trilha específica."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Trilha encontrada."),
            @ApiResponse(responseCode = "404", description = "Trilha não encontrada.")
    })
    public ResponseEntity<TrilhaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(trilhaService.buscarPorId(id));
    }

    @GetMapping
    @Operation(
            summary = "Listar trilhas",
            description = "Retorna as trilhas cadastradas, com filtro opcional por status."
    )
    public ResponseEntity<List<TrilhaResponseDTO>> listar(
            @RequestParam(required = false) StatusTrilha status
    ) {
        return ResponseEntity.ok(trilhaService.listar(status));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar trilha",
            description = "Atualiza os metadados de uma trilha existente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Trilha atualizada."),
            @ApiResponse(responseCode = "404", description = "Trilha não encontrada.")
    })
    public ResponseEntity<TrilhaResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody TrilhaRequestDTO dto
    ) {
        return ResponseEntity.ok(trilhaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir trilha",
            description = "Remove uma trilha do catálogo."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Trilha excluída."),
            @ApiResponse(responseCode = "404", description = "Trilha não encontrada.")
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
