package com.investimento.app.controller;

import com.investimento.app.dto.response.TransacaoResponseDTO;
import com.investimento.app.model.Transacao;
import com.investimento.app.service.TransacaoService;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transacoes")
@RequiredArgsConstructor
public class TransacaoController {

    private final TransacaoService transacaoService;
    private final ModelMapper mapper;

    @PostMapping("/carteiras/{carteiraId}/ativos/{ativoId}")
    public ResponseEntity<TransacaoResponseDTO> registrar(
            @PathVariable Long carteiraId,
            @PathVariable Long ativoId,
            @RequestBody Transacao transacao) { 
        
        Transacao novaTransacao = transacaoService.registrarTransacao(carteiraId, ativoId, transacao);
        TransacaoResponseDTO response = mapper.map(novaTransacao, TransacaoResponseDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}