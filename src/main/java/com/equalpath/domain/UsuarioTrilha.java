package com.equalpath.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "USUARIO_TRILHA")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class UsuarioTrilha {

    @EmbeddedId
    private UsuarioTrilhaId id;

    @ManyToOne
    @MapsId("idUsuario")
    @JoinColumn(name = "USUARIO_idUsuario")
    private Usuario usuario;

    @ManyToOne
    @MapsId("idTrilha")
    @JoinColumn(name = "TRILHA_idTrilha")
    private Trilha trilha;

    private String status;

    private LocalDate dataInicio;

    private LocalDate dataConclusao;
}
