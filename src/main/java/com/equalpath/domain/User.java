package com.equalpath.domain;

import com.equalpath.domain.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column
    private String localizacao;

    @Column
    private String areaInteresse;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserSkill> skills = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private List<Application> candidaturas;

    @OneToMany(mappedBy = "user")
    private List<Notification> notificacoes;
}
