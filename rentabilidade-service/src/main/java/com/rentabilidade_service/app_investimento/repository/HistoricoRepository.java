package com.rentabilidade_service.app_investimento.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rentabilidade_service.app_investimento.model.Historico;

@Repository
public interface HistoricoRepository extends JpaRepository<Historico, UUID> {

}
