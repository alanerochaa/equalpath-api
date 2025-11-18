package com.equalpath.repository;

import com.equalpath.domain.CursoRecomendado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRecomendadoRepository extends JpaRepository<CursoRecomendado, Long> {

    Page<CursoRecomendado> findByTrilha_Id(Long idTrilha, Pageable pageable);
}
