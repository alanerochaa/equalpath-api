package com.equalpath.controller;

import com.equalpath.dto.RecomendacaoResponseDTO;
import com.equalpath.service.RecomendacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recomendacoes")
@RequiredArgsConstructor
public class RecomendacaoController {

    private final RecomendacaoService recomendacaoService;

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<RecomendacaoResponseDTO>> recomendarPorUsuario(@PathVariable Long idUsuario) {
        List<RecomendacaoResponseDTO> recomendacoes = recomendacaoService.recomendarPorUsuario(idUsuario);
        return ResponseEntity.ok(recomendacoes);
    }
}
