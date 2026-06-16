package com.investimento.app.service.calculo;

import org.springframework.stereotype.Component;
import com.investimento.app.dto.request.CarteiraAtivoRequestDTO;
import com.investimento.app.model.Transacao;
import com.investimento.app.model.enums.TipoTransacao;
import com.investimento.app.service.CarteiraAtivoService;
import com.investimento.app.component.CalculadoraInvestimento;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

@Component("RENDA_FIXA")
@RequiredArgsConstructor
public class RendaFixaCalculo implements ProcessadorAtivo {
	
	private final CarteiraAtivoService carteiraAtivoService;
	private final CalculadoraInvestimento calculadora; 

	@Override
	public void processar(Transacao transacao) {
		if (transacao.getTipo() == TipoTransacao.COMPRA) {
            
            CarteiraAtivoRequestDTO posicaoDTO = new CarteiraAtivoRequestDTO();
            posicaoDTO.setCarteiraId(transacao.getCarteira().getId());
            posicaoDTO.setAtivoId(transacao.getAtivo().getId());
            posicaoDTO.setQuantidade(transacao.getQuantidade());
            posicaoDTO.setPrecoEntrada(transacao.getPrecoUnitario());

            carteiraAtivoService.adicionarAtivo(posicaoDTO);
           
            transacao.setImpostoRetido(BigDecimal.ZERO);
            
        } else if (transacao.getTipo() == TipoTransacao.VENDA) {
        	
        	CarteiraAtivoRequestDTO vendaDTO = new CarteiraAtivoRequestDTO();
			vendaDTO.setCarteiraId(transacao.getCarteira().getId());
			vendaDTO.setAtivoId(transacao.getAtivo().getId());
			vendaDTO.setQuantidade(transacao.getQuantidade());
			vendaDTO.setPrecoEntrada(transacao.getPrecoUnitario());
			
			carteiraAtivoService.removerOuReduzirAtivo(vendaDTO);
           
			//Qualquer dia eu ainda vou colocar isso no request pra conseguir editar no envio
			long diasCorridos = 400; 
			BigDecimal lucroBruto = transacao.getValorTotal().multiply(BigDecimal.valueOf(0.1));

			BigDecimal imposto = calculadora.calcularImpostoRendaFixa(lucroBruto, diasCorridos);
			transacao.setImpostoRetido(imposto);
			
        }
    }
}