package com.investimento.app.dto.update;

import java.util.Optional;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioUpdateDTO {
	
	private Optional<String> nome = Optional.empty();
    private Optional<String> email = Optional.empty();
    private Optional<String> senha = Optional.empty();

}
