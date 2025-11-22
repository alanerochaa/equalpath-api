package com.equalpath.repository;

import com.equalpath.domain.CursoRecomendado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CursoRecomendadoRepository extends JpaRepository<CursoRecomendado, Long> {

    List<CursoRecomendado> findByTrilha_Id(Long idTrilha);

    // antes recebia PlataformaCurso, agora String
    List<CursoRecomendado> findByTrilha_IdAndPlataforma(Long idTrilha, String plataforma);
}
