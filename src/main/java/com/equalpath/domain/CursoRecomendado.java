package com.equalpath.domain;

import com.equalpath.domain.enums.PlataformaCurso;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CURSO_RECOMENDADO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CursoRecomendado {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_curso")
    @SequenceGenerator(name = "seq_curso", sequenceName = "SEQ_CURSO", allocationSize = 1)
    @Column(name = "IDCURSO")
    private Long id;

    @Column(name = "NOME", nullable = false, length = 200)
    private String nome;

    @Column(name = "URL", nullable = false, length = 400)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRILHA_IDTRILHA", nullable = false)
    private Trilha trilha;

    @Enumerated(EnumType.STRING)
    @Column(name = "PLATAFORMA", nullable = false, length = 100)
    private PlataformaCurso plataforma;

    @Column(name = "DURACAOHORAS", nullable = false)
    private Integer duracaoHoras;
}
