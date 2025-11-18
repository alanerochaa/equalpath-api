package com.equalpath.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "TRILHA_SKILL_NECESSARIA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrilhaSkillNecessaria {

    @EmbeddedId
    private TrilhaSkillNecessariaId id = new TrilhaSkillNecessariaId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("trilhaId")
    @JoinColumn(name = "TRILHA_idTrilha")
    private Trilha trilha;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("skillId")
    @JoinColumn(name = "SKILL_idSkill")
    private Skill skill;

    @Embeddable
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class TrilhaSkillNecessariaId implements Serializable {

        private Long trilhaId;
        private Long skillId;
    }
}
