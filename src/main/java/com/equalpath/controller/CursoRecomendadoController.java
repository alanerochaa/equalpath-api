package com.equalpath.controller;

import com.equalpath.dto.CursoRecomendadoRequestDTO;
import com.equalpath.dto.CursoRecomendadoResponseDTO;
import com.equalpath.service.CursoRecomendadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
@Tag(
        name = "Curso Recomendado",
        description = "Gestão de cursos sugeridos para apoiar trilhas de carreira."
)
@SecurityRequirement(name = "bearerAuth")
public class CursoRecomendadoController {

    private final CursoRecomendadoService cursoService;

    @PostMapping
    @Operation(
            summary = "Criar curso recomendado",
            description = "Registra um novo curso vinculado a uma trilha de carreira."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Curso criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos no payload."),
            @ApiResponse(responseCode = "404", description = "Trilha vinculada não encontrada."),
            @ApiResponse(responseCode = "500", description = "Erro interno ao criar curso.")
    })
    public ResponseEntity<CursoRecomendadoResponseDTO> criar(
            @Valid @RequestBody CursoRecomendadoRequestDTO dto
    ) {
        return ResponseEntity.status(201).body(cursoService.criar(dto));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar curso recomendado por ID",
            description = "Recupera os dados completos de um curso recomendado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Curso encontrado."),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno ao consultar curso.")
    })
    public ResponseEntity<CursoRecomendadoResponseDTO> buscarPorId(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(cursoService.buscarPorId(id));
    }

    @GetMapping
    @Operation(
            summary = "Listar cursos por trilha",
            description = "Retorna todos os cursos associados a uma trilha específica."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Trilha não encontrada."),
            @ApiResponse(responseCode = "500", description = "Erro interno ao listar cursos.")
    })
    public ResponseEntity<List<CursoRecomendadoResponseDTO>> listarPorTrilha(
            @RequestParam Long idTrilha
    ) {
        return ResponseEntity.ok(cursoService.listarPorTrilha(idTrilha));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar curso recomendado",
            description = "Edita os dados estruturais de um curso (nome, URL, plataforma e trilha)."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Curso atualizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Payload inválido para atualização."),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno ao atualizar curso.")
    })
    public ResponseEntity<CursoRecomendadoResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CursoRecomendadoRequestDTO dto
    ) {
        return ResponseEntity.ok(cursoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir curso recomendado",
            description = "Remove o curso recomendado, preservando a integridade das trilhas vinculadas."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Curso removido com sucesso."),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno ao excluir curso.")
    })
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        cursoService.excluir(id);

        return ResponseEntity.ok(
                Map.of(
                        "status", "sucesso",
                        "mensagem", "Curso recomendado removido com sucesso.",
                        "idExcluido", id
                )
        );
    }
}
