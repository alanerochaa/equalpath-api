package com.equalpath.domain;

import com.equalpath.domain.enums.NivelTrilha;
import com.equalpath.domain.enums.ObjetivoCarreira;
import com.equalpath.domain.enums.StatusTrilha;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TRILHA")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trilha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTrilha")
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(length = 500)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NivelTrilha nivel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ObjetivoCarreira objetivo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusTrilha status;

    @Column(nullable = false)
    private LocalDate dtCriacao;

    @OneToMany(mappedBy = "trilha", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UsuarioTrilha> usuarios = new HashSet<>();

    @OneToMany(mappedBy = "trilha", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TrilhaSkillNecessaria> skills = new HashSet<>();

    @OneToMany(mappedBy = "trilha", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CursoRecomendado> cursos = new HashSet<>();
}

