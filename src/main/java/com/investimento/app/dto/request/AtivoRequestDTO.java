package com.investimento.app.dto.request;

import com.investimento.app.model.enums.TipoAtivo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtivoRequestDTO {

    @NotBlank(message = "O ticker do ativo é obrigatório.")
    @Size(min = 4, max = 20, message = "O ticker deve ter entre 4 e 20 caracteres.")
    private String ticker;

    @NotBlank(message = "O nome do ativo é obrigatório.")
    private String nome;

    @NotNull(message = "O tipo do ativo é obrigatório.")
    private TipoAtivo tipoAtivo;
}