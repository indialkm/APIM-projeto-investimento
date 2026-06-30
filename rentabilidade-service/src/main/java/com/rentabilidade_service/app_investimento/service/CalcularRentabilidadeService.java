package com.rentabilidade_service.app_investimento.service;


import com.rentabilidade_service.app_investimento.model.PosicaoAtivo;
import com.rentabilidade_service.app_investimento.model.dto.CarteiraAtivoDTO;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CalcularRentabilidadeService {

    private final Map<String, CalculadorRentabilidadeAtivo> calculadores;

    public CalcularRentabilidadeService(Map<String, CalculadorRentabilidadeAtivo> calculadores) {
        this.calculadores = calculadores;
    }

    public List<PosicaoAtivo> gerarPosicoes(List<CarteiraAtivoDTO> ativos, Map<String, BigDecimal> precosBrapi, 
                                            List<String> tiposAtivos, List<String> tickers) {
        List<PosicaoAtivo> posicoes = new ArrayList<>();

        for (int i = 0; i < ativos.size(); i++) {
            CarteiraAtivoDTO ativoDto = ativos.get(i);
            String ticker = tickers.get(i);
            String tipo = tiposAtivos.get(i); 

           
            CalculadorRentabilidadeAtivo estrategia = calculadores.get("RENT_" + tipo.toUpperCase());
            
            BigDecimal precoBrapi = precosBrapi.getOrDefault(ticker.toUpperCase(), ativoDto.precoEntrada());

            if (estrategia != null) {
               
                PosicaoAtivo posicao = new PosicaoAtivo(ticker, ativoDto.quantidade(), ativoDto.precoEntrada(), precoBrapi, estrategia);
                posicoes.add(posicao);
            }
        }

        return posicoes;
    }
}