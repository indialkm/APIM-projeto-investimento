package com.rentabilidade_service.app_investimento.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_historico_rendimento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Historico {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "ticker_ativo", nullable = false)
    private String tickerAtivo; 

    @Column(name = "preco_fechamento", nullable = false, precision = 18, scale = 2)
    private BigDecimal precoFechamento;

    @Column(nullable = false)
    private LocalDateTime data;

    @Column(name = "acumulo_inflacao", precision = 5, scale = 4)
    private BigDecimal acumuloInflacao;
}
