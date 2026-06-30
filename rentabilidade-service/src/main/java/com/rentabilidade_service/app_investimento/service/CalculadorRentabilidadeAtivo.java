package com.rentabilidade_service.app_investimento.service;

import java.math.BigDecimal;
import java.util.Map;

public interface CalculadorRentabilidadeAtivo {
    Map<String, BigDecimal> calcularValoresAtuais(BigDecimal quantidade, BigDecimal precoEntrada, BigDecimal precoMercadoBrapi);
}