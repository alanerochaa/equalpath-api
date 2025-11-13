package com.equalpath.dto;


import com.equalpath.domain.enums.JobMode;
import com.equalpath.domain.enums.SeniorityLevel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JobResponseDTO {

    private Long id;
    private String titulo;
    private String descricao;
    private String empresa;
    private String localizacao;
    private JobMode modalidade;
    private SeniorityLevel senioridade;
}
