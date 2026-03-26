package br.com.indra.marcelo_guedes.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutosRequestDTO {

    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String codigoBarras;
    private Long categoriaId;

}
