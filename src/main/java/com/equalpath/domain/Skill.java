package com.equalpath.domain;

import com.equalpath.domain.enums.CategoriaSkill;
import com.equalpath.domain.enums.NivelTrilha;
import com.equalpath.domain.enums.TipoSkill;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SKILL")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSkill")
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 500)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NivelTrilha nivel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CategoriaSkill categoria;

    private LocalDate ultimoAcesso;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoSkill tipo;

    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UsuarioSkill> usuarios = new HashSet<>();

    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TrilhaSkillNecessaria> trilhas = new HashSet<>();
}

