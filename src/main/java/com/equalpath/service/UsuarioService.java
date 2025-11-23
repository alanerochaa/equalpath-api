package com.equalpath.service;

import com.equalpath.domain.Usuario;
import com.equalpath.dto.UsuarioRequestDTO;
import com.equalpath.dto.UsuarioResponseDTO;
import com.equalpath.exception.NotFoundException;
import com.equalpath.repository.UsuarioAreaRepository;
import com.equalpath.repository.UsuarioRepository;
import com.equalpath.repository.UsuarioSkillRepository;
import com.equalpath.repository.UsuarioTrilhaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioSkillRepository usuarioSkillRepository;
    private final UsuarioTrilhaRepository usuarioTrilhaRepository;
    private final UsuarioAreaRepository usuarioAreaRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public UsuarioResponseDTO criar(@NotNull UsuarioRequestDTO dto) {

        LocalDate hoje = LocalDate.now();

        StoredProcedureQuery sp = entityManager
                .createStoredProcedureQuery("PRC_INS_USUARIO");

        sp.registerStoredProcedureParameter("p_idUsuario",        Long.class,   ParameterMode.IN);
        sp.registerStoredProcedureParameter("p_nome",             String.class, ParameterMode.IN);
        sp.registerStoredProcedureParameter("p_sobrenome",        String.class, ParameterMode.IN);
        sp.registerStoredProcedureParameter("p_email",            String.class, ParameterMode.IN);
        sp.registerStoredProcedureParameter("p_telefone",         String.class, ParameterMode.IN);
        sp.registerStoredProcedureParameter("p_dtCadastro",       Date.class,   ParameterMode.IN);
        sp.registerStoredProcedureParameter("p_estado",           String.class, ParameterMode.IN);
        sp.registerStoredProcedureParameter("p_objetivoCarreira", String.class, ParameterMode.IN);
        sp.registerStoredProcedureParameter("p_statusPerfil",     String.class, ParameterMode.IN);

        sp.setParameter("p_idUsuario", null); // deixa a SEQ_USUARIO cuidar do ID
        sp.setParameter("p_nome", dto.nome());
        sp.setParameter("p_sobrenome", dto.sobrenome());
        sp.setParameter("p_email", dto.email());
        sp.setParameter("p_telefone", dto.telefone());
        sp.setParameter("p_dtCadastro", Date.valueOf(hoje));
        sp.setParameter("p_estado", dto.estado().toUpperCase());
        sp.setParameter("p_objetivoCarreira", dto.objetivoCarreira());
        sp.setParameter("p_statusPerfil", dto.statusPerfil());

        sp.execute();

        Usuario usuario = usuarioRepository.findByEmail(dto.email())
                .orElseThrow(() -> new IllegalStateException(
                        "Usuário não encontrado após inserção via procedure: " + dto.email()
                ));

        return mapToResponse(usuario);
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado: " + id));

        return mapToResponse(usuario);
    }

    @Transactional(readOnly = true)
    public Page<UsuarioResponseDTO> listar(String nome, Pageable pageable) {
        Page<Usuario> pagina;

        if (nome != null && !nome.isBlank()) {
            pagina = usuarioRepository.findByNomeContainingIgnoreCaseOrSobrenomeContainingIgnoreCase(
                    nome, nome, pageable
            );
        } else {
            pagina = usuarioRepository.findAll(pageable);
        }

        return pagina.map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listarPorStatus(String statusPerfil) {
        List<Usuario> usuarios;

        if (statusPerfil != null && !statusPerfil.isBlank()) {
            usuarios = usuarioRepository.findByStatusPerfil(statusPerfil);
        } else {
            usuarios = usuarioRepository.findAll();
        }

        return usuarios.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public UsuarioResponseDTO atualizar(Long id, @NotNull UsuarioRequestDTO dto) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado: " + id));

        usuario.setNome(dto.nome());
        usuario.setSobrenome(dto.sobrenome());
        usuario.setEmail(dto.email());
        usuario.setTelefone(dto.telefone());
        usuario.setEstado(dto.estado().toUpperCase());
        usuario.setObjetivoCarreira(dto.objetivoCarreira());
        usuario.setStatusPerfil(dto.statusPerfil());

        return mapToResponse(usuarioRepository.save(usuario));
    }

    @Transactional
    public void excluir(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado: " + id));

        usuarioSkillRepository.deleteByUsuario_Id(id);
        usuarioTrilhaRepository.deleteByUsuario_Id(id);
        usuarioAreaRepository.deleteByUsuario_Id(id);

        usuarioRepository.delete(usuario);
    }

    private UsuarioResponseDTO mapToResponse(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getSobrenome(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getEstado(),
                usuario.getDtCadastro(),
                usuario.getObjetivoCarreira(),
                usuario.getStatusPerfil()
        );
    }
}
