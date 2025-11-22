package com.equalpath.service;

import com.equalpath.domain.Trilha;
import com.equalpath.dto.TrilhaRequestDTO;
import com.equalpath.dto.TrilhaResponseDTO;
import com.equalpath.exception.NotFoundException;
import com.equalpath.repository.TrilhaRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrilhaService {

    private final TrilhaRepository trilhaRepository;

    @Transactional
    public TrilhaResponseDTO criar(@NotNull TrilhaRequestDTO dto) {

        String nivel = dto.nivel().trim().toUpperCase();
        String status = dto.status().trim().toUpperCase();

        Trilha trilha = Trilha.builder()
                .nome(dto.nome())
                .descricao(dto.descricao())
                .nivel(nivel)
                .objetivo(dto.objetivo())
                .status(status)
                .dtCriacao(LocalDate.now())
                .build();

        return mapToResponse(trilhaRepository.save(trilha));
    }

    @Transactional(readOnly = true)
    public TrilhaResponseDTO buscarPorId(Long id) {
        return mapToResponse(getById(id));
    }

    @Transactional(readOnly = true)
    public List<TrilhaResponseDTO> listar(String status) {

        List<Trilha> trilhas;

        if (status != null && !status.isBlank()) {
            trilhas = trilhaRepository.findByStatus(status.trim().toUpperCase());
        } else {
            trilhas = trilhaRepository.findAll();
        }

        return trilhas.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public TrilhaResponseDTO atualizar(Long id, @NotNull TrilhaRequestDTO dto) {

        Trilha trilha = getById(id);

        trilha.setNome(dto.nome());
        trilha.setDescricao(dto.descricao());
        trilha.setNivel(dto.nivel().trim().toUpperCase());
        trilha.setObjetivo(dto.objetivo());
        trilha.setStatus(dto.status().trim().toUpperCase());

        return mapToResponse(trilhaRepository.save(trilha));
    }

    @Transactional
    public void excluir(Long id) {
        Trilha trilha = getById(id);
        trilhaRepository.delete(trilha);
    }

    private Trilha getById(Long id) {
        return trilhaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Trilha não encontrada: " + id));
    }

    private TrilhaResponseDTO mapToResponse(Trilha trilha) {
        return new TrilhaResponseDTO(
                trilha.getId(),
                trilha.getNome(),
                trilha.getDescricao(),
                trilha.getNivel(),
                trilha.getObjetivo(),
                trilha.getStatus(),
                trilha.getDtCriacao()
        );
    }
}
