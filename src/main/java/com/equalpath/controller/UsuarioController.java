package com.equalpath.controller;

import com.equalpath.domain.enums.StatusPerfil;
import com.equalpath.dto.MensagemResponseDTO;
import com.equalpath.dto.UsuarioRequestDTO;
import com.equalpath.dto.UsuarioResponseDTO;
import com.equalpath.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(
        name = "Usuário",
        description = "Gestão do cadastro de usuários da EqualPath."
)
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    @Operation(
            summary = "Criar usuário",
            description = "Realiza o onboarding de um novo usuário na EqualPath, gravando os dados básicos de perfil."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para criação do usuário."),
            @ApiResponse(responseCode = "409", description = "Conflito de dados (e-mail já cadastrado)."),
            @ApiResponse(responseCode = "500", description = "Erro interno ao criar usuário.")
    })
    public ResponseEntity<EntityModel<UsuarioResponseDTO>> criar(
            @Valid @RequestBody UsuarioRequestDTO dto
    ) {
        UsuarioResponseDTO response = usuarioService.criar(dto);

        EntityModel<UsuarioResponseDTO> resource = EntityModel.of(
                response,
                linkTo(methodOn(UsuarioController.class).buscarPorId(response.id())).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).listarPorStatus(null)).withRel("usuarios")
        );

        URI location = linkTo(methodOn(UsuarioController.class)
                .buscarPorId(response.id())).toUri();

        return ResponseEntity.created(location).body(resource);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar usuário por ID",
            description = "Recupera os dados de um usuário específico pelo ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro ao consultar usuário.")
    })
    public ResponseEntity<EntityModel<UsuarioResponseDTO>> buscarPorId(@PathVariable Long id) {
        UsuarioResponseDTO response = usuarioService.buscarPorId(id);

        EntityModel<UsuarioResponseDTO> resource = EntityModel.of(
                response,
                linkTo(methodOn(UsuarioController.class).buscarPorId(id)).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).listarPorStatus(null)).withRel("usuarios")
        );

        return ResponseEntity.ok(resource);
    }

    @GetMapping("/status")
    @Operation(
            summary = "Listar usuários por status",
            description = "Filtra usuários por status do perfil (ATIVO, INATIVO, PENDENTE). Caso vazio, retorna todos."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuários listados."),
            @ApiResponse(responseCode = "500", description = "Erro interno ao listar usuários.")
    })
    public ResponseEntity<CollectionModel<EntityModel<UsuarioResponseDTO>>> listarPorStatus(
            @Parameter(
                    description = "Status do perfil do usuário (opcional). Valores possíveis: ATIVO, INATIVO, PENDENTE.",
                    schema = @Schema(implementation = StatusPerfil.class)
            )
            @RequestParam(required = false) StatusPerfil statusPerfil
    ) {
        String statusFiltro = (statusPerfil != null ? statusPerfil.name() : null);

        List<UsuarioResponseDTO> usuarios = usuarioService.listarPorStatus(statusFiltro);

        List<EntityModel<UsuarioResponseDTO>> content = usuarios.stream()
                .map(u -> EntityModel.of(
                        u,
                        linkTo(methodOn(UsuarioController.class).buscarPorId(u.id())).withSelfRel()
                ))
                .toList();

        CollectionModel<EntityModel<UsuarioResponseDTO>> collection =
                CollectionModel.of(
                        content,
                        linkTo(methodOn(UsuarioController.class).listarPorStatus(statusPerfil)).withSelfRel()
                );

        return ResponseEntity.ok(collection);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar usuário",
            description = "Atualiza os dados cadastrais de um usuário existente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário atualizado."),
            @ApiResponse(responseCode = "400", description = "Payload inválido."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro ao atualizar usuário.")
    })
    public ResponseEntity<EntityModel<UsuarioResponseDTO>> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDTO dto
    ) {
        UsuarioResponseDTO response = usuarioService.atualizar(id, dto);

        EntityModel<UsuarioResponseDTO> resource = EntityModel.of(
                response,
                linkTo(methodOn(UsuarioController.class).buscarPorId(id)).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).listarPorStatus(null)).withRel("usuarios")
        );

        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir usuário",
            description = "Remove o usuário e desvincula todas as relações auxiliares."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário excluído."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro ao excluir usuário.")
    })
    public ResponseEntity<EntityModel<MensagemResponseDTO>> excluir(@PathVariable Long id) {

        usuarioService.excluir(id);

        MensagemResponseDTO mensagem = new MensagemResponseDTO("Usuário removido com sucesso.");

        EntityModel<MensagemResponseDTO> resource = EntityModel.of(
                mensagem,
                linkTo(methodOn(UsuarioController.class).listarPorStatus(null)).withRel("usuarios")
        );

        return ResponseEntity.ok(resource);
    }
}
