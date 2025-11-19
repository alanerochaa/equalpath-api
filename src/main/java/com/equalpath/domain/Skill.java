package com.equalpath.domain;

import com.equalpath.domain.enums.CategoriaSkill;
import com.equalpath.domain.enums.NivelTrilha;
import com.equalpath.domain.enums.TipoSkill;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SKILL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Skill {

    @Id
    @Column(name = "IDSKILL")
    private Long id;

    @Column(name = "NOME", nullable = false, length = 100)
    private String nome;

    @Column(name = "DESCRICAO", length = 500)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "NIVEL", nullable = false, length = 20)
    private NivelTrilha nivel;

    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORIA", nullable = false, length = 20)
    private CategoriaSkill categoria;

    @Column(name = "ULTIMOACESSO")
    private LocalDate ultimoAcesso;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO", nullable = false, length = 20)
    private TipoSkill tipo;

    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // Skill -> UsuarioSkill -> Usuario -> UsuarioSkill...
    private Set<UsuarioSkill> usuarios = new HashSet<>();

    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // Skill -> TrilhaSkillNecessaria -> Trilha -> TrilhaSkillNecessaria...
    private Set<TrilhaSkillNecessaria> trilhas = new HashSet<>();
}
