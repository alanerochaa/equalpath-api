package com.equalpath.controller;

import com.equalpath.dto.SkillRequestDTO;
import com.equalpath.dto.SkillResponseDTO;
import com.equalpath.service.SkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/skills")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    @PostMapping
    public ResponseEntity<SkillResponseDTO> criar(@Valid @RequestBody SkillRequestDTO dto) {
        SkillResponseDTO response = skillService.criar(dto);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillResponseDTO> buscarPorId(@PathVariable Long id) {
        SkillResponseDTO response = skillService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<SkillResponseDTO>> listar(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable
    ) {
        Page<SkillResponseDTO> page = skillService.listar(pageable);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SkillResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody SkillRequestDTO dto
    ) {
        SkillResponseDTO response = skillService.atualizar(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        skillService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
