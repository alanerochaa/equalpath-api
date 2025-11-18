package com.equalpath.domain;

import com.equalpath.domain.enums.PlataformaCurso;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "CURSO_RECOMENDADO")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class CursoRecomendado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCurso")
    private Long id;

    @NotBlank
    @Size(max = 200)
    private String nome;

    @Size(max = 400)
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private PlataformaCurso plataforma;

    private Integer duracaoHoras;

    @ManyToOne
    @JoinColumn(name = "TRILHA_idTrilha")
    private Trilha trilha;
}
