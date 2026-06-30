package com.rentabilidade_service.app_investimento.controller;


import com.rentabilidade_service.app_investimento.model.PosicaoAtivo;
import com.rentabilidade_service.app_investimento.model.dto.BrapiResponseDTO;
import com.rentabilidade_service.app_investimento.model.dto.BrapiResultDTO;
import com.rentabilidade_service.app_investimento.model.dto.CarteiraAtivoDTO;
import com.rentabilidade_service.app_investimento.service.BrapiService;
import com.rentabilidade_service.app_investimento.service.CalcularRentabilidadeService;
import com.rentabilidade_service.app_investimento.service.MonolitoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final MonolitoService monolitoService;
    private final BrapiService brapiService;
    private final CalcularRentabilidadeService calcularRentabilidadeService;

    public DashboardController(MonolitoService monolitoService, 
                               BrapiService brapiService, 
                               CalcularRentabilidadeService calcularRentabilidadeService) {
        this.monolitoService = monolitoService;
        this.brapiService = brapiService;
        this.calcularRentabilidadeService = calcularRentabilidadeService;
    }

    @GetMapping("/{carteiraId}")
    public ResponseEntity<?> buscarPosicaoDashboard(
            @PathVariable Long carteiraId, 
            HttpServletRequest request) {
        
        String token = request.getHeader("Authorization");

        try {
            // 1. Busca os ativos mapeados direto do objeto real da carteira
            List<CarteiraAtivoDTO> listaAtivos = monolitoService.obterAtivosDaCarteira(carteiraId, token);

            if (listaAtivos.isEmpty()) {
                return ResponseEntity.ok(List.of());
            }

            // 2. Extrai usando os nomes corretos do DTO (nomeAtivo e tipoAtivo)
            List<String> tickers = listaAtivos.stream()
                    .map(CarteiraAtivoDTO::nomeAtivo)
                    .toList();

            List<String> tiposAtivos = listaAtivos.stream()
                    .map(CarteiraAtivoDTO::tipoAtivo)
                    .toList();

            // 3. Consulta a API da Brapi
            String tickersQuery = String.join(",", tickers);
            BrapiResponseDTO dadosBrapi = brapiService.obterCotacoesEmTempoReal(tickersQuery);

            // 4. Estrutura o mapa de preços da Brapi
            Map<String, BigDecimal> precosBrapi = new HashMap<>();
            if (dadosBrapi != null && dadosBrapi.results() != null) {
                for (BrapiResultDTO res : dadosBrapi.results()) {
                    if (res.symbol() != null) {
                        precosBrapi.put(res.symbol().toUpperCase(), res.regularMarketPrice());
                    }
                }
            }

            // 5. Gera as posições com os modelos ricos do DDD
            List<PosicaoAtivo> posicoesCalculadas = calcularRentabilidadeService.gerarPosicoes(
                    listaAtivos, precosBrapi, tiposAtivos, tickers
                );

            return ResponseEntity.ok(posicoesCalculadas);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro no processamento do dashboard: " + e.getMessage());
        }
    }
}