package com.equalpath.controller;

import com.equalpath.dto.TrilhaRequestDTO;
import com.equalpath.dto.TrilhaResponseDTO;
import com.equalpath.service.TrilhaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trilhas")
@RequiredArgsConstructor
public class TrilhaController {

    private final TrilhaService trilhaService;

    @PostMapping
    public ResponseEntity<TrilhaResponseDTO> criar(@Valid @RequestBody TrilhaRequestDTO dto) {
        TrilhaResponseDTO response = trilhaService.criar(dto);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrilhaResponseDTO> buscarPorId(@PathVariable Long id) {
        TrilhaResponseDTO response = trilhaService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<TrilhaResponseDTO>> listar(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable
    ) {
        Page<TrilhaResponseDTO> page = trilhaService.listar(pageable);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrilhaResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody TrilhaRequestDTO dto
    ) {
        TrilhaResponseDTO response = trilhaService.atualizar(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        trilhaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
