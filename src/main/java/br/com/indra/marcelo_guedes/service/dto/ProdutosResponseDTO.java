package br.com.indra.marcelo_guedes.service.dto;

import br.com.indra.marcelo_guedes.model.Categorias;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProdutosResponseDTO {

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String codigoBarras;
    private Integer estoqueMinimo;
    private Long categoriaId;
    private String categoriaNome;


}
