package com.rentabilidade_service.app_investimento.model.dto;

import java.util.List;

public record MonolitoPageResponseDTO<T>(
    List<T> content,
    boolean last,
    long totalElements,
    int totalPages,
    int size,
    int number
) {}