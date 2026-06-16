package com.investimento.app.dto.request;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarteiraAtivoRequestDTO {

    @NotNull(message = "O ID da carteira é obrigatório.")
    private Long carteiraId;

    @NotNull(message = "O ID do ativo é obrigatório.")
    private Long ativoId;

    @NotNull(message = "A quantidade é obrigatória.")
    @Positive(message = "A quantidade deve ser maior que zero.")
    private BigDecimal quantidade;

    @NotNull(message = "O preço de entrada é obrigatório.")
    @Positive(message = "O preço de entrada deve ser maior que zero.")
    private BigDecimal precoEntrada;
}
