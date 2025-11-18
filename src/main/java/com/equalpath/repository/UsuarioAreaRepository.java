package com.equalpath.repository;

import com.equalpath.domain.UsuarioArea;
import com.equalpath.domain.UsuarioArea.UsuarioAreaId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioAreaRepository extends JpaRepository<UsuarioArea, UsuarioAreaId> {
}
