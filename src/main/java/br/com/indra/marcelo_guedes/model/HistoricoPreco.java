package br.com.indra.marcelo_guedes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "historico_preco")
public class HistoricoPreco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produtos produto;

    @Column(name = "preco_antigo")
    private BigDecimal precoAntigo;

    @Column(name = "preco_novo")
    private BigDecimal precoNovo;

    @CreationTimestamp
    @Column(name = "data_alteracao", updatable = false)
    private LocalDateTime dataAlteracao;

}
