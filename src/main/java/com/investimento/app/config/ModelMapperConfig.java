package com.investimento.app.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.investimento.app.dto.response.CarteiraAtivoResponseDTO;
import com.investimento.app.dto.response.CarteiraResponseDTO;
import com.investimento.app.dto.response.TransacaoResponseDTO;
import com.investimento.app.model.Carteira;
import com.investimento.app.model.CarteiraAtivo;
import com.investimento.app.model.Transacao;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        
       
        modelMapper.getConfiguration()
                   .setMatchingStrategy(MatchingStrategies.STRICT);
        
        modelMapper.typeMap(Carteira.class, CarteiraResponseDTO.class).addMappings(mapper -> {
            mapper.map(src -> src.getUsuario().getId(), CarteiraResponseDTO::setUsuarioId);
        });
        
        modelMapper.typeMap(CarteiraAtivo.class, CarteiraAtivoResponseDTO.class).addMappings(mapper -> {
            mapper.map(src -> src.getAtivo().getId(), CarteiraAtivoResponseDTO::setAtivoId);
            mapper.map(src -> src.getAtivo().getNome(), CarteiraAtivoResponseDTO::setNomeAtivo);
            mapper.map(src -> src.getAtivo().getTipoAtivo(), CarteiraAtivoResponseDTO::setTipoAtivo);
        });
        
        modelMapper.typeMap(Transacao.class, TransacaoResponseDTO.class).addMappings(mapper -> {
            mapper.map(src -> src.getCarteira().getId(), TransacaoResponseDTO::setCarteiraId);
            mapper.map(src -> src.getAtivo().getId(), TransacaoResponseDTO::setAtivoId);
            mapper.map(src -> src.getAtivo().getTicker(), TransacaoResponseDTO::setAtivoTicker);
          
        });
                   
        return modelMapper;
    }
}