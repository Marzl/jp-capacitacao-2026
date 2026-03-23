package br.com.indra.marcelo_guedes.service;

import br.com.indra.marcelo_guedes.model.HistoricoPreco;
import br.com.indra.marcelo_guedes.repository.HistoricoPrecoRepository;
import br.com.indra.marcelo_guedes.service.dto.HistoricoProdutoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class HistoricoService {

    private final HistoricoPrecoRepository historicoPrecoRepository;

    public HistoricoProdutoDTO getHistoricoByProdutoId(Long produtoId) {
        Set<HistoricoPreco> historicoPrecos = historicoPrecoRepository.findByProdutosId(produtoId)
                .stream().flatMap(/*desenvolveria o mapeamento*/).toList();
        /**
         * Existe várias forma de se mappear, map, flatmap, for, stream, mapperstruct, projection
         */
        return new HistoricoProdutoDTO(
                historicoPrecos.getId(),
                historicoPrecos.getProdutos().getNome(),
                historicoPrecos.getPrecoAntigo(),
                historicoPrecos.getPrecoNovo(),
                historicoPrecos.getDataAlteracao()
        );


    }
}