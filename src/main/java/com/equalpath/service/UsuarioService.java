package com.equalpath.service;


import com.equalpath.domain.Usuario;
import com.equalpath.domain.enums.StatusPerfil;
import com.equalpath.dto.UsuarioRequestDTO;
import com.equalpath.dto.UsuarioResponseDTO;
import com.equalpath.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioResponseDTO criar(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setSobrenome(dto.sobrenome());
        usuario.setEmail(dto.email());
        usuario.setTelefone(dto.telefone());
        usuario.setEstado(dto.estado());
        usuario.setObjetivoCarreira(dto.objetivoCarreira());
        usuario.setStatusPerfil(StatusPerfil.ATIVO);
        usuario.setDtCadastro(LocalDate.now());

        Usuario salvo = repository.save(usuario);
        return toResponse(salvo);
    }

    public Page<UsuarioResponseDTO> listar(String nome, StatusPerfil status, Pageable pageable) {
        Page<Usuario> page;

        if (nome != null && !nome.isBlank()) {
            page = repository.findByNomeContainingIgnoreCase(nome, pageable);
        } else if (status != null) {
            page = repository.findByStatusPerfil(status, pageable);
        } else {
            page = repository.findAll(pageable);
        }

        return page.map(this::toResponse);
    }

    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        return toResponse(usuario);
    }

    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        usuario.setNome(dto.nome());
        usuario.setSobrenome(dto.sobrenome());
        usuario.setTelefone(dto.telefone());
        usuario.setEstado(dto.estado());
        usuario.setObjetivoCarreira(dto.objetivoCarreira());

        Usuario salvo = repository.save(usuario);
        return toResponse(salvo);
    }

    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Usuário não encontrado");
        }
        repository.deleteById(id);
    }

    private UsuarioResponseDTO toResponse(Usuario u) {
        return new UsuarioResponseDTO(
                u.getId(),
                u.getNome(),
                u.getSobrenome(),
                u.getEmail(),
                u.getTelefone(),
                u.getEstado(),
                u.getObjetivoCarreira(),
                u.getStatusPerfil(),
                u.getDtCadastro()
        );
    }
}
