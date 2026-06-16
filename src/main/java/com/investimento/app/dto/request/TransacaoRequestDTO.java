package com.investimento.app.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.investimento.app.model.enums.TipoTransacao;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransacaoRequestDTO {

    @NotNull(message = "O ID da carteira é obrigatório.")
    private Long carteiraId;

    @NotNull(message = "O ID do ativo é obrigatório.")
    private Long ativoId;

    @NotNull(message = "O tipo da transação (COMPRA/VENDA) é obrigatório.")
    private TipoTransacao tipo;

    @NotNull(message = "A quantidade é obrigatória.")
    @Positive(message = "A quantidade deve ser maior que zero.")
    private Integer quantidade;

    @NotNull(message = "O preço unitário é obrigatório.")
    @Positive(message = "O preço unitário deve ser maior que zero.")
    private BigDecimal precoUnitario;

    @NotNull(message = "A data da transação é obrigatória.")
    private LocalDate data;
}
