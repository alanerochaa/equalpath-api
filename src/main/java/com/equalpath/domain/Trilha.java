package com.equalpath.domain;

import com.equalpath.domain.enums.NivelTrilha;
import com.equalpath.domain.enums.ObjetivoCarreira;
import com.equalpath.domain.enums.StatusTrilha;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TRILHA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trilha {

    @Id
    @Column(name = "IDTRILHA")
    private Long id;

    @Column(name = "NOME", nullable = false, length = 150)
    private String nome;

    @Column(name = "DESCRICAO", length = 500)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "NIVEL", nullable = false, length = 20)
    private NivelTrilha nivel;

    @Enumerated(EnumType.STRING)
    @Column(name = "OBJETIVO", nullable = false, length = 30)
    private ObjetivoCarreira objetivo;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 20)
    private StatusTrilha status;

    @Column(name = "DTCRIACAO", nullable = false)
    private LocalDate dtCriacao;

    @OneToMany(mappedBy = "trilha", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // Trilha -> UsuarioTrilha -> Usuario -> UsuarioTrilha...
    private Set<UsuarioTrilha> usuarios = new HashSet<>();

    @OneToMany(mappedBy = "trilha", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // Trilha -> TrilhaSkillNecessaria -> Skill -> TrilhaSkillNecessaria...
    private Set<TrilhaSkillNecessaria> skills = new HashSet<>();

    @OneToMany(mappedBy = "trilha", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // evita carregar árvore inteira de cursos na doc
    private Set<CursoRecomendado> cursos = new HashSet<>();
}
