package com.equalpath.service;

import com.equalpath.domain.Trilha;
import com.equalpath.domain.TrilhaSkillNecessaria;
import com.equalpath.domain.Usuario;
import com.equalpath.domain.UsuarioSkill;
import com.equalpath.dto.RecomendacaoResponseDTO;
import com.equalpath.exception.NotFoundException;
import com.equalpath.repository.TrilhaRepository;
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

    @Transactional(readOnly = true)
    public List<RecomendacaoResponseDTO> recomendarPorUsuario(Long idUsuario) {

        // Validação de existência do usuário
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() ->
                        new NotFoundException("Usuário não encontrado para o id " + idUsuario));

        // Skills que o usuário possui
        List<UsuarioSkill> usuarioSkills =
                usuarioSkillRepository.findByUsuario_Id(usuario.getId());

        // Se o usuário não tiver skills mapeadas, já retorna lista vazia
        if (usuarioSkills.isEmpty()) {
            return Collections.emptyList();
        }

        // Conjunto de IDs das skills do usuário (para match rápido)
        Set<Long> idsSkillsUsuario = usuarioSkills.stream()
                .map(us -> us.getSkill().getId())
                .collect(Collectors.toSet());

        // Mapa ID -> Nome das skills do usuário (para montagem dos nomes)
        Map<Long, String> nomeSkillsUsuario = usuarioSkills.stream()
                .collect(Collectors.toMap(
                        us -> us.getSkill().getId(),
                        us -> us.getSkill().getNome(),
                        (a, b) -> a // em caso de chave repetida, mantém o primeiro
                ));

        List<Trilha> trilhas = trilhaRepository.findAll();
        List<RecomendacaoResponseDTO> resultado = new ArrayList<>();

        for (Trilha trilha : trilhas) {

            // Skills necessárias configuradas para a trilha
            List<TrilhaSkillNecessaria> necessarias = trilha.getSkills().stream().toList();

            // Se a trilha não tiver skills de referência, ignora
            if (necessarias.isEmpty()) {
                continue;
            }

            Set<Long> idsSkillsNecessarias = necessarias.stream()
                    .map(ts -> ts.getSkill().getId())
                    .collect(Collectors.toSet());

            long qtdMatch = idsSkillsNecessarias.stream()
                    .filter(idsSkillsUsuario::contains)
                    .count();

            BigDecimal percentual = BigDecimal
                    .valueOf((qtdMatch * 100.0) / idsSkillsNecessarias.size())
                    .setScale(2, RoundingMode.HALF_UP);

            // Skills que o usuário possui e que batem com a trilha
            List<String> nomesUsuarioPossui = idsSkillsNecessarias.stream()
                    .filter(idsSkillsUsuario::contains)
                    .map(nomeSkillsUsuario::get)
                    .filter(Objects::nonNull)
                    .toList();

            // Todas as skills de referência da trilha
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

        // Ordena por maior aderência primeiro
        resultado.sort(
                Comparator.comparing(RecomendacaoResponseDTO::percentualAderencia)
                        .reversed()
        );

        return resultado;
    }
}
