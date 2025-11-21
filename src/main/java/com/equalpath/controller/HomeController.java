package com.equalpath.controller;

import com.equalpath.dto.MensagemResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/home")
public class HomeController {

    @GetMapping
    public EntityModel<MensagemResponseDTO> home() {

        MensagemResponseDTO response =
                new MensagemResponseDTO(
                        "API EqualPath está rodando! Acesse o Swagger UI ou os recursos principais."
                );

        EntityModel<MensagemResponseDTO> model = EntityModel.of(response);

        // self
        model.add(linkTo(methodOn(HomeController.class).home()).withSelfRel());

        model.add(linkTo(methodOn(UsuarioController.class)
                .listarPorStatus(null)).withRel("usuarios"));

        model.add(linkTo(methodOn(TrilhaController.class)
                .listar(null)).withRel("trilhas"));

        model.add(linkTo(methodOn(SkillController.class)
                .listar()).withRel("skills"));

        model.add(Link.of("/swagger-ui/index.html").withRel("swagger-ui"));

        return model;
    }
}
