package br.com.indra.marcelo_guedes.service;

import br.com.indra.marcelo_guedes.exceptions.BusinessException;
import br.com.indra.marcelo_guedes.exceptions.ResourceNotFoundException;
import br.com.indra.marcelo_guedes.model.MovimentacaoEstoque;
import br.com.indra.marcelo_guedes.model.Produtos;
import br.com.indra.marcelo_guedes.model.enums.TipoMovimentacaoEstoque;
import br.com.indra.marcelo_guedes.repository.MovimentacaoEstoqueRepository;
import br.com.indra.marcelo_guedes.repository.ProdutosRepository;
import br.com.indra.marcelo_guedes.service.dto.EstoqueResponseDTO;
import br.com.indra.marcelo_guedes.service.dto.MovimentacaoEstoqueRequestDTO;
import br.com.indra.marcelo_guedes.service.dto.MovimentacaoEstoqueResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstoqueService {

    private final ProdutosRepository produtosRepository;
    private final MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;

    public EstoqueResponseDTO adicionarEstoque(Long produtoId, MovimentacaoEstoqueRequestDTO dto) {

        Produtos produto = produtosRepository.findByIdAndAtivoTrue(produtoId)
                .orElseThrow(() -> new ResourceNotFoundException("produto não encontrado"));

        if(dto.getQuantidade() == null || dto.getQuantidade() <= 0) {
            throw new BusinessException("a quantidade a ser adicionada tem que ser maior que zero");
        }

        Integer antigaQuantidade = produto.getQuantidadeEstoque();

        Integer novaQuantidade = produto.getQuantidadeEstoque() + dto.getQuantidade();

        produto.setQuantidadeEstoque(novaQuantidade);

        MovimentacaoEstoque movimentacaoEstoque = new MovimentacaoEstoque();
        movimentacaoEstoque.setProduto(produto);
        movimentacaoEstoque.setTipoMovimentacao(TipoMovimentacaoEstoque.ENTRADA);
        movimentacaoEstoque.setQuantidade(dto.getQuantidade());
        movimentacaoEstoque.setEstoqueAntes(antigaQuantidade);
        movimentacaoEstoque.setEstoqueDepois(novaQuantidade);

        Produtos produtoAtualizado = produtosRepository.save(produto);
        movimentacaoEstoqueRepository.save(movimentacaoEstoque);

        return toResponseDTO(produtoAtualizado);

    }

    public EstoqueResponseDTO consultarEstoque(Long produtoId) {

        Produtos produto = produtosRepository.findByIdAndAtivoTrue(produtoId)
                .orElseThrow(() -> new ResourceNotFoundException("produto não encontrado"));

        return toResponseDTO(produto);
    }

    public List<MovimentacaoEstoqueResponseDTO> listarMovimentacoes(Long produtoId) {

       produtosRepository.findByIdAndAtivoTrue(produtoId)
               .orElseThrow(() -> new ResourceNotFoundException("produto não encontrado"));

        return movimentacaoEstoqueRepository.findByProdutoId(produtoId)
                .stream()
                .map(this::toMovimentacaoResponseDTO)
                .toList();
    }

    public EstoqueResponseDTO removerEstoque(Long produtoId, MovimentacaoEstoqueRequestDTO dto) {

        Produtos produto = produtosRepository.findByIdAndAtivoTrue(produtoId)
                .orElseThrow(() -> new ResourceNotFoundException("produto não encontrado"));

        if(dto.getQuantidade() == null || dto.getQuantidade() <= 0) {
            throw new BusinessException("a quantidade ser removida tem que ser maior que zero");
        }

        Integer novaQuantidade = produto.getQuantidadeEstoque() - dto.getQuantidade();

        if (novaQuantidade < 0) {
            throw new BusinessException("não possui estoque suficiente para remover");
        }

        Integer antigaQuantidade = produto.getQuantidadeEstoque();

        produto.setQuantidadeEstoque(novaQuantidade);

        MovimentacaoEstoque movimentacaoEstoque = new MovimentacaoEstoque();
        movimentacaoEstoque.setProduto(produto);
        movimentacaoEstoque.setTipoMovimentacao(TipoMovimentacaoEstoque.SAIDA);
        movimentacaoEstoque.setQuantidade(dto.getQuantidade());
        movimentacaoEstoque.setEstoqueAntes(antigaQuantidade);
        movimentacaoEstoque.setEstoqueDepois(novaQuantidade);

        Produtos produtoAtualizado = produtosRepository.save(produto);
        movimentacaoEstoqueRepository.save(movimentacaoEstoque);

        return toResponseDTO(produtoAtualizado);

    }

    private EstoqueResponseDTO toResponseDTO(Produtos produto) {

        Boolean estoqueBaixo = false;

        if (produto.getQuantidadeEstoque() <= produto.getEstoqueMinimo()){
            estoqueBaixo = true;
        }

        return EstoqueResponseDTO.builder()
                .produtoId(produto.getId())
                .nomeProduto(produto.getNome())
                .quantidadeEstoque(produto.getQuantidadeEstoque())
                .estoqueMinimo(produto.getEstoqueMinimo())
                .estoqueBaixo(estoqueBaixo)
                .build();
    }

    private MovimentacaoEstoqueResponseDTO toMovimentacaoResponseDTO(MovimentacaoEstoque movimentacaoEstoque) {
        return MovimentacaoEstoqueResponseDTO.builder()
                .id(movimentacaoEstoque.getId())
                .produtoId(movimentacaoEstoque.getProduto().getId())
                .produtoNome(movimentacaoEstoque.getProduto().getNome())
                .tipoMovimentacao(movimentacaoEstoque.getTipoMovimentacao())
                .quantidade(movimentacaoEstoque.getQuantidade())
                .estoqueAntes(movimentacaoEstoque.getEstoqueAntes())
                .estoqueDepois(movimentacaoEstoque.getEstoqueDepois())
                .dataMovimentacao(movimentacaoEstoque.getDataMovimentacao())
                .build();
    }
}
