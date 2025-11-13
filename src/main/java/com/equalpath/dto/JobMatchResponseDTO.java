package com.equalpath.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class JobMatchResponseDTO {

    private JobResponseDTO vaga;
    private int scoreMatch;
    private List<String> skillsQueBatendo;
    private List<String> skillsEmFalta;
    private String justificativa;
}
