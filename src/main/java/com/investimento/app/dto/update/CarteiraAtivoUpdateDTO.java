package com.investimento.app.dto.update;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarteiraAtivoUpdateDTO {

    private Optional<BigDecimal> quantidade = Optional.empty();
    private Optional<BigDecimal> precoEntrada = Optional.empty();
}
