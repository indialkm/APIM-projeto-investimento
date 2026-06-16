package com.investimento.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.investimento.app.model.Ativo;

public interface AtivoRepository extends JpaRepository<Ativo, Long> {

	Optional<Ativo> findByTickerIgnoreCase(String trim);

	boolean existsByTickerIgnoreCase(String tickerFormatado);

}
