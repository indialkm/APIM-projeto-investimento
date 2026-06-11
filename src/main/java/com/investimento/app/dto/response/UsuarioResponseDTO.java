package com.investimento.app.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import com.investimento.app.model.enums.Roles;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {
	
	private Long id;
    private String nome;
    private String email;
    private boolean ativo;
    private List<Roles> roles;

}
