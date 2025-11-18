package com.equalpath.domain;

import com.equalpath.domain.enums.PlataformaCurso;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CURSO_RECOMENDADO")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CursoRecomendado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCurso")
    private Long id;

    @Column(nullable = false, length = 200)
    private String nome;

    @Column(nullable = false, length = 400)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRILHA_idTrilha", nullable = false)
    private Trilha trilha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private PlataformaCurso plataforma;

    @Column(nullable = false)
    private Integer duracaoHoras;
}

