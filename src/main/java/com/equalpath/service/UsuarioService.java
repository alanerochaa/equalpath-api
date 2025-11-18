package com.equalpath.service;

import com.equalpath.domain.Usuario;
import com.equalpath.dto.UsuarioRequestDTO;
import com.equalpath.dto.UsuarioResponseDTO;
import com.equalpath.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository4 usuarioRepository;

    @Transactional
    public UsuarioResponseDTO criar(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setSobrenome(dto.sobrenome());
        usuario.setEmail(dto.email());
        usuario.setTelefone(dto.telefone());
        usuario.setEstado(dto.estado());
        usuario.setObjetivoCarreira(dto.objetivoCarreira());
        usuario.setStatusPerfil(dto.statusPerfil());
        usuario.setDtCadastro(LocalDate.now());

        usuario = usuarioRepository.save(usuario);
        return toResponse(usuario);
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = getById(id);
        return toResponse(usuario);
    }

    @Transactional(readOnly = true)
    public Page<UsuarioResponseDTO> listar(String nome, Pageable pageable) {
        Page<Usuario> page;

        if (nome != null && !nome.isBlank()) {
            page = usuarioRepository
                    .findByNomeContainingIgnoreCaseOrSobrenomeContainingIgnoreCase(nome, nome, pageable);
        } else {
            page = usuarioRepository.findAll(pageable);
        }

        return page.map(this::toResponse);
    }

    @Transactional
    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = getById(id);

        usuario.setNome(dto.nome());
        usuario.setSobrenome(dto.sobrenome());
        usuario.setTelefone(dto.telefone());
        usuario.setEstado(dto.estado());
        usuario.setObjetivoCarreira(dto.objetivoCarreira());
        usuario.setStatusPerfil(dto.statusPerfil());
        // email geralmente não muda, mas se quiser:
        // usuario.setEmail(dto.email());

        usuario = usuarioRepository.save(usuario);
        return toResponse(usuario);
    }

    @Transactional
    public void excluir(Long id) {
        Usuario usuario = getById(id);
        usuarioRepository.delete(usuario);
    }

    private Usuario getById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado para o id " + id));
    }

    private UsuarioResponseDTO toResponse(Usuario usuario) {
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
