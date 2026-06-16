package com.investimento.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.investimento.app.model.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

}
