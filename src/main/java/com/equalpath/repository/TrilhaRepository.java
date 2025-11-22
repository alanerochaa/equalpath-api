package com.equalpath.repository;

import com.equalpath.domain.Trilha;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TrilhaRepository extends JpaRepository<Trilha, Long> {

    List<Trilha> findByStatus(String status);
}
