package com.investimento.app.dto.update;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarteiraUpdateDTO {

    private Optional<String> nome = Optional.empty();
    private Optional<String> descricao = Optional.empty();
   
}