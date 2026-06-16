package com.investimento.app.model;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "carteira_ativo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarteiraAtivo {

    @EmbeddedId
    private CarteiraAtivoId id = new CarteiraAtivoId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("carteiraId")
    @JoinColumn(name = "carteira_id")
    private Carteira carteira;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ativoId")
    @JoinColumn(name = "ativo_id")
    @ToString.Exclude            
    @EqualsAndHashCode.Exclude 
    private Ativo ativo;

    @Column(nullable = false, precision = 18, scale = 4)
    private BigDecimal quantidade;

    @Column(name = "preco_entrada", nullable = false, precision = 18, scale = 4)
    private BigDecimal precoEntrada;
    
    public BigDecimal getValorTotalInvestido() {
        if (this.quantidade != null && this.precoEntrada != null) {
            return this.quantidade.multiply(this.precoEntrada)
                       .setScale(2, RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO;
    }
}