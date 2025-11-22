package com.equalpath.domain;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
    @SequenceGenerator(name = "seq_usuario", sequenceName = "SEQ_USUARIO", allocationSize = 1)
    @Column(name = "IDUSUARIO")
    private Long id;

    @Column(name = "NOME", nullable = false, length = 100)
    private String nome;

    @Column(name = "SOBRENOME", nullable = false, length = 100)
    private String sobrenome;

    @Column(name = "EMAIL", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "TELEFONE", nullable = false, length = 20)
    private String telefone;

    @Column(name = "ESTADO", nullable = false, length = 2)
    private String estado;

    @Column(name = "OBJETIVOCARREIRA", nullable = false, length = 50)
    private String objetivoCarreira;

    @Column(name = "STATUSPERFIL", nullable = false, length = 50)
    private String statusPerfil;

    @Column(name = "DTCADASTRO", nullable = false)
    private LocalDate dtCadastro;
}
