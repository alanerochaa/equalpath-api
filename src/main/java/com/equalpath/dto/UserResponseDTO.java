package com.equalpath.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private String localizacao;
    private String areaInteresse;
    private String role;
}
