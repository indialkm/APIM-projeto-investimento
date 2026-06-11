package com.investimento.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.investimento.app.model.Carteira;

public interface CarteiraAtivoRepository extends JpaRepository<Carteira, Long> {

}
