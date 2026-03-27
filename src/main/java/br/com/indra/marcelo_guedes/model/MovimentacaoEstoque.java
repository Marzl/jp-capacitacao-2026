package br.com.indra.marcelo_guedes.model;

import br.com.indra.marcelo_guedes.model.enums.TipoMovimentacaoEstoque;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movimentacao_estoque")
public class MovimentacaoEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produtos produto;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimentacao", nullable = false)
    private TipoMovimentacaoEstoque tipoMovimentacao;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @Column(name = "estoque_antes", nullable = false)
    private Integer estoqueAntes;

    @Column(name = "estoque_depois", nullable = false)
    private Integer estoqueDepois;

    @CreationTimestamp
    @Column(name = "data_movimentacao", nullable = false)
    private LocalDateTime dataMovimentacao;



}
