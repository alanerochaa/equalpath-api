package com.equalpath.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDTO {

    private String accessToken;
    private String tokenType;
    private Long userId;
    private String nome;
    private String role;
}
