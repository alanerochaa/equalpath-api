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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
@Tag(
        name = "Curso Recomendado",
        description = "Gestão de cursos sugeridos para apoiar as trilhas de carreira."
)
@SecurityRequirement(name = "bearerAuth")
public class CursoRecomendadoController {

    private final CursoRecomendadoService cursoService;

    @PostMapping
    @Operation(
            summary = "Criar curso recomendado",
            description = "Cadastra um novo curso associado a uma trilha de carreira."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Curso recomendado criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para criação do curso."),
            @ApiResponse(responseCode = "404", description = "Trilha associada não encontrada."),
            @ApiResponse(responseCode = "500", description = "Erro interno ao criar curso recomendado.")
    })
    public ResponseEntity<CursoRecomendadoResponseDTO> criar(
            @Valid @RequestBody CursoRecomendadoRequestDTO dto
    ) {
        CursoRecomendadoResponseDTO response = cursoService.criar(dto);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar curso recomendado por ID",
            description = "Retorna os dados de um curso recomendado específico."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Curso recomendado encontrado."),
            @ApiResponse(responseCode = "404", description = "Curso recomendado não encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno ao consultar curso recomendado.")
    })
    public ResponseEntity<CursoRecomendadoResponseDTO> buscarPorId(@PathVariable Long id) {
        CursoRecomendadoResponseDTO response = cursoService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(
            summary = "Listar cursos por trilha",
            description = "Lista os cursos recomendados vinculados a uma trilha específica, com paginação."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de cursos recuperada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Parâmetros de filtragem/paginação inválidos."),
            @ApiResponse(responseCode = "404", description = "Trilha informada não encontrada."),
            @ApiResponse(responseCode = "500", description = "Erro interno ao listar cursos recomendados.")
    })
    public ResponseEntity<Page<CursoRecomendadoResponseDTO>> listarPorTrilha(
            @RequestParam Long idTrilha,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable
    ) {
        Page<CursoRecomendadoResponseDTO> page = cursoService.listarPorTrilha(idTrilha, pageable);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar curso recomendado",
            description = "Atualiza metadados de um curso (nome, provedor, carga horária, etc.)."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Curso recomendado atualizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização."),
            @ApiResponse(responseCode = "404", description = "Curso recomendado não encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno ao atualizar curso recomendado.")
    })
    public ResponseEntity<CursoRecomendadoResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CursoRecomendadoRequestDTO dto
    ) {
        CursoRecomendadoResponseDTO response = cursoService.atualizar(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir curso recomendado",
            description = "Remove um curso recomendado associado à trilha."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Curso recomendado removido com sucesso."),
            @ApiResponse(responseCode = "404", description = "Curso recomendado não encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno ao excluir curso recomendado.")
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
