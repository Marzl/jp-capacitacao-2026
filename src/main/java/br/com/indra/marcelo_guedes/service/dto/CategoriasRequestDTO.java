package br.com.indra.marcelo_guedes.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoriasRequestDTO {

    private String nome;
    private Long categoriaPaiId;

}
