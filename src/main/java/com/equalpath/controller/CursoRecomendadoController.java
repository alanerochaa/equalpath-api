package com.equalpath.controller;

import com.equalpath.dto.CursoRecomendadoRequestDTO;
import com.equalpath.dto.CursoRecomendadoResponseDTO;
import com.equalpath.service.CursoRecomendadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
public class CursoRecomendadoController {

    private final CursoRecomendadoService cursoService;

    @PostMapping
    public ResponseEntity<CursoRecomendadoResponseDTO> criar(@Valid @RequestBody CursoRecomendadoRequestDTO dto) {
        CursoRecomendadoResponseDTO response = cursoService.criar(dto);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoRecomendadoResponseDTO> buscarPorId(@PathVariable Long id) {
        CursoRecomendadoResponseDTO response = cursoService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<CursoRecomendadoResponseDTO>> listarPorTrilha(
            @RequestParam Long idTrilha,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable
    ) {
        Page<CursoRecomendadoResponseDTO> page = cursoService.listarPorTrilha(idTrilha, pageable);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoRecomendadoResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CursoRecomendadoRequestDTO dto
    ) {
        CursoRecomendadoResponseDTO response = cursoService.atualizar(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        cursoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
