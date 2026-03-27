package br.com.indra.marcelo_guedes.service;

import br.com.indra.marcelo_guedes.exceptions.ResourceNotFoundException;
import br.com.indra.marcelo_guedes.model.HistoricoPreco;
import br.com.indra.marcelo_guedes.repository.HistoricoPrecoRepository;
import br.com.indra.marcelo_guedes.repository.ProdutosRepository;
import br.com.indra.marcelo_guedes.service.dto.HistoricoPrecoResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoricoPrecoService {

    private final HistoricoPrecoRepository historicoPrecoRepository;
    private final ProdutosRepository produtosRepository;

    public List<HistoricoPrecoResponseDTO> listarHistoricoPreco(Long produtoId) {

        produtosRepository.findByIdAndAtivoTrue(produtoId)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        List<HistoricoPreco> historicoPrecos = historicoPrecoRepository.findByProdutoId(produtoId);

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