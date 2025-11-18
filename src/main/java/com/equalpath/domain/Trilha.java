package com.equalpath.domain;

import com.equalpath.domain.enums.NivelTrilha;
import com.equalpath.domain.enums.StatusTrilha;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "TRILHA")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Trilha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTrilha")
    private Long id;

    @NotBlank
    @Size(max = 150)
    private String nome;

    @Size(max = 500)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private NivelTrilha nivel;

    @Size(max = 500)
    private String objetivo;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private StatusTrilha status;

    private LocalDate dtCriacao;

    @ManyToOne
    @JoinColumn(name = "AREA_idArea")
    private Area area;
}
