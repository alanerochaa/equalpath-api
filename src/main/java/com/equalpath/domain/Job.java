package com.equalpath.domain;

import com.equalpath.domain.enums.JobMode;
import com.equalpath.domain.enums.SeniorityLevel;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, length = 2000)
    private String descricao;

    @Column
    private String empresa;

    @Enumerated(EnumType.STRING)
    private JobMode modalidade; // REMOTO, PRESENCIAL, HIBRIDO

    @Enumerated(EnumType.STRING)
    private SeniorityLevel senioridade;

    @Column
    private String localizacao;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<JobSkill> skills = new HashSet<>();
}
