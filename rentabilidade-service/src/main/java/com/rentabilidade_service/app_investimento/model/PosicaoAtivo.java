package com.rentabilidade_service.app_investimento.model;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import com.rentabilidade_service.app_investimento.service.CalculadorRentabilidadeAtivo;

public class PosicaoAtivo {
    
    private final String ticker;
    private final BigDecimal quantidade;
    private final BigDecimal precoCusto;
    
    // Campos dinâmicos calculados pelo comportamento do objeto
    private final BigDecimal precoAtual;
    private final BigDecimal valorTotalAtual;

    // O Construtor Rico exige os dados e a interface que sabe calcular o tipo do ativo!
    public PosicaoAtivo(String ticker, BigDecimal quantidade, BigDecimal precoCusto, 
                        BigDecimal precoMercadoBrapi, CalculadorRentabilidadeAtivo estrategiaCalculo) {
        
        this.ticker = ticker;
        this.quantidade = quantidade;
        this.precoCusto = precoCusto;

        // O próprio objeto ativa a interface estratégica para descobrir seu preço e valor atual!
        Map<String, BigDecimal> valoresAtuais = estrategiaCalculo.calcularValoresAtuais(quantidade, precoCusto, precoMercadoBrapi);
        
        this.precoAtual = valoresAtuais.get("precoAtual");
        this.valorTotalAtual = valoresAtuais.get("valorAtual");
    }

    // Métodos de negócio ricos e puros (Encapsulados)
    public BigDecimal getValorTotalInvestido() {
        return this.precoCusto.multiply(this.quantidade);
    }

    public BigDecimal getValorTotalAtual() {
        return this.valorTotalAtual;
    }

    public BigDecimal getLucroPrejuizo() {
        return getValorTotalAtual().subtract(getValorTotalInvestido());
    }

    public BigDecimal getRentabilidadePercentual() {
        BigDecimal investido = getValorTotalInvestido();
        if (investido.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ZERO;
        
        return getLucroPrejuizo()
                .multiply(new BigDecimal("100"))
                .divide(investido, 2, RoundingMode.HALF_UP);
    }

    // Getters padrões para o controlador ler os resultados
    public String getTicker() { return ticker; }
    public BigDecimal getQuantidade() { return quantidade; }
    public BigDecimal getPrecoCusto() { return precoCusto; }
    public BigDecimal getPrecoAtual() { return precoAtual; }
}