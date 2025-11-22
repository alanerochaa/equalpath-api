package com.equalpath.repository;

import com.equalpath.domain.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Page<Usuario> findByNomeContainingIgnoreCaseOrSobrenomeContainingIgnoreCase(
            String nome,
            String sobrenome,
            Pageable pageable
    );

    List<Usuario> findByStatusPerfil(String statusPerfil);
}
