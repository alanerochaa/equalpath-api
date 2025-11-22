package com.equalpath.domain;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_skill")
    @SequenceGenerator(name = "seq_skill", sequenceName = "SEQ_SKILL", allocationSize = 1)
    @Column(name = "IDSKILL")
    private Long id;

    @Column(name = "NOME", nullable = false, length = 100)
    private String nome;

    @Column(name = "DESCRICAO", nullable = false, length = 500)
    private String descricao;

    @Column(name = "NIVEL", nullable = false, length = 40)
    private String nivel;

    @Column(name = "CATEGORIA", nullable = false, length = 50)
    private String categoria;

    @Column(name = "ULTIMOACESSO", nullable = false)
    private LocalDate ultimoAcesso;

    @Column(name = "TIPO", nullable = false, length = 50)
    private String tipo;

    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<UsuarioSkill> usuarios = new HashSet<>();

    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<TrilhaSkillNecessaria> trilhas = new HashSet<>();
}
