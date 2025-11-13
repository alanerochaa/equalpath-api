package com.equalpath.service;

import com.equalpath.domain.*;
import com.equalpath.dto.JobMatchResponseDTO;
import com.equalpath.dto.JobResponseDTO;
import com.equalpath.exception.NotFoundException;
import com.equalpath.repository.JobRepository;
import com.equalpath.repository.JobSkillRepository;
import com.equalpath.repository.UserRepository;
import com.equalpath.repository.UserSkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final UserRepository userRepository;
    private final UserSkillRepository userSkillRepository;
    private final JobRepository jobRepository;
    private final JobSkillRepository jobSkillRepository;

    public Page<JobMatchResponseDTO> sugerirVagas(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        List<UserSkill> userSkills = userSkillRepository.findByUser(user);
        Page<Job> vagas = jobRepository.findAll(pageable);

        return vagas.map(vaga -> buildMatch(vaga, userSkills));
    }

    private JobMatchResponseDTO buildMatch(Job vaga, List<UserSkill> userSkills) {
        List<JobSkill> jobSkills = jobSkillRepository.findByJob(vaga);

        List<String> skillsQueBatendo = jobSkills.stream()
                .filter(js -> userSkills.stream()
                        .anyMatch(us -> us.getSkill().getId().equals(js.getSkill().getId())))
                .map(js -> js.getSkill().getNome())
                .toList();

        List<String> skillsEmFalta = jobSkills.stream()
                .filter(js -> userSkills.stream()
                        .noneMatch(us -> us.getSkill().getId().equals(js.getSkill().getId())))
                .map(js -> js.getSkill().getNome())
                .toList();

        int total = jobSkills.size();
        int matchCount = skillsQueBatendo.size();
        int score = total == 0 ? 0 : (int) ((matchCount * 100.0) / total);

        JobResponseDTO vagaDto = JobResponseDTO.builder()
                .id(vaga.getId())
                .titulo(vaga.getTitulo())
                .descricao(vaga.getDescricao())
                .empresa(vaga.getEmpresa())
                .localizacao(vaga.getLocalizacao())
                .modalidade(vaga.getModalidade())
                .senioridade(vaga.getSenioridade())
                .build();

        String justificativa = String.format("Casou %d de %d habilidades requeridas", matchCount, total);

        return JobMatchResponseDTO.builder()
                .vaga(vagaDto)
                .scoreMatch(score)
                .skillsQueBatendo(skillsQueBatendo)
                .skillsEmFalta(skillsEmFalta)
                .justificativa(justificativa)
                .build();
    }
}
