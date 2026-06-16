package com.investimento.app.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.investimento.app.model.Carteira;

public interface CarteiraRepository extends JpaRepository<Carteira, Long> {
	
	Page<Carteira> findByUsuarioId(Long usuarioId, Pageable pageable);
	Optional<Carteira> findByIdAndUsuarioId(Long id, Long usuarioId);

}
