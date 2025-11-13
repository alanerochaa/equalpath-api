package com.equalpath.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApplicationResponseDTO {

    private Long id;
    private Long userId;
    private Long jobId;
    private String tituloVaga;
    private String empresa;
    private String status;
}
