package br.com.indra.marcelo_guedes.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoriasResponseDTO {

    private Long id;
    private String nome;
    private Long categoriaPaiId;
    private String categoriaPaiNome;

}
