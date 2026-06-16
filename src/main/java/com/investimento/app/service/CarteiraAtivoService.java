package com.investimento.app.service;

import java.math.BigDecimal;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.investimento.app.component.CalculadoraInvestimento;
import com.investimento.app.dto.request.CarteiraAtivoRequestDTO;
import com.investimento.app.model.Ativo;
import com.investimento.app.model.Carteira;
import com.investimento.app.model.CarteiraAtivo;
import com.investimento.app.model.CarteiraAtivoId;
import com.investimento.app.repository.CarteiraAtivoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarteiraAtivoService {

	private final AtivoService ativoService;
	private final CarteiraService carteiraService;
	private final CarteiraAtivoRepository carteiraAtivoRepository;
	private final AuthenticationService auth;
	private final ModelMapper mapper;
	
	private final CalculadoraInvestimento calculadora;
	
	@Transactional
    public CarteiraAtivo adicionarAtivo(CarteiraAtivoRequestDTO requestDTO) {
        /*Valida se a carteira existe, já tem a lógica de pegar só pelo usuário*/
        Carteira carteiraEntity = carteiraService.buscarPorId(requestDTO.getCarteiraId());
        
        /*Valida se existe os ativos, nesse caso os ativos são visto por todos*/
        Ativo ativoEntity = ativoService.buscarPorId(requestDTO.getAtivoId());

        /*Monta chave composta*/
        CarteiraAtivoId idComposto = new CarteiraAtivoId(carteiraEntity.getId(), ativoEntity.getId());

        /*Aqui é a lógica de criação porque se já existe ele só soma, mas se não existe ele cria*/
        CarteiraAtivo carteiraAtivo = carteiraAtivoRepository.findById(idComposto)
                .map(posicaoExistente -> atualizarPosicaoExistente(posicaoExistente, requestDTO))
                .orElseGet(() -> criarNovaPosicao(idComposto, carteiraEntity, ativoEntity, requestDTO));

        return carteiraAtivoRepository.save(carteiraAtivo);
    }
	
	private CarteiraAtivo atualizarPosicaoExistente(CarteiraAtivo posicaoExistente, CarteiraAtivoRequestDTO requestDTO) {
        BigDecimal qtdAntiga = posicaoExistente.getQuantidade();
        BigDecimal precoAntigo = posicaoExistente.getPrecoEntrada();
        
        BigDecimal qtdNova = requestDTO.getQuantidade();
        BigDecimal precoNovo = requestDTO.getPrecoEntrada();

        /*Preco Medio é o custo unitário ponderado de um ativo*/
        BigDecimal novoPrecoMedio = calculadora.calcularPrecoMedio(qtdAntiga, precoAntigo, qtdNova, precoNovo);

        posicaoExistente.setQuantidade(qtdAntiga.add(qtdNova));
        posicaoExistente.setPrecoEntrada(novoPrecoMedio);
        
        return posicaoExistente;
    }
	
	private CarteiraAtivo criarNovaPosicao(CarteiraAtivoId idComposto, Carteira carteira, Ativo ativo, CarteiraAtivoRequestDTO requestDTO) {
       
        CarteiraAtivo novaPosicao = mapper.map(requestDTO, CarteiraAtivo.class);
        novaPosicao.setId(idComposto);
        novaPosicao.setCarteira(carteira);
        novaPosicao.setAtivo(ativo);
        
        return novaPosicao;
    }
	
	public CarteiraAtivo buscarCarteiraAtivo(Long idCarteira, Long idAtivo)
	{
		return carteiraAtivoRepository.findByCarteiraIdAndAtivoId(idCarteira, idAtivo);
		
	}
	
	/**
     * Busca o preço médio de entrada atual de um ativo em uma carteira específica.
     * Útil para o cálculo do lucro real na venda.
     */
    public BigDecimal obterPrecoMedio(Long idCarteira, Long idAtivo) {
        CarteiraAtivo posicao = buscarCarteiraAtivo(idCarteira, idAtivo);
        if (posicao == null) {
            throw new jakarta.persistence.EntityNotFoundException(
                "O investidor não possui o ativo informado nesta carteira."
            );
        }
        return posicao.getPrecoEntrada();
    }

    /**
     * Reduz a quantidade de ativos após uma venda ou remove a posição 
     * caso o investidor tenha zerado suas cotas/ações.
     */
    @Transactional
    public void removerOuReduzirAtivo(CarteiraAtivoRequestDTO requestDTO) {
        // 1. Instancia a chave composta
        CarteiraAtivoId idComposto = new CarteiraAtivoId(requestDTO.getCarteiraId(), requestDTO.getAtivoId());
        
        // 2. Busca a posição atual no banco
        CarteiraAtivo posicaoExistente = carteiraAtivoRepository.findById(idComposto)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException(
                    "Não foi possível processar a venda pois o ativo não existe na carteira."
                ));

        BigDecimal qtdAtual = posicaoExistente.getQuantidade();
        BigDecimal qtdVendida = requestDTO.getQuantidade();

        // 3. Validação de segurança: impede vender mais do que possui
        if (qtdAtual.compareTo(qtdVendida) < 0) {
            throw new IllegalArgumentException(
                "Margem insuficiente: Você tentou vender " + qtdVendida + 
                " unidades, mas só possui " + qtdAtual + " em carteira."
            );
        }

        // 4. Se a quantidade final for ZERO, deletamos o registro da carteira
        if (qtdAtual.compareTo(qtdVendida) == 0) {
            carteiraAtivoRepository.delete(posicaoExistente);
        } else {
            // 5. Se ainda sobrarem ativos, apenas subtraímos a quantidade
            // Lembre-se: O preço médio de entrada NÃO se altera em operações de venda
            posicaoExistente.setQuantidade(qtdAtual.subtract(qtdVendida));
            carteiraAtivoRepository.save(posicaoExistente);
        }
    }
	
}
