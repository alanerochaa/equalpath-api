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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
            @ApiResponse(responseCode = "400", description = "Dados inválidos para criação da skill.")
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
            @ApiResponse(responseCode = "404", description = "Skill não encontrada.")
    })
    public ResponseEntity<SkillResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(skillService.buscarPorId(id));
    }

    @GetMapping
    @Operation(
            summary = "Listar skills",
            description = "Retorna todas as skills disponíveis no catálogo."
    )
    public ResponseEntity<List<SkillResponseDTO>> listar() {
        return ResponseEntity.ok(skillService.listar());
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar skill",
            description = "Atualiza nome, descrição ou classificação de uma skill existente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Skill atualizada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Skill não encontrada.")
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
            description = "Remove uma skill do catálogo."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Skill removida com sucesso."),
            @ApiResponse(responseCode = "404", description = "Skill não encontrada.")
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
