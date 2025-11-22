package com.equalpath.controller;

import com.equalpath.dto.MensagemResponseDTO;
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
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    public ResponseEntity<EntityModel<TrilhaResponseDTO>> criar(
            @Valid @RequestBody TrilhaRequestDTO dto
    ) {
        TrilhaResponseDTO response = trilhaService.criar(dto);

        EntityModel<TrilhaResponseDTO> resource = EntityModel.of(
                response,
                linkTo(methodOn(TrilhaController.class).buscarPorId(response.id())).withSelfRel(),
                linkTo(methodOn(TrilhaController.class).listar(null)).withRel("trilhas")
        );

        URI location = linkTo(methodOn(TrilhaController.class)
                .buscarPorId(response.id())).toUri();

        return ResponseEntity.created(location).body(resource);
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
    public ResponseEntity<EntityModel<TrilhaResponseDTO>> buscarPorId(@PathVariable Long id) {
        TrilhaResponseDTO response = trilhaService.buscarPorId(id);

        EntityModel<TrilhaResponseDTO> resource = EntityModel.of(
                response,
                linkTo(methodOn(TrilhaController.class).buscarPorId(id)).withSelfRel(),
                linkTo(methodOn(TrilhaController.class).listar(null)).withRel("trilhas")
        );

        return ResponseEntity.ok(resource);
    }

    @GetMapping
    @Operation(
            summary = "Listar trilhas",
            description = "Retorna as trilhas cadastradas, com filtro opcional por status."
    )
    public ResponseEntity<CollectionModel<EntityModel<TrilhaResponseDTO>>> listar(
            @RequestParam(required = false) String status
    ) {
        List<TrilhaResponseDTO> trilhas = trilhaService.listar(status);

        List<EntityModel<TrilhaResponseDTO>> content = trilhas.stream()
                .map(t -> EntityModel.of(
                        t,
                        linkTo(methodOn(TrilhaController.class).buscarPorId(t.id())).withSelfRel()
                ))
                .toList();

        CollectionModel<EntityModel<TrilhaResponseDTO>> collectionModel =
                CollectionModel.of(
                        content,
                        linkTo(methodOn(TrilhaController.class).listar(status)).withSelfRel()
                );

        return ResponseEntity.ok(collectionModel);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar trilha",
            description = "Atualiza os metadados de uma trilha existente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Trilha atualizado."),
            @ApiResponse(responseCode = "404", description = "Trilha não encontrada.")
    })
    public ResponseEntity<EntityModel<TrilhaResponseDTO>> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody TrilhaRequestDTO dto
    ) {
        TrilhaResponseDTO response = trilhaService.atualizar(id, dto);

        EntityModel<TrilhaResponseDTO> resource = EntityModel.of(
                response,
                linkTo(methodOn(TrilhaController.class).buscarPorId(id)).withSelfRel(),
                linkTo(methodOn(TrilhaController.class).listar(null)).withRel("trilhas")
        );

        return ResponseEntity.ok(resource);
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
    public ResponseEntity<EntityModel<MensagemResponseDTO>> excluir(@PathVariable Long id) {
        trilhaService.excluir(id);

        MensagemResponseDTO mensagem =
                new MensagemResponseDTO("Trilha removida com sucesso.");

        EntityModel<MensagemResponseDTO> resource = EntityModel.of(
                mensagem,
                linkTo(methodOn(TrilhaController.class).listar(null)).withRel("trilhas")
        );

        return ResponseEntity.ok(resource);
    }
}
