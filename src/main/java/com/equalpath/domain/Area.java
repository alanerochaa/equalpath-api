package com.equalpath.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "AREA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_area")
    @SequenceGenerator(name = "seq_area", sequenceName = "SEQ_AREA", allocationSize = 1)
    @Column(name = "IDAREA")
    private Long id;

    @Column(name = "NOME", nullable = false, length = 100)
    private String nome;

    @Column(name = "DESCRICAO", nullable = false, length = 300)
    private String descricao;

    @Column(name = "DTCRIACAO", nullable = false)
    private LocalDate dtCriacao;

    @OneToMany(mappedBy = "area", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<UsuarioArea> usuarios = new HashSet<>();
}
