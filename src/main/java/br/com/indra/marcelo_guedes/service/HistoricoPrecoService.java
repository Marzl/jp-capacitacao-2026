package br.com.indra.marcelo_guedes.service;

import br.com.indra.marcelo_guedes.model.HistoricoPreco;
import br.com.indra.marcelo_guedes.repository.HistoricoPrecoRepository;
import br.com.indra.marcelo_guedes.service.dto.HistoricoPrecoResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoricoPrecoService {

    private final HistoricoPrecoRepository historicoPrecoRepository;

    public List<HistoricoPrecoResponseDTO> listarHistoricoPreco(Long produtoId) {

        List<HistoricoPreco> historicoPrecos = historicoPrecoRepository.findByProdutosId(produtoId);

        return historicoPrecos
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    private HistoricoPrecoResponseDTO toResponseDTO(HistoricoPreco historicoPreco) {

        return HistoricoPrecoResponseDTO.builder()
                .id(historicoPreco.getId())
                .produto(historicoPreco.getProduto().getNome())
                .precoAntigo(historicoPreco.getPrecoAntigo())
                .precoNovo(historicoPreco.getPrecoNovo())
                .dataAlteracao(historicoPreco.getDataAlteracao())
                .build();

    }

}