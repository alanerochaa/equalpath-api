package com.equalpath.domain;

import com.equalpath.domain.enums.CategoriaSkill;
import com.equalpath.domain.enums.TipoSkill;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "SKILL")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSkill")
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String nome;

    @Size(max = 500)
    private String descricao;

    @Size(max = 20)
    private String nivel;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private CategoriaSkill categoria;

    private LocalDate ultimoAcesso;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private TipoSkill tipo;
}
