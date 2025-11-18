package com.equalpath.service;

import com.equalpath.domain.Usuario;
import com.equalpath.dto.UsuarioRequestDTO;
import com.equalpath.dto.UsuarioResponseDTO;
import com.equalpath.exception.NotFoundException;
import com.equalpath.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

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
        usuario.setEstado(dto.estado());
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
        usuario.setEstado(dto.estado());
        usuario.setObjetivoCarreira(dto.objetivoCarreira());
        usuario.setStatusPerfil(dto.statusPerfil());
        // dtCadastro não é alterado no update

        Usuario atualizado = usuarioRepository.save(usuario);
        return mapToResponse(atualizado);
    }

    // =========================================
    // DELETE
    // =========================================
    @Transactional
    public void excluir(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new NotFoundException("Usuário não encontrado: " + id);
        }
        usuarioRepository.deleteById(id);
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
