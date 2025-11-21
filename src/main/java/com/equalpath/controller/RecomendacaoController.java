package com.equalpath.controller;

import com.equalpath.dto.RecomendacaoResponseDTO;
import com.equalpath.service.RecomendacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/recomendacoes")
@RequiredArgsConstructor
@Tag(
        name = "Recomendações",
        description = "Motor de recomendações de trilhas e cursos com base no perfil do usuário."
)
@SecurityRequirement(name = "bearerAuth")
public class RecomendacaoController {

    private final RecomendacaoService recomendacaoService;

    @GetMapping("/usuario/{idUsuario}")
    @Operation(
            summary = "Recomendar trilhas e cursos para o usuário",
            description = "Executa o motor de recomendação da EqualPath utilizando o ID do usuário como entrada."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Recomendações geradas com sucesso."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno ao processar recomendações.")
    })
    public ResponseEntity<CollectionModel<EntityModel<RecomendacaoResponseDTO>>> recomendarPorUsuario(
            @PathVariable Long idUsuario
    ) {
        List<RecomendacaoResponseDTO> recomendacoes =
                recomendacaoService.recomendarPorUsuario(idUsuario);

        List<EntityModel<RecomendacaoResponseDTO>> content = recomendacoes.stream()
                .map(r -> EntityModel.of(r))
                .toList();

        CollectionModel<EntityModel<RecomendacaoResponseDTO>> collectionModel =
                CollectionModel.of(
                        content,
                        linkTo(methodOn(RecomendacaoController.class)
                                .recomendarPorUsuario(idUsuario)).withSelfRel(),
                        linkTo(methodOn(UsuarioController.class)
                                .buscarPorId(idUsuario)).withRel("usuario"),
                        linkTo(methodOn(TrilhaController.class)
                                .listar(null)).withRel("trilhas"),
                        linkTo(methodOn(CursoRecomendadoController.class)
                                .listarPorTrilha(null, null)).withRel("cursos")
                );

        return ResponseEntity.ok(collectionModel);
    }
}
