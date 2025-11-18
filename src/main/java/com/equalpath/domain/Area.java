package com.equalpath.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "AREA")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idArea")
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 300)
    private String descricao;

    @Column(nullable = false)
    private LocalDate dtCriacao;

    @OneToMany(mappedBy = "area", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UsuarioArea> usuarios = new HashSet<>();
}
