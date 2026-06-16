package com.investimento.app.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarteiraResponseDTO {

    private Long id;
    private Long usuarioId;
    private String nome;
    private String descricao;
    private boolean ativo;
    private List<TransacaoResponseDTO> transacoes;
    private List<CarteiraAtivoResponseDTO> ativos;
}