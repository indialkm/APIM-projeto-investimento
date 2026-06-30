package com.rentabilidade_service.app_investimento.service;

import com.rentabilidade_service.app_investimento.model.dto.BrapiResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class BrapiService {

    private final RestClient restClient;
    
    
    private final String TOKEN = "fLvyqRj5VpCkHqDVCsbSve"; 

    public BrapiService() {
        this.restClient = RestClient.builder()
                .baseUrl("https://brapi.dev/api")
                .build();
    }

    /**
     * Busca as cotações no endpoint da Brapi enviando os tickers separados por vírgula.
     * @param tickers Exemplo: "PETR4,VALE3,ITUB4"
     */
    public BrapiResponseDTO obterCotacoesEmTempoReal(String tickers) {
        try {
            return restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/quote/" + tickers)
                            .queryParam("token", TOKEN)
                            .build())
                    .retrieve()
                    .body(BrapiResponseDTO.class); 
        } catch (Exception e) {
            System.err.println("Erro ao conectar ou ler dados da API Brapi: " + e.getMessage());
            
            return null; 
        }
    }
}