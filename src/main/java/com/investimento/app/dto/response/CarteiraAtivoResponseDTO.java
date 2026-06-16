package com.investimento.app.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarteiraAtivoResponseDTO {
    private Long ativoId;
    private String nomeAtivo;
    private String tipoAtivo;

    private BigDecimal quantidade;
    private BigDecimal precoEntrada; 
    
    private BigDecimal valorTotalInvestido; 
}
