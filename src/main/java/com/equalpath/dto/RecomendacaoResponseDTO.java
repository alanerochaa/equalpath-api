package com.equalpath.dto;

import java.math.BigDecimal;
import java.util.List;

public record RecomendacaoResponseDTO(
        Long idTrilha,
        String nomeTrilha,
        BigDecimal percentualAderencia,
        List<String> skillsUsuarioPossui,
        List<String> skillsNecessarias
) {}
