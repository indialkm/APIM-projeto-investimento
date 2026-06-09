package com.investimento.app.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarteiraAtivoId implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long carteiraId;
    private Long ativoId;
}