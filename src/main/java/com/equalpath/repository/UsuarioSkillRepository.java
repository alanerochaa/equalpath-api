package com.equalpath.repository;

import com.equalpath.domain.UsuarioSkill;
import com.equalpath.domain.UsuarioSkill.UsuarioSkillId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UsuarioSkillRepository extends JpaRepository<UsuarioSkill, UsuarioSkillId> {

    List<UsuarioSkill> findByUsuario_Id(Long usuarioId);

    void deleteByUsuario_Id(Long usuarioId);
}
