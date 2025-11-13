package com.equalpath.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NotificationResponseDTO {

    private Long id;
    private String mensagem;
    private String tipo;
    private LocalDateTime dataCriacao;
    private boolean lida;
}
