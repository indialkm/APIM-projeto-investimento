package com.investimento.app.service;

import com.investimento.app.dto.request.AtivoRequestDTO;
import com.investimento.app.model.Ativo;
import com.investimento.app.repository.AtivoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AtivoService {

    private final AtivoRepository ativoRepository;
    private final ModelMapper mapper;

    @Transactional
    public Ativo criar(AtivoRequestDTO requestDTO) {
     
        String tickerFormatado = requestDTO.getTicker().toUpperCase().trim();

        if (ativoRepository.existsByTickerIgnoreCase(tickerFormatado)) {
            throw new IllegalArgumentException("Já existe um ativo cadastrado com o ticker: " + tickerFormatado);
        }

        Ativo ativo = new Ativo();
        ativo.setTicker(tickerFormatado);
        ativo.setNome(requestDTO.getNome());
        ativo.setTipoAtivo(requestDTO.getTipoAtivo());
        
        ativo.setStatus(true);

        return ativoRepository.save(ativo);
    }
    
    

    @Transactional(readOnly = true)
    public Ativo buscarPorId(Long id) {
        return ativoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ativo não encontrado com o ID: " + id));
    }

    @Transactional(readOnly = true)
    public Ativo buscarPorTicker(String ticker) {
        return ativoRepository.findByTickerIgnoreCase(ticker.trim())
                .orElseThrow(() -> new RuntimeException("Ativo não encontrado com o ticker: " + ticker.toUpperCase()));
    }

    @Transactional(readOnly = true)
    public Page<Ativo> listarTodos(Pageable pageable) {
        return ativoRepository.findAll(pageable);
    }

    /*@Transactional
    public Ativo atualizar(Long id, AtivoUpdateDTO updateDTO) {
        Ativo ativoExistente = buscarPorId(id);
        updateDTO.getNome() = null ?
        
        		ativoExistente.setNome(updateDTO.getNome());
        ativoExistente.setTipoAtivo(updateDTO.getTipoAtivo());

        return ativoRepository.save(ativoExistente);
    }*/

    @Transactional
    public void deletar(Long id) {
        Ativo ativo = buscarPorId(id);
        
        // Se alguma carteira já possuir esse ativo, o delete físico falharia por FK. 
        // Por segurança, você pode optar por desativar ou dar o delete físico caso queira limpar tudo.
        try {
            ativoRepository.delete(ativo);
        } catch (Exception e) {
            throw new IllegalStateException("Não é possível excluir o ativo pois ele já está vinculado a históricos ou carteiras. Considere desativá-lo.");
        }
    }

    @Transactional
    public void ativar(Long id) {
        Ativo ativo = buscarPorId(id);
        ativo.setStatus(true);
        ativoRepository.save(ativo);
    }

    @Transactional
    public void desativar(Long id) {
        Ativo ativo = buscarPorId(id);
        ativo.setStatus(false);
        ativoRepository.save(ativo);
    }
}
