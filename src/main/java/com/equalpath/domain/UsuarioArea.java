package com.equalpath.domain;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "USUARIO_AREA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioArea {

    @EmbeddedId
    private UsuarioAreaId id = new UsuarioAreaId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("usuarioId")
    @JoinColumn(name = "USUARIO_IDUSUARIO")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("areaId")
    @JoinColumn(name = "AREA_IDAREA")
    private Area area;

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class UsuarioAreaId implements Serializable {

        @Column(name = "USUARIO_IDUSUARIO")
        private Long usuarioId;

        @Column(name = "AREA_IDAREA")
        private Long areaId;
    }
}
