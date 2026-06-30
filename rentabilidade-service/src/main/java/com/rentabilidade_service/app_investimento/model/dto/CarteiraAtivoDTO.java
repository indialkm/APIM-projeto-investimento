package com.rentabilidade_service.app_investimento.model.dto;

import java.math.BigDecimal;

public record CarteiraAtivoDTO(
    Long ativoId,
    String nomeAtivo,       
    String tipoAtivo,       
    BigDecimal quantidade,
    BigDecimal precoEntrada,
    BigDecimal valorTotalInvestido
) {}