package com.equalpath.service;

import com.equalpath.domain.Usuario;
import com.equalpath.domain.enums.StatusPerfil;
import com.equalpath.dto.UsuarioRequestDTO;
import com.equalpath.dto.UsuarioResponseDTO;
import com.equalpath.exception.NotFoundException;
import com.equalpath.repository.UsuarioRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    // =========================================
    // CREATE
    // =========================================
    @Transactional
    public UsuarioResponseDTO criar(@NotNull UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();

        usuario.setNome(dto.nome());
        usuario.setSobrenome(dto.sobrenome());
        usuario.setEmail(dto.email());
        usuario.setTelefone(dto.telefone());

        // normaliza UF para maiúsculo
        usuario.setEstado(dto.estado() != null ? dto.estado().toUpperCase() : null);

        usuario.setObjetivoCarreira(dto.objetivoCarreira());
        usuario.setStatusPerfil(dto.statusPerfil());
        usuario.setDtCadastro(LocalDate.now()); // sistema define data de cadastro

        Usuario salvo = usuarioRepository.save(usuario);
        return mapToResponse(salvo);
    }

    // =========================================
    // READ by ID
    // =========================================
    @Transactional(readOnly = true)
    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado: " + id));

        return mapToResponse(usuario);
    }

    // =========================================
    // LIST paginado + filtro por nome/sobrenome
    // (mantido para atender requisito de paginação)
    // =========================================
    @Transactional(readOnly = true)
    public Page<UsuarioResponseDTO> listar(String nome, Pageable pageable) {
        Page<Usuario> pagina;

        if (nome != null && !nome.isBlank()) {
            pagina = usuarioRepository
                    .findByNomeContainingIgnoreCaseOrSobrenomeContainingIgnoreCase(
                            nome, nome, pageable
                    );
        } else {
            pagina = usuarioRepository.findAll(pageable);
        }

        return pagina.map(this::mapToResponse);
    }

    // =========================================
    // LIST sem paginação, filtrando por StatusPerfil
    // GET /api/usuarios/status?statusPerfil=ATIVO (exemplo de uso no controller)
    // =========================================
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listarPorStatus(StatusPerfil statusPerfil) {

        List<Usuario> usuarios;

        if (statusPerfil != null) {
            usuarios = usuarioRepository.findByStatusPerfil(statusPerfil);
        } else {
            // se não vier status, devolve tudo (sem filtro)
            usuarios = usuarioRepository.findAll();
        }

        return usuarios.stream()
                .map(this::mapToResponse)
                .toList();
    }

    // =========================================
    // UPDATE
    // =========================================
    @Transactional
    public UsuarioResponseDTO atualizar(Long id, @NotNull UsuarioRequestDTO dto) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado: " + id));

        usuario.setNome(dto.nome());
        usuario.setSobrenome(dto.sobrenome());
        usuario.setEmail(dto.email());
        usuario.setTelefone(dto.telefone());
        usuario.setEstado(dto.estado() != null ? dto.estado().toUpperCase() : null);
        usuario.setObjetivoCarreira(dto.objetivoCarreira());
        usuario.setStatusPerfil(dto.statusPerfil());
        // dtCadastro permanece o original

        Usuario atualizado = usuarioRepository.save(usuario);
        return mapToResponse(atualizado);
    }

    // =========================================
    // DELETE
    // =========================================
    @Transactional
    public void excluir(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado: " + id));

        usuarioRepository.delete(usuario); // garante cascade nas relações
    }

    // =========================================
    // MAPEAMENTO ENTITY -> DTO
    // =========================================
    private UsuarioResponseDTO mapToResponse(@NotNull Usuario usuario) {
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
