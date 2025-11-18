package com.equalpath.repository;

import com.equalpath.domain.Usuario;
import com.equalpath.domain.enums.StatusPerfil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Page<Usuario> findByStatusPerfil(StatusPerfil statusPerfil, Pageable pageable);

    Page<Usuario> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
