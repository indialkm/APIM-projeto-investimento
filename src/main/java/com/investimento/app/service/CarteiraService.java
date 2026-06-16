package com.investimento.app.service;

import com.investimento.app.dto.request.CarteiraRequestDTO;
import com.investimento.app.dto.update.CarteiraUpdateDTO;
import com.investimento.app.model.Carteira;
import com.investimento.app.model.CarteiraAtivo;
import com.investimento.app.model.Usuario;
import com.investimento.app.repository.CarteiraRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CarteiraService {

    private final CarteiraRepository carteiraRepository;
    private final UsuarioService usuarioService;
    private final ModelMapper mapper;
    private final AuthenticationService auth;
    
    
    /*Na nossa arquitetura a carteira inicia vazia e depois vamos associando ativos a ela*/
    @Transactional
    public Carteira criar(CarteiraRequestDTO requestDTO) {
        Long id = auth.getUsuarioIdLogado();
        
        System.out.println(id);
        
        Usuario usuario = usuarioService.buscarPorId(id);
        if (usuario == null) {
            throw new IllegalArgumentException("Deve ter um usuário cadastrado com este ID.");
        }
        
        Carteira carteira = mapper.map(requestDTO, Carteira.class);       
        carteira.setUsuario(usuario);
        carteira.setAtivo(true);

        return carteiraRepository.save(carteira);
    }

    @Transactional(readOnly = true)
    public Carteira buscarPorId(Long idCarteira) {
    	Long id = auth.getUsuarioIdLogado();
    	System.out.println("Id Token: " + id);
        return carteiraRepository.findByIdAndUsuarioId (id, idCarteira)
                .orElseThrow(() -> new EntityNotFoundException("Carteira não encontrada com o ID: " + idCarteira ));
    }

    @Transactional(readOnly = true)
    public Page<Carteira> listarTodos(Pageable pageable) {
        return carteiraRepository.findByUsuarioId(auth.getUsuarioIdLogado(), pageable);
    }

    @Transactional
    public Carteira atualizar(Long id, CarteiraUpdateDTO updateDTO) {
        Carteira carteiraExistente = buscarPorId(id);

        updateDTO.getNome().ifPresent(carteiraExistente::setNome);
        updateDTO.getDescricao().ifPresent(carteiraExistente::setDescricao);

        return carteiraRepository.save(carteiraExistente);
    }

    @Transactional
    public void deletar(Long id) {
        Carteira carteira = buscarPorId(id);
        carteiraRepository.delete(carteira);
    }
    
    @Transactional
    public void ativar(Long id)
    {
    	Carteira carteira = buscarPorId(id);
    	carteira.setAtivo(true);
    	carteiraRepository.save(carteira);
    }
    
    
    @Transactional
    public void desativar(Long id)
    {
    	Carteira carteira = buscarPorId(id);
    	carteira.setAtivo(false);
    	carteiraRepository.save(carteira);
    }
    
}