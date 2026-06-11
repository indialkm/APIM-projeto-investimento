package com.investimento.app.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.investimento.app.dto.request.UsuarioRequestDTO;
import com.investimento.app.dto.response.UsuarioResponseDTO;
import com.investimento.app.dto.update.UsuarioUpdateDTO;
import com.investimento.app.model.Usuario;
import com.investimento.app.model.enums.Roles;
import com.investimento.app.repository.UsuarioRepository;


import jakarta.transaction.Transactional;

@Service
public class UsuarioService {
	
	private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper;
	
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, ModelMapper mapper) {
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
		this.mapper = mapper;
	}
    
    
    @Transactional
    public Usuario cadastrar(UsuarioRequestDTO dto) {

        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Este e-mail já está cadastrado no sistema.");
        }
        Usuario novoUsuario = mapper.map(dto, Usuario.class);
        String senhaCriptografada = passwordEncoder.encode(novoUsuario.getSenha());
        novoUsuario.setSenha(senhaCriptografada);
        novoUsuario.setAtivo(true);
        novoUsuario.setRoles(List.of(Roles.ROLE_USER)); 
        return usuarioRepository.save(novoUsuario);
    }
   
    
    @Transactional()
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + id));
    }
    
    @Transactional()
    public Page<UsuarioResponseDTO> listarTodos(Pageable pageable) {
        Page<Usuario> usuariosPage = usuarioRepository.findAll(pageable);
        return usuariosPage.map(usuario -> mapper.map(usuario, UsuarioResponseDTO.class));
    }
    
    @Transactional
    public UsuarioResponseDTO atualizar(Long id, UsuarioUpdateDTO dto) {
        
        Usuario usuarioExistente = buscarPorId(id);
                

        if (dto.getEmail().isPresent() && !usuarioExistente.getEmail().equals(dto.getEmail().get())) {
            if (usuarioRepository.existsByEmail(dto.getEmail().get())) {
                throw new IllegalArgumentException("O novo e-mail informado já está em uso.");
            }
        }
        dto.getNome().ifPresent(usuarioExistente::setNome);
        dto.getEmail().ifPresent(usuarioExistente::setEmail);
        
        dto.getSenha().ifPresent(senhaPura -> {
            if (!senhaPura.isBlank()) {
                usuarioExistente.setSenha(passwordEncoder.encode(senhaPura));
            }
        });

        Usuario usuarioAtualizado = usuarioRepository.save(usuarioExistente);
        return mapper.map(usuarioAtualizado, UsuarioResponseDTO.class);
    }
    
    @Transactional
    public void deletar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado com o ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    @Transactional
    public void desativar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + id));
        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
    }
    

}
