package com.rentabilidade_service.app_investimento.service;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.rentabilidade_service.app_investimento.model.dto.CarteiraAtivoDTO;
import com.rentabilidade_service.app_investimento.model.dto.CarteiraResponseDTO;

import java.util.List;

@Service
public class MonolitoService {

    private final RestClient restClient;

    public MonolitoService() {
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
    }

    public List<CarteiraAtivoDTO> obterAtivosDaCarteira(Long carteiraId, String tokenAutenticacao) {
        try {
            // Mapeia o JSON diretamente para o DTO completo da carteira
            CarteiraResponseDTO carteira = restClient.get()
                    .uri("/api/carteiras/{id}", carteiraId)
                    .header("Authorization", tokenAutenticacao)
                    .retrieve()
                    .body(CarteiraResponseDTO.class);

            if (carteira != null && carteira.ativos() != null) {
                return carteira.ativos();
            }
            return List.of();
        } catch (Exception e) {
            throw new RuntimeException("Falha ao recuperar e tipar os ativos do Monólito: " + e.getMessage(), e);
        }
    }
}