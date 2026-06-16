package com.investimento.app.service.calculo;

import com.investimento.app.model.Transacao;

public interface ProcessadorAtivo {
	void processar(Transacao transacao);
}
