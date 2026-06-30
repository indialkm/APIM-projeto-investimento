package com.rentabilidade_service.app_investimento.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.Map;

@Component("RENT_RENDA_FIXA")
@RequiredArgsConstructor
public class RendaFixaRentabilidade implements CalculadorRentabilidadeAtivo {

    private final CalculadoraInvestimento calculadora;

    @Override
    public Map<String, BigDecimal> calcularValoresAtuais(BigDecimal quantidade, BigDecimal precoEntrada, BigDecimal precoMercadoBrapi) {
        BigDecimal valorAplicado = quantidade.multiply(precoEntrada);
        
        BigDecimal taxaAnual = BigDecimal.valueOf(0.12); 
        long diasCorridos = 150; 

        
        BigDecimal valorAtualizado = calculadora.calcularRendimentoRendaFixa(valorAplicado, taxaAnual, diasCorridos);
        
        BigDecimal precoAtualNaCurva = valorAtualizado.divide(quantidade, 4, java.math.RoundingMode.HALF_UP);

        return Map.of(
            "precoAtual", precoAtualNaCurva,
            "valorAtual", valorAtualizado
        );
    }
}