package com.equalpath.domain;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_trilha")
    @SequenceGenerator(name = "seq_trilha", sequenceName = "SEQ_TRILHA", allocationSize = 1)
    @Column(name = "IDTRILHA")
    private Long id;

    @Column(name = "NOME", nullable = false, length = 150)
    private String nome;

    @Column(name = "DESCRICAO", nullable = false, length = 500)
    private String descricao;

    @Column(name = "NIVEL", nullable = false, length = 40)
    private String nivel;

    @Column(name = "OBJETIVO", nullable = false, length = 500)
    private String objetivo;

    @Column(name = "STATUS", nullable = false, length = 40)
    private String status;

    @Column(name = "DTCRIACAO", nullable = false)
    private LocalDate dtCriacao;

    @OneToMany(mappedBy = "trilha", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<UsuarioTrilha> usuarios = new HashSet<>();

    @OneToMany(mappedBy = "trilha", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<TrilhaSkillNecessaria> skills = new HashSet<>();

    @OneToMany(mappedBy = "trilha", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<CursoRecomendado> cursos = new HashSet<>();
}
