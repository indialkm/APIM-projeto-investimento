package com.investimento.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.investimento.app.model.Carteira;
import com.investimento.app.model.CarteiraAtivo;
import com.investimento.app.model.CarteiraAtivoId;

@Repository
public interface CarteiraAtivoRepository extends JpaRepository<CarteiraAtivo, CarteiraAtivoId> {
	
	CarteiraAtivo findByCarteiraIdAndAtivoId(Long carteiraId, Long ativoId);

}
