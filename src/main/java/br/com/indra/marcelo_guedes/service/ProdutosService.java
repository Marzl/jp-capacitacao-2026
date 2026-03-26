package br.com.indra.marcelo_guedes.service;

import br.com.indra.marcelo_guedes.exceptions.ResourceNotFoundException;
import br.com.indra.marcelo_guedes.model.HistoricoPreco;
import br.com.indra.marcelo_guedes.model.Produtos;
import br.com.indra.marcelo_guedes.repository.HistoricoPrecoRepository;
import br.com.indra.marcelo_guedes.repository.ProdutosRepository;
import br.com.indra.marcelo_guedes.service.dto.ProdutosRequestDTO;
import br.com.indra.marcelo_guedes.service.dto.ProdutosResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutosService {

    private final ProdutosRepository produtosRepository;
    private final HistoricoPrecoRepository historicoPrecoRepository;

    public ProdutosResponseDTO criarProduto(ProdutosRequestDTO dto) {

        Produtos produtoSalvo = produtosRepository.save(dto);

        return toResponseDTO(produtoSalvo);
    }

    public List<ProdutosResponseDTO> listarProdutos() {
        return produtosRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public ProdutosResponseDTO buscarProduto(Long id) {
        Produtos produtoBuscado = produtosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        return toResponseDTO(produtoBuscado);
    }

    public ProdutosResponseDTO atualizarProdutos(ProdutosRequestDTO dto) {

        Produtos produtoAtualizado = produtosRepository.save(dto);

        return toResponseDTO(produtoAtualizado);
    }

    public ProdutosResponseDTO atualizarPreco(Long id, BigDecimal preco) {
//        Produtos produto = produtosRepository.findById(id).get();
        final var produto = produtosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
        produto.setPreco(preco);
        /***
         * Rastreabilidade
         * 1 - Criar um log
         * 2 - Adicionar em tabela historico de preços valores old e new
         * para cada produto atualizado
         * 3 - Antes de atualizar a tabela de produto, pegar o valor atual da tabela e adiconar
         * na tabela historico
         * 4 - Pegar novo valor da tabela e adicionar na tabela historico
         * 5 - Sempre na tabela, adicionar novo registro após atualizar tabela de produto
         * Estrutura da tabela historico de preços
         * id
         * id_produto
         * preco_antigo
         * preco_novo
         * data_alteracao
         */
        final var historico = new HistoricoPreco();
        historico.setPrecoAntigo(produto.getPreco());
        historico.setProdutos(produto);
        historico.setPrecoNovo(preco);
        //Código abaixo pode ser substituido por @CreationTimestamp
//        historico.setDataAlteracao(LocalDateTime.now());
        historicoPrecoRepository.save(historico);
        return produtosRepository.saveAndFlush(produto);

        //Exemplo de não se fazer por gerar retrabalho
//        final var historicoNovo = historicoPrecoRepository.findById(historico.getId()).get();
//        historicoNovo.setPrecoNovo(preco);
//        historicoPrecoRepository.save(historicoNovo);
        /**
         * get na tabela produtos para novo preço
         */
//        return produto;
    }

    public void deletarProduto(Long id) {
        produtosRepository.deleteById(id);
    }

    private ProdutosResponseDTO toResponseDTO(Produtos produto) {

        return ProdutosResponseDTO.builder()
                .id(produto.getId())
                .nome(produto.getNome())
                .descricao(produto.getDescricao())
                .preco(produto.getPreco())
                .codigoBarras(produto.getCodigoBarras())
                .categoriaId(produto.getCategoria().getId())
                .categoriaNome(produto.getCategoria().getNome())
                .build();
    }

}

