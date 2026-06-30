package com.rentabilidade_service.app_investimento.model.dto;

import java.math.BigDecimal;

public record AtivoRendimentoDTO(
	    String ticker,
	    String nomeAtivo,
	    BigDecimal quantidade,
	    BigDecimal precoMedioEntrada,
	    BigDecimal cotacaoAtual,
	    BigDecimal valorTotalAtual,   
	    BigDecimal percentualNaCarteira
	) {}
