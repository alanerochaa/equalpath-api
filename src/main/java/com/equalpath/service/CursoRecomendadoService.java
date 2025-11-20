package com.equalpath.service;

import com.equalpath.domain.CursoRecomendado;
import com.equalpath.domain.Trilha;
import com.equalpath.dto.CursoRecomendadoRequestDTO;
import com.equalpath.dto.CursoRecomendadoResponseDTO;
import com.equalpath.exception.NotFoundException;
import com.equalpath.repository.CursoRecomendadoRepository;
import com.equalpath.repository.TrilhaRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CursoRecomendadoService {

    private final CursoRecomendadoRepository cursoRepository;
    private final TrilhaRepository trilhaRepository;

    @Transactional
    public CursoRecomendadoResponseDTO criar(@NotNull CursoRecomendadoRequestDTO dto) {

        Trilha trilha = trilhaRepository.findById(dto.idTrilha())
                .orElseThrow(() -> new NotFoundException("Trilha não encontrada: " + dto.idTrilha()));

        CursoRecomendado curso = CursoRecomendado.builder()
                .nome(dto.nome())
                .url(dto.url())
                .trilha(trilha)
                .plataforma(dto.plataforma())
                .duracaoHoras(dto.duracaoHoras())
                .build();

        return toResponse(cursoRepository.save(curso));
    }

    @Transactional(readOnly = true)
    public List<CursoRecomendadoResponseDTO> listarPorTrilha(Long idTrilha) {

        List<CursoRecomendado> cursos = cursoRepository.findByTrilha_Id(idTrilha);

        return cursos.stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CursoRecomendadoResponseDTO buscarPorId(Long id) {
        return toResponse(getById(id));
    }

    @Transactional
    public CursoRecomendadoResponseDTO atualizar(Long id, @NotNull CursoRecomendadoRequestDTO dto) {

        CursoRecomendado curso = getById(id);

        Trilha trilha = trilhaRepository.findById(dto.idTrilha())
                .orElseThrow(() -> new NotFoundException("Trilha não encontrada: " + dto.idTrilha()));

        curso.setNome(dto.nome());
        curso.setUrl(dto.url());
        curso.setTrilha(trilha);
        curso.setPlataforma(dto.plataforma());
        curso.setDuracaoHoras(dto.duracaoHoras());

        return toResponse(cursoRepository.save(curso));
    }

    @Transactional
    public void excluir(Long id) {
        cursoRepository.delete(getById(id));
    }

    private CursoRecomendado getById(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Curso recomendado não encontrado: " + id));
    }

    private CursoRecomendadoResponseDTO toResponse(CursoRecomendado curso) {
        return new CursoRecomendadoResponseDTO(
                curso.getId(),
                curso.getNome(),
                curso.getUrl(),
                curso.getTrilha().getId(),
                curso.getTrilha().getNome(),
                curso.getPlataforma(),
                curso.getDuracaoHoras()
        );
    }
}
