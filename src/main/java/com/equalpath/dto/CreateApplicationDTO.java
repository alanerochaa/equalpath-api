package com.equalpath.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateApplicationDTO {

    @NotNull
    private Long userId;

    @NotNull
    private Long jobId;
}
