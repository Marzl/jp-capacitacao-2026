package br.com.indra.marcelo_guedes.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HistoricoPrecoResponseDTO {

    private Long id;
    private String produto;
    private BigDecimal precoAntigo;
    private BigDecimal precoNovo;
    private LocalDateTime dataAlteracao;

}
