package com.equalpath.service;

import com.equalpath.domain.CursoRecomendado;
import com.equalpath.domain.Trilha;
import com.equalpath.dto.CursoRecomendadoRequestDTO;
import com.equalpath.dto.CursoRecomendadoResponseDTO;
import com.equalpath.exception.NotFoundException;
import com.equalpath.repository.CursoRecomendadoRepository;
import com.equalpath.repository.TrilhaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CursoRecomendadoService {

    private final CursoRecomendadoRepository cursoRepository;
    private final TrilhaRepository trilhaRepository;

    @Transactional
    public CursoRecomendadoResponseDTO criar(CursoRecomendadoRequestDTO dto) {
        Trilha trilha = trilhaRepository.findById(dto.idTrilha())
                .orElseThrow(() -> new NotFoundException("Trilha não encontrada para o id " + dto.idTrilha()));

        CursoRecomendado curso = new CursoRecomendado();
        curso.setNome(dto.nome());
        curso.setUrl(dto.url());
        curso.setTrilha(trilha);
        curso.setPlataforma(dto.plataforma());
        curso.setDuracaoHoras(dto.duracaoHoras());

        curso = cursoRepository.save(curso);
        return toResponse(curso);
    }

    @Transactional(readOnly = true)
    public Page<CursoRecomendadoResponseDTO> listarPorTrilha(Long idTrilha, Pageable pageable) {
        return cursoRepository.findByTrilha_Id(idTrilha, pageable)
                .map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public CursoRecomendadoResponseDTO buscarPorId(Long id) {
        CursoRecomendado curso = getById(id);
        return toResponse(curso);
    }

    @Transactional
    public CursoRecomendadoResponseDTO atualizar(Long id, CursoRecomendadoRequestDTO dto) {
        CursoRecomendado curso = getById(id);
        Trilha trilha = trilhaRepository.findById(dto.idTrilha())
                .orElseThrow(() -> new NotFoundException("Trilha não encontrada para o id " + dto.idTrilha()));

        curso.setNome(dto.nome());
        curso.setUrl(dto.url());
        curso.setTrilha(trilha);
        curso.setPlataforma(dto.plataforma());
        curso.setDuracaoHoras(dto.duracaoHoras());

        curso = cursoRepository.save(curso);
        return toResponse(curso);
    }

    @Transactional
    public void excluir(Long id) {
        CursoRecomendado curso = getById(id);
        cursoRepository.delete(curso);
    }

    private CursoRecomendado getById(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Curso recomendado não encontrado para o id " + id));
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
