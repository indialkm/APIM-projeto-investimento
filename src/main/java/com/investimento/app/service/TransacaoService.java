package com.investimento.app.service;

import com.investimento.app.model.Ativo;
import com.investimento.app.model.Carteira;
import com.investimento.app.model.Transacao;
import com.investimento.app.repository.TransacaoRepository;
import com.investimento.app.service.calculo.ProcessadorAtivo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final CarteiraService carteiraService;
    private final AtivoService ativoService;
    
    private final Map<String, ProcessadorAtivo> calculos;

    @Transactional
    public Transacao registrarTransacao(Long carteiraId, Long ativoId, Transacao transacaoPreenchida) {
        Carteira carteira = carteiraService.buscarPorId(carteiraId);
        Ativo ativo = ativoService.buscarPorId(ativoId);

        transacaoPreenchida.setCarteira(carteira);
        transacaoPreenchida.setAtivo(ativo);
        
        if (transacaoPreenchida.getQuantidade() != null && transacaoPreenchida.getPrecoUnitario() != null) {
            BigDecimal total = transacaoPreenchida.getQuantidade().multiply(transacaoPreenchida.getPrecoUnitario());
            transacaoPreenchida.setValorTotal(total);
        }

        String tipoEnumTexto = ativo.getTipoAtivo().name(); 
        ProcessadorAtivo processador = calculos.get(tipoEnumTexto);

        if (processador != null) {
            processador.processar(transacaoPreenchida);
        }

        Transacao transacaoSalva = transacaoRepository.save(transacaoPreenchida);
        return transacaoSalva;
    }
}	