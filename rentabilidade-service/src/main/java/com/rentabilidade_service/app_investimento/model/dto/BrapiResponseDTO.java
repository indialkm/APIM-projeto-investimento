package com.rentabilidade_service.app_investimento.model.dto;

import java.util.List;

public record BrapiResponseDTO(
    List<BrapiResultDTO> results,
    String requestedAt
) {}