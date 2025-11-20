package com.equalpath.service;

import com.equalpath.domain.Trilha;
import com.equalpath.dto.TrilhaRequestDTO;
import com.equalpath.dto.TrilhaResponseDTO;
import com.equalpath.exception.NotFoundException;
import com.equalpath.repository.TrilhaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TrilhaService {

    private final TrilhaRepository trilhaRepository;

    @Transactional
    public TrilhaResponseDTO criar(TrilhaRequestDTO dto) {
        Trilha trilha = new Trilha();
        trilha.setNome(dto.nome());
        trilha.setDescricao(dto.descricao());
        trilha.setNivel(dto.nivel());          // enum NivelTrilha
        trilha.setObjetivo(dto.objetivo());    // String (texto livre)
        trilha.setStatus(dto.status());        // enum StatusTrilha
        trilha.setDtCriacao(LocalDate.now());

        trilha = trilhaRepository.save(trilha);
        return toResponse(trilha);
    }

    @Transactional(readOnly = true)
    public TrilhaResponseDTO buscarPorId(Long id) {
        Trilha trilha = getById(id);
        return toResponse(trilha);
    }

    @Transactional(readOnly = true)
    public Page<TrilhaResponseDTO> listar(Pageable pageable) {
        return trilhaRepository.findAll(pageable)
                .map(this::toResponse);
    }

    @Transactional
    public TrilhaResponseDTO atualizar(Long id, TrilhaRequestDTO dto) {
        Trilha trilha = getById(id);

        trilha.setNome(dto.nome());
        trilha.setDescricao(dto.descricao());
        trilha.setNivel(dto.nivel());
        trilha.setObjetivo(dto.objetivo());
        trilha.setStatus(dto.status());

        trilha = trilhaRepository.save(trilha);
        return toResponse(trilha);
    }

    @Transactional
    public void excluir(Long id) {
        Trilha trilha = getById(id);
        trilhaRepository.delete(trilha);
    }

    private Trilha getById(Long id) {
        return trilhaRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Trilha não encontrada para o id " + id));
    }

    private TrilhaResponseDTO toResponse(Trilha trilha) {
        return new TrilhaResponseDTO(
                trilha.getId(),
                trilha.getNome(),
                trilha.getDescricao(),
                trilha.getNivel(),
                trilha.getObjetivo(),   // String
                trilha.getStatus(),
                trilha.getDtCriacao()
        );
    }
}
