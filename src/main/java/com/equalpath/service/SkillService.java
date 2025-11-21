package com.equalpath.service;

import com.equalpath.domain.Skill;
import com.equalpath.dto.SkillRequestDTO;
import com.equalpath.dto.SkillResponseDTO;
import com.equalpath.exception.NotFoundException;
import com.equalpath.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;

    @Transactional
    public SkillResponseDTO criar(SkillRequestDTO dto) {

        Skill skill = Skill.builder()
                .nome(dto.nome())
                .descricao(dto.descricao())
                .nivel(dto.nivel())
                .categoria(dto.categoria())
                .tipo(dto.tipo())
                .ultimoAcesso(LocalDate.now())
                .build();

        return toResponse(skillRepository.save(skill));
    }

    @Transactional(readOnly = true)
    public SkillResponseDTO buscarPorId(Long id) {
        return toResponse(getById(id));
    }

    @Transactional(readOnly = true)
    public List<SkillResponseDTO> listar() {
        return skillRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public SkillResponseDTO atualizar(Long id, SkillRequestDTO dto) {

        Skill skill = getById(id);

        skill.setNome(dto.nome());
        skill.setDescricao(dto.descricao());
        skill.setNivel(dto.nivel());
        skill.setCategoria(dto.categoria());
        skill.setTipo(dto.tipo());

        return toResponse(skillRepository.save(skill));
    }

    @Transactional
    public void excluir(Long id) {
        skillRepository.delete(getById(id));
    }

    private Skill getById(Long id) {
        return skillRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Skill não encontrada: " + id));
    }

    private SkillResponseDTO toResponse(Skill skill) {
        return new SkillResponseDTO(
                skill.getId(),
                skill.getNome(),
                skill.getDescricao(),
                skill.getNivel(),
                skill.getCategoria(),
                skill.getTipo(),
                skill.getUltimoAcesso()
        );
    }
}
