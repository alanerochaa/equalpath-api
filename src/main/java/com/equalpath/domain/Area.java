package com.equalpath.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "AREA")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idArea")
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String nome;

    @Size(max = 300)
    private String descricao;

    private LocalDate dtCriacao;
}
