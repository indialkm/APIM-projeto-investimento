package com.rentabilidade_service.app_investimento.service;

import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.Map;

@Component("RENT_RENDA_VARIAVEL")
public class RendaVariavelRentabilidade implements CalculadorRentabilidadeAtivo {

    @Override
    public Map<String, BigDecimal> calcularValoresAtuais(BigDecimal quantidade, BigDecimal precoEntrada, BigDecimal precoMercadoBrapi) {
       
        BigDecimal valorAtual = quantidade.multiply(precoMercadoBrapi);
        
        return Map.of(
            "precoAtual", precoMercadoBrapi,
            "valorAtual", valorAtual
        );
    }
}