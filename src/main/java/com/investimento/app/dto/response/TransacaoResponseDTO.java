package com.investimento.app.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.investimento.app.model.enums.TipoTransacao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoResponseDTO {

    private Long id;
    private Long carteiraId;
    private Long ativoId;
    private String ativoTicker; 
    private TipoTransacao tipo;
    private BigDecimal quantidade;
    private BigDecimal precoUnitario;
    private LocalDateTime data;
    private BigDecimal impostoRetido;
    private BigDecimal valorTotal; 

}
