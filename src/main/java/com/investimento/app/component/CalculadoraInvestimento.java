package com.investimento.app.component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Component;

@Component
public class CalculadoraInvestimento {
	
    /**
     * Calcula o Preço Médio Ponderado
     * Ele é usado para compra de ativos, uma vez que não é só possivel somar ou fazer uma média comum é 
     * preciso fazer uma podenrada pela quantidade de ações de cada compra
     */
    public BigDecimal calcularPrecoMedio(BigDecimal qtdAntiga, BigDecimal precoAntigo, 
                                         BigDecimal qtdNova, BigDecimal precoNovo) {
        BigDecimal totalAntigo = qtdAntiga.multiply(precoAntigo);
        BigDecimal totalNovo = qtdNova.multiply(precoNovo);
        BigDecimal quantidadeTotal = qtdAntiga.add(qtdNova);
        
        return totalAntigo.add(totalNovo).divide(quantidadeTotal, 4, RoundingMode.HALF_UP);
    }
    
    /**
     * Calcula o valor atualizado de um título de Renda Fixa Pré-fixado
     */
    public BigDecimal calcularRendimentoRendaFixa(BigDecimal valorAplicado, BigDecimal taxaAnual, long diasCorridos) {
        double taxa = taxaAnual.doubleValue();
        double principal = valorAplicado.doubleValue();
        
        double expoente = diasCorridos / 365.0;
        double valorAtualizado = principal * Math.pow(1 + taxa, expoente);
        
        return BigDecimal.valueOf(valorAtualizado).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calcula a alíquota do Imposto de Renda Regressivo com base nos dias
     */
    public BigDecimal calcularImpostoRendaFixa(BigDecimal lucroBruto, long diasCorridos) {
        double aliquota;
        
        if (diasCorridos <= 180) {
            aliquota = 0.225; 
        } else if (diasCorridos <= 360) {
            aliquota = 0.20;  
        } else if (diasCorridos <= 720) {
            aliquota = 0.175; 
        } else {
            aliquota = 0.15;  
        }
        
        return lucroBruto.multiply(BigDecimal.valueOf(aliquota)).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * NOVO MÉTODO: Calcula o Imposto de Renda para Renda Variável (Ações)
     * Alíquota padrão de 15% sobre o lucro obtido.
     */
    public BigDecimal calcularImpostoRendaVariavel(BigDecimal lucroBruto) {
        return lucroBruto.multiply(BigDecimal.valueOf(0.15)).setScale(2, RoundingMode.HALF_UP);
    }
}