package com.investimento.app.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarteiraRequestDTO {

    @NotBlank(message = "O nome da carteira é obrigatório.")
    private String nome;
    private String descricao;
}