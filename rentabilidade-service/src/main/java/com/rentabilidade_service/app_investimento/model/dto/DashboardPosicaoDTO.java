package com.rentabilidade_service.app_investimento.model.dto;

import java.util.List;

public record DashboardPosicaoDTO(
    String statusProcessamento,
    Long carteiraId,
    List<CarteiraAtivoDTO> ativos
) {}