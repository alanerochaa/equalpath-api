package com.equalpath.service;

import com.equalpath.domain.Trilha;
import com.equalpath.domain.TrilhaSkillNecessaria;
import com.equalpath.domain.Usuario;
import com.equalpath.domain.UsuarioSkill;
import com.equalpath.dto.RecomendacaoResponseDTO;
import com.equalpath.exception.NotFoundException;
import com.equalpath.repository.TrilhaRepository;
import com.equalpath.repository.TrilhaSkillNecessariaRepository;
import com.equalpath.repository.UsuarioRepository;
import com.equalpath.repository.UsuarioSkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecomendacaoService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioSkillRepository usuarioSkillRepository;
    private final TrilhaRepository trilhaRepository;
    private final TrilhaSkillNecessariaRepository trilhaSkillNecessariaRepository;

    @Transactional(readOnly = true)
    public List<RecomendacaoResponseDTO> recomendarPorUsuario(Long idUsuario) {

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado para o id " + idUsuario));

        // Skills que o usuário possui
        List<UsuarioSkill> usuarioSkills = usuarioSkillRepository.findByUsuario_Id(usuario.getId());
        Set<Long> idsSkillsUsuario = usuarioSkills.stream()
                .map(us -> us.getSkill().getId())
                .collect(Collectors.toSet());

        Map<Long, String> nomeSkillsUsuario = usuarioSkills.stream()
                .collect(Collectors.toMap(us -> us.getSkill().getId(), us -> us.getSkill().getNome(), (a, b) -> a));

        List<Trilha> trilhas = trilhaRepository.findAll();

        List<RecomendacaoResponseDTO> resultado = new ArrayList<>();

        for (Trilha trilha : trilhas) {
            List<TrilhaSkillNecessaria> necessarias =
                    trilhaSkillNecessariaRepository.findByTrilha_Id(trilha.getId());

            if (necessarias.isEmpty()) {
                continue; // trilha sem regra, pula
            }

            Set<Long> idsSkillsNecessarias = necessarias.stream()
                    .map(ts -> ts.getSkill().getId())
                    .collect(Collectors.toSet());

            long qtdMatch = idsSkillsNecessarias.stream()
                    .filter(idsSkillsUsuario::contains)
                    .count();

            BigDecimal percentual = BigDecimal
                    .valueOf(qtdMatch * 100.0 / idsSkillsNecessarias.size())
                    .setScale(2, RoundingMode.HALF_UP);

            List<String> nomesUsuarioPossui = idsSkillsNecessarias.stream()
                    .filter(idsSkillsUsuario::contains)
                    .map(nomeSkillsUsuario::get)
                    .filter(Objects::nonNull)
                    .toList();

            List<String> nomesNecessarias = necessarias.stream()
                    .map(ts -> ts.getSkill().getNome())
                    .toList();

            resultado.add(new RecomendacaoResponseDTO(
                    trilha.getId(),
                    trilha.getNome(),
                    percentual,
                    nomesUsuarioPossui,
                    nomesNecessarias
            ));
        }

        // Ordena da maior aderência pra menor
        resultado.sort(Comparator.comparing(RecomendacaoResponseDTO::percentualAderencia).reversed());
        return resultado;
    }
}
