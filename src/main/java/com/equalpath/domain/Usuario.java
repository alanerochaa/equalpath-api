package com.equalpath.domain;

import com.equalpath.domain.enums.ObjetivoCarreira;
import com.equalpath.domain.enums.StatusPerfil;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "USUARIO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nome;

    @Size(max = 100)
    @Column(length = 100)
    private String sobrenome;

    @NotBlank
    @Email
    @Size(max = 150)
    @Column(nullable = false, length = 150, unique = true)
    private String email;

    @Size(max = 20)
    @Column(length = 20)
    private String telefone;

    @Column
    private LocalDate dtCadastro;

    @Size(max = 2)
    @Column(length = 2)
    private String estado;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private ObjetivoCarreira objetivoCarreira;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private StatusPerfil statusPerfil;
}
