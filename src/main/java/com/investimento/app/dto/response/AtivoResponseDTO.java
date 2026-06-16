package com.investimento.app.dto.response;

import java.util.List;

import com.investimento.app.model.enums.TipoAtivo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtivoResponseDTO {

    private Long id;
    private String ticker;
    private String nome;
    private TipoAtivo tipoAtivo;
    private Boolean status;;
    private List<HistoricoResponseDTO> historicos;
}