package com.rentabilidade_service.app_investimento.model.dto;

import java.util.List;

public record CarteiraResponseDTO(
    Long id,
    String nome,
    String descricao,
    List<CarteiraAtivoDTO> ativos // Lista de relacionamentos do Monólito
) {}