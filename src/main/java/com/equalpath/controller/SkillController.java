package com.equalpath.controller;

import com.equalpath.dto.SkillRequestDTO;
import com.equalpath.dto.SkillResponseDTO;
import com.equalpath.service.SkillService;
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
@RequestMapping("/api/skills")
@RequiredArgsConstructor
@Tag(
        name = "Skill",
        description = "Catálogo de skills técnicas e comportamentais utilizadas nas recomendações."
)
@SecurityRequirement(name = "bearerAuth")
public class SkillController {

    private final SkillService skillService;

    @PostMapping
    @Operation(
            summary = "Criar skill",
            description = "Inclui uma nova skill no catálogo global de competências."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Skill criada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para criação da skill."),
            @ApiResponse(responseCode = "500", description = "Erro interno ao criar skill.")
    })
    public ResponseEntity<SkillResponseDTO> criar(@Valid @RequestBody SkillRequestDTO dto) {
        SkillResponseDTO response = skillService.criar(dto);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar skill por ID",
            description = "Retorna os detalhes de uma skill específica."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Skill localizada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Skill não encontrada."),
            @ApiResponse(responseCode = "500", description = "Erro interno ao consultar skill.")
    })
    public ResponseEntity<SkillResponseDTO> buscarPorId(@PathVariable Long id) {
        SkillResponseDTO response = skillService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(
            summary = "Listar skills",
            description = "Lista as skills cadastradas, com paginação e ordenação configuráveis."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de skills recuperada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Parâmetros de paginação/ordenação inválidos."),
            @ApiResponse(responseCode = "500", description = "Erro interno ao listar skills.")
    })
    public ResponseEntity<Page<SkillResponseDTO>> listar(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable
    ) {
        Page<SkillResponseDTO> page = skillService.listar(pageable);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar skill",
            description = "Atualiza nome, descrição ou classificação de uma skill existente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Skill atualizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização."),
            @ApiResponse(responseCode = "404", description = "Skill não encontrada."),
            @ApiResponse(responseCode = "500", description = "Erro interno ao atualizar skill.")
    })
    public ResponseEntity<SkillResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody SkillRequestDTO dto
    ) {
        SkillResponseDTO response = skillService.atualizar(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir skill",
            description = "Remove uma skill do catálogo, garantindo integridade com trilhas que a utilizam."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Skill removida com sucesso."),
            @ApiResponse(responseCode = "404", description = "Skill não encontrada."),
            @ApiResponse(responseCode = "500", description = "Erro interno ao excluir skill.")
    })
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        skillService.excluir(id);
        return ResponseEntity.ok(
                Map.of(
                        "status", "sucesso",
                        "mensagem", "Skill removida com sucesso.",
                        "idExcluido", id
                )
        );
    }
}
