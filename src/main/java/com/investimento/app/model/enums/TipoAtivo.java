package com.investimento.app.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TipoAtivo {
	RENDA_FIXA,
    RENDA_VARIAVEL;
    
    @JsonCreator
    public static TipoAtivo deString(String valor) {
        if (valor == null) return null;
        
        try {
          
            return TipoAtivo.valueOf(valor.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("O tipo '" + valor + "' não é aceito. Escolha RENDA_FIXA ou RENDA_VARIAVEL.");
        }
    }
}
