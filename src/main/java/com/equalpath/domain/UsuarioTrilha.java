package com.equalpath.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "USUARIO_TRILHA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioTrilha {

    @EmbeddedId
    private UsuarioTrilhaId id = new UsuarioTrilhaId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("usuarioId")
    @JoinColumn(name = "USUARIO_idUsuario")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("trilhaId")
    @JoinColumn(name = "TRILHA_idTrilha")
    private Trilha trilha;

    @Embeddable
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class UsuarioTrilhaId implements Serializable {

        private Long usuarioId;
        private Long trilhaId;
    }
}
