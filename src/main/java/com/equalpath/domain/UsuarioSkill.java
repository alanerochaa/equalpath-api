package com.equalpath.domain;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "USUARIO_SKILL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioSkill {

    @EmbeddedId
    private UsuarioSkillId id = new UsuarioSkillId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("usuarioId")
    @JoinColumn(name = "USUARIO_IDUSUARIO")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("skillId")
    @JoinColumn(name = "SKILL_IDSKILL")
    private Skill skill;

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class UsuarioSkillId implements Serializable {

        @Column(name = "USUARIO_IDUSUARIO")
        private Long usuarioId;

        @Column(name = "SKILL_IDSKILL")
        private Long skillId;
    }
}
