package com.investimento.app.dto.update;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtivoUpdateDTO {

    private Optional<String> ticker = Optional.empty();
    private Optional<String> nome = Optional.empty();
    private Optional<String> tipoAtivo = Optional.empty();
    private Optional<Boolean> ativo = Optional.empty();
}
