package com.equalpath.domain;

import com.equalpath.domain.enums.ObjetivoCarreira;
import com.equalpath.domain.enums.StatusPerfil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USUARIO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @Column(name = "IDUSUARIO")
    private Long id;

    @Column(name = "NOME", nullable = false, length = 100)
    private String nome;

    @Column(name = "SOBRENOME", nullable = false, length = 200)
    private String sobrenome;

    @Column(name = "EMAIL", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "TELEFONE", length = 20)
    private String telefone;

    @Column(name = "DTCADASTRO", nullable = false)
    private LocalDate dtCadastro;

    @Column(name = "ESTADO", columnDefinition = "CHAR(2)")
    private String estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "OBJETIVOCARREIRA", nullable = false, length = 30)
    private ObjetivoCarreira objetivoCarreira;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUSPERFIL", nullable = false, length = 20)
    private StatusPerfil statusPerfil;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<UsuarioArea> areas = new HashSet<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<UsuarioSkill> skills = new HashSet<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<UsuarioTrilha> trilhas = new HashSet<>();
}
