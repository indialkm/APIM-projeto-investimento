package com.investimento.app.service.calculo;

import org.springframework.stereotype.Component;

import com.investimento.app.component.CalculadoraInvestimento;
import com.investimento.app.dto.request.CarteiraAtivoRequestDTO;
import com.investimento.app.model.Transacao;
import com.investimento.app.model.enums.TipoTransacao;
import com.investimento.app.service.CarteiraAtivoService;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;

@Component("RENDA_VARIAVEL")
@RequiredArgsConstructor
public class RendaVariavelCalculo implements ProcessadorAtivo {

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
           
            CarteiraAtivoRequestDTO posicaoDTO = new CarteiraAtivoRequestDTO();
            posicaoDTO.setCarteiraId(transacao.getCarteira().getId());
            posicaoDTO.setAtivoId(transacao.getAtivo().getId());
            posicaoDTO.setQuantidade(transacao.getQuantidade());
            posicaoDTO.setPrecoEntrada(transacao.getPrecoUnitario());

           
            BigDecimal precoMedioCompra = carteiraAtivoService.obterPrecoMedio(transacao.getCarteira().getId(), transacao.getAtivo().getId());
            
            carteiraAtivoService.removerOuReduzirAtivo(posicaoDTO); 
            BigDecimal precoVenda = transacao.getPrecoUnitario();
            BigDecimal quantidadeVendida = transacao.getQuantidade();
            
           
            BigDecimal lucroUnitario = precoVenda.subtract(precoMedioCompra);
            
            BigDecimal imposto = BigDecimal.ZERO;
            
         
            if (lucroUnitario.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal lucroTotalDaVenda = lucroUnitario.multiply(quantidadeVendida);
                
               
                imposto = calculadora.calcularImpostoRendaVariavel(lucroTotalDaVenda); 
            }
            
            transacao.setImpostoRetido(imposto);
        }
    }
}