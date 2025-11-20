package com.equalpath.repository;

import com.equalpath.domain.CursoRecomendado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CursoRecomendadoRepository extends JpaRepository<CursoRecomendado, Long> {

    // busca todos os cursos vinculados a uma trilha específica
    List<CursoRecomendado> findByTrilha_Id(Long idTrilha);
}
