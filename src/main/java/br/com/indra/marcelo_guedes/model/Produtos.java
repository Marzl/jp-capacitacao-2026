package br.com.indra.marcelo_guedes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "produtos")
public class Produtos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "preco", nullable = false)
    private BigDecimal preco;

    @Column(name="codigo_barras")
    private String codigoBarras;

    @Column(name="quantidade_estoque")
    private Integer quantidadeEstoque = 0;

    @Column(name="estoque_minimo")
    private Integer estoqueMinimo = 0;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categorias categoria;

}