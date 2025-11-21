package com.equalpath.controller;

import com.equalpath.dto.MensagemResponseDTO;
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
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    public ResponseEntity<EntityModel<SkillResponseDTO>> criar(
            @Valid @RequestBody SkillRequestDTO dto
    ) {
        SkillResponseDTO response = skillService.criar(dto);

        EntityModel<SkillResponseDTO> resource = EntityModel.of(
                response,
                linkTo(methodOn(SkillController.class).buscarPorId(response.id())).withSelfRel(),
                linkTo(methodOn(SkillController.class).listar()).withRel("skills")
        );

        URI location = linkTo(methodOn(SkillController.class)
                .buscarPorId(response.id())).toUri();

        return ResponseEntity.created(location).body(resource);
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
    public ResponseEntity<EntityModel<SkillResponseDTO>> buscarPorId(@PathVariable Long id) {
        SkillResponseDTO response = skillService.buscarPorId(id);

        EntityModel<SkillResponseDTO> resource = EntityModel.of(
                response,
                linkTo(methodOn(SkillController.class).buscarPorId(id)).withSelfRel(),
                linkTo(methodOn(SkillController.class).listar()).withRel("skills")
        );

        return ResponseEntity.ok(resource);
    }

    @GetMapping
    @Operation(
            summary = "Listar skills",
            description = "Retorna todas as skills disponíveis no catálogo."
    )
    public ResponseEntity<CollectionModel<EntityModel<SkillResponseDTO>>> listar() {
        List<SkillResponseDTO> skills = skillService.listar();

        List<EntityModel<SkillResponseDTO>> content = skills.stream()
                .map(s -> EntityModel.of(
                        s,
                        linkTo(methodOn(SkillController.class).buscarPorId(s.id())).withSelfRel()
                ))
                .toList();

        CollectionModel<EntityModel<SkillResponseDTO>> collectionModel =
                CollectionModel.of(
                        content,
                        linkTo(methodOn(SkillController.class).listar()).withSelfRel()
                );

        return ResponseEntity.ok(collectionModel);
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
    public ResponseEntity<EntityModel<SkillResponseDTO>> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody SkillRequestDTO dto
    ) {
        SkillResponseDTO response = skillService.atualizar(id, dto);

        EntityModel<SkillResponseDTO> resource = EntityModel.of(
                response,
                linkTo(methodOn(SkillController.class).buscarPorId(id)).withSelfRel(),
                linkTo(methodOn(SkillController.class).listar()).withRel("skills")
        );

        return ResponseEntity.ok(resource);
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
    public ResponseEntity<EntityModel<MensagemResponseDTO>> excluir(@PathVariable Long id) {
        skillService.excluir(id);

        MensagemResponseDTO mensagem =
                new MensagemResponseDTO("Skill removida com sucesso.");

        EntityModel<MensagemResponseDTO> resource = EntityModel.of(
                mensagem,
                linkTo(methodOn(SkillController.class).listar()).withRel("skills")
        );

        return ResponseEntity.ok(resource);
    }
}
