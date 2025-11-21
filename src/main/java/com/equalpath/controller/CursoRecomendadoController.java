package com.equalpath.controller;

import com.equalpath.domain.enums.PlataformaCurso;
import com.equalpath.dto.CursoRecomendadoRequestDTO;
import com.equalpath.dto.CursoRecomendadoResponseDTO;
import com.equalpath.dto.MensagemResponseDTO;
import com.equalpath.service.CursoRecomendadoService;
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
            description = "Cadastra um novo curso associado a uma trilha."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Curso criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Payload inválido."),
            @ApiResponse(responseCode = "404", description = "Trilha vinculada não encontrada."),
            @ApiResponse(responseCode = "500", description = "Erro interno ao criar curso.")
    })
    public ResponseEntity<EntityModel<CursoRecomendadoResponseDTO>> criar(
            @Valid @RequestBody CursoRecomendadoRequestDTO dto
    ) {
        CursoRecomendadoResponseDTO response = cursoService.criar(dto);

        EntityModel<CursoRecomendadoResponseDTO> resource = EntityModel.of(
                response,
                linkTo(methodOn(CursoRecomendadoController.class)
                        .buscarPorId(response.id())).withSelfRel(),
                linkTo(methodOn(CursoRecomendadoController.class)
                        .listarPorTrilha(response.idTrilha(), null)).withRel("cursos_da_trilha")
        );

        URI location = linkTo(methodOn(CursoRecomendadoController.class)
                .buscarPorId(response.id())).toUri();

        return ResponseEntity.created(location).body(resource);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar curso recomendado",
            description = "Consulta os dados completos de um curso recomendado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Curso encontrado."),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro ao consultar curso.")
    })
    public ResponseEntity<EntityModel<CursoRecomendadoResponseDTO>> buscarPorId(@PathVariable Long id) {
        CursoRecomendadoResponseDTO response = cursoService.buscarPorId(id);

        EntityModel<CursoRecomendadoResponseDTO> resource = EntityModel.of(
                response,
                linkTo(methodOn(CursoRecomendadoController.class)
                        .buscarPorId(id)).withSelfRel(),
                linkTo(methodOn(CursoRecomendadoController.class)
                        .listarPorTrilha(response.idTrilha(), null)).withRel("cursos_da_trilha")
        );

        return ResponseEntity.ok(resource);
    }

    @GetMapping
    @Operation(
            summary = "Listar cursos por trilha",
            description = "Retorna todos os cursos vinculados a uma trilha específica, com filtro opcional por plataforma."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cursos listados com sucesso."),
            @ApiResponse(responseCode = "500", description = "Erro ao listar cursos.")
    })
    public ResponseEntity<CollectionModel<EntityModel<CursoRecomendadoResponseDTO>>> listarPorTrilha(
            @RequestParam Long idTrilha,
            @RequestParam(required = false) PlataformaCurso plataforma
    ) {
        List<CursoRecomendadoResponseDTO> cursos =
                cursoService.listarPorTrilha(idTrilha, plataforma);

        List<EntityModel<CursoRecomendadoResponseDTO>> content = cursos.stream()
                .map(c -> EntityModel.of(
                        c,
                        linkTo(methodOn(CursoRecomendadoController.class)
                                .buscarPorId(c.id())).withSelfRel()
                ))
                .toList();

        CollectionModel<EntityModel<CursoRecomendadoResponseDTO>> collectionModel =
                CollectionModel.of(
                        content,
                        linkTo(methodOn(CursoRecomendadoController.class)
                                .listarPorTrilha(idTrilha, plataforma)).withSelfRel()
                );

        return ResponseEntity.ok(collectionModel);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar curso recomendado",
            description = "Atualiza nome, plataforma, URL e vínculo da trilha."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Curso atualizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Payload inválido."),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro ao atualizar curso.")
    })
    public ResponseEntity<EntityModel<CursoRecomendadoResponseDTO>> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CursoRecomendadoRequestDTO dto
    ) {
        CursoRecomendadoResponseDTO response = cursoService.atualizar(id, dto);

        EntityModel<CursoRecomendadoResponseDTO> resource = EntityModel.of(
                response,
                linkTo(methodOn(CursoRecomendadoController.class)
                        .buscarPorId(id)).withSelfRel(),
                linkTo(methodOn(CursoRecomendadoController.class)
                        .listarPorTrilha(response.idTrilha(), null)).withRel("cursos_da_trilha")
        );

        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir curso recomendado",
            description = "Remove o curso recomendado sem afetar trilhas e usuários já registrados."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Curso excluído com sucesso."),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro ao excluir curso.")
    })
    public ResponseEntity<EntityModel<MensagemResponseDTO>> excluir(@PathVariable Long id) {
        cursoService.excluir(id);

        MensagemResponseDTO mensagem =
                new MensagemResponseDTO("Curso recomendado removido com sucesso.");

        EntityModel<MensagemResponseDTO> resource = EntityModel.of(
                mensagem,
                // Link genérico para consulta de cursos por trilha; idTrilha será informado pelo consumidor
                linkTo(methodOn(CursoRecomendadoController.class)
                        .listarPorTrilha(null, null)).withRel("cursos_por_trilha")
        );

        return ResponseEntity.ok(resource);
    }
}
