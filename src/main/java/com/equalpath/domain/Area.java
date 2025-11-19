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
    @Column(name = "IDAREA")
    private Long id;

    @Column(name = "NOME", nullable = false, length = 100)
    private String nome;

    @Column(name = "DESCRICAO", length = 300)
    private String descricao;

    @Column(name = "DTCRIACAO", nullable = false)
    private LocalDate dtCriacao;

    @OneToMany(mappedBy = "area", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // evita loop UsuarioArea -> Area -> UsuarioArea
    private Set<UsuarioArea> usuarios = new HashSet<>();
}
