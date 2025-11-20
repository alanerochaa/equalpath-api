package com.equalpath.domain;

import com.equalpath.domain.enums.ObjetivoCarreira;
import com.equalpath.domain.enums.StatusPerfil;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
    @SequenceGenerator(
            name = "usuario_seq",
            sequenceName = "SEQ_USUARIO", // sequence já criada no Oracle
            allocationSize = 1
    )
    @Column(name = "IDUSUARIO")
    private Long id;

    @Column(name = "NOME", length = 100, nullable = false)
    private String nome;

    // 🔁 alinhado com VARCHAR2(100) do banco
    @Column(name = "SOBRENOME", length = 100, nullable = false)
    private String sobrenome;

    @Column(name = "EMAIL", length = 150, nullable = false, unique = true)
    private String email;

    @Column(name = "TELEFONE", length = 20)
    private String telefone;

    // 🔁 agora bate com CHAR(2) do Oracle (corrige o erro de validação)
    @Column(name = "ESTADO", columnDefinition = "CHAR(2)", nullable = false)
    private String estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "OBJETIVOCARREIRA", nullable = false, length = 500)
    private ObjetivoCarreira objetivoCarreira;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUSPERFIL", nullable = false, length = 20)
    private StatusPerfil statusPerfil;

    @Column(name = "DTCADASTRO", nullable = false)
    private LocalDate dtCadastro;
}
