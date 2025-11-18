package com.equalpath.service;

import com.equalpath.domain.Skill;
import com.equalpath.dto.SkillRequestDTO;
import com.equalpath.dto.SkillResponseDTO;
import com.equalpath.exception.NotFoundException;
import com.equalpath.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;

    @Transactional
    public SkillResponseDTO criar(SkillRequestDTO dto) {
        Skill skill = new Skill();
        skill.setNome(dto.nome());
        skill.setDescricao(dto.descricao());
        skill.setNivel(dto.nivel());
        skill.setCategoria(dto.categoria());
        skill.setTipo(dto.tipo());
        skill.setUltimoAcesso(LocalDate.now());

        skill = skillRepository.save(skill);
        return toResponse(skill);
    }

    @Transactional(readOnly = true)
    public SkillResponseDTO buscarPorId(Long id) {
        Skill skill = getById(id);
        return toResponse(skill);
    }

    @Transactional(readOnly = true)
    public Page<SkillResponseDTO> listar(Pageable pageable) {
        return skillRepository.findAll(pageable)
                .map(this::toResponse);
    }

    @Transactional
    public SkillResponseDTO atualizar(Long id, SkillRequestDTO dto) {
        Skill skill = getById(id);

        skill.setNome(dto.nome());
        skill.setDescricao(dto.descricao());
        skill.setNivel(dto.nivel());
        skill.setCategoria(dto.categoria());
        skill.setTipo(dto.tipo());

        skill = skillRepository.save(skill);
        return toResponse(skill);
    }

    @Transactional
    public void excluir(Long id) {
        Skill skill = getById(id);
        skillRepository.delete(skill);
    }

    private Skill getById(Long id) {
        return skillRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Skill não encontrada para o id " + id));
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
