package com.equalpath.repository;

import com.equalpath.domain.TrilhaSkillNecessaria;
import com.equalpath.domain.TrilhaSkillNecessaria.TrilhaSkillNecessariaId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrilhaSkillNecessariaRepository extends JpaRepository<TrilhaSkillNecessaria, TrilhaSkillNecessariaId> {

    List<TrilhaSkillNecessaria> findByTrilha_Id(Long idTrilha);
}
