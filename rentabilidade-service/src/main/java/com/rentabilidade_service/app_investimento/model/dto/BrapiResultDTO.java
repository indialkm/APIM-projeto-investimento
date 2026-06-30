package com.rentabilidade_service.app_investimento.model.dto;

import java.math.BigDecimal;

public record BrapiResultDTO(
    String symbol,
    String shortName,
    String longName,
    BigDecimal regularMarketPrice
) {}
