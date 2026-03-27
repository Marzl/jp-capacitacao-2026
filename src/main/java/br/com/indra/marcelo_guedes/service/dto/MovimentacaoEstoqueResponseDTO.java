package br.com.indra.marcelo_guedes.service.dto;

import br.com.indra.marcelo_guedes.model.enums.TipoMovimentacaoEstoque;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovimentacaoEstoqueResponseDTO {

    private Long id;
    private Long produtoId;
    private String produtoNome;
    private TipoMovimentacaoEstoque tipoMovimentacao;
    private Integer quantidade;
    private Integer estoqueAntes;
    private Integer estoqueDepois;
    private LocalDateTime dataMovimentacao;

}
