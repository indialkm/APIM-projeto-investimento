package com.investimento.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.investimento.app.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByEmail(String username);

	boolean existsByEmail(String email);

}
