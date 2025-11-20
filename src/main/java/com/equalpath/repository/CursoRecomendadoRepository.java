package com.equalpath.repository;

import com.equalpath.domain.CursoRecomendado;
import com.equalpath.domain.enums.PlataformaCurso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CursoRecomendadoRepository extends JpaRepository<CursoRecomendado, Long> {

    List<CursoRecomendado> findByTrilha_Id(Long idTrilha);

    List<CursoRecomendado> findByTrilha_IdAndPlataforma(Long idTrilha, PlataformaCurso plataforma);
}
