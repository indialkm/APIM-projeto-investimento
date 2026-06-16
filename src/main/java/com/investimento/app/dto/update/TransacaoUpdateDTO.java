package com.investimento.app.dto.update;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import com.investimento.app.model.enums.TipoTransacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoUpdateDTO {

    private Optional<Long> carteiraId = Optional.empty();
    private Optional<Long> ativoId = Optional.empty();
    private Optional<TipoTransacao> tipo = Optional.empty();
    private Optional<BigDecimal> quantidade = Optional.empty();
    private Optional<BigDecimal> precoUnitario = Optional.empty();
    private Optional<LocalDateTime> data = Optional.empty();
    private Optional<BigDecimal> impostoRetido = Optional.empty();
}