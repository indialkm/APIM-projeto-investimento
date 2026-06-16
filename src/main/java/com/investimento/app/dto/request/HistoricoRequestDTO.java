package com.investimento.app.dto.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class HistoricoRequestDTO {

	@NotNull(message = "O ID do ativo é obrigatório.")
    private Long ativoId;

    @NotNull(message = "O preço de fechamento é obrigatório.")
    @Positive(message = "O preço de fechamento deve ser maior que zero.")
    private BigDecimal precoFechamento;

    @NotNull(message = "A data do registro é obrigatória.")
    private LocalDateTime data;

    private BigDecimal acumuloInflacao;
}
