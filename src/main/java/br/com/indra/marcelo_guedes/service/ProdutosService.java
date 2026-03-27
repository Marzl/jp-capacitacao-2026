package br.com.indra.marcelo_guedes.service;

import br.com.indra.marcelo_guedes.exceptions.BusinessException;
import br.com.indra.marcelo_guedes.exceptions.ResourceNotFoundException;
import br.com.indra.marcelo_guedes.model.Categorias;
import br.com.indra.marcelo_guedes.model.HistoricoPreco;
import br.com.indra.marcelo_guedes.model.Produtos;
import br.com.indra.marcelo_guedes.repository.CategoriasRepository;
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
    private final CategoriasRepository categoriasRepository;

    public ProdutosResponseDTO criarProduto(ProdutosRequestDTO dto) {

        if(dto.getNome() == null || dto.getNome().isBlank()){
            throw new BusinessException("o nome do produto é obrigatório");
        }

        if(dto.getPreco() == null) {
            throw new BusinessException("o preço do produto é obrigatório");
        }

        if(dto.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("o preço nao pode ser menor que 0");
        }

        if (dto.getEstoqueMinimo() == null) {
            throw new BusinessException("o estoque mínimo do produto é obrigatório");
        }

        if (dto.getEstoqueMinimo() < 0) {
            throw new BusinessException("o estoque mínimo não pode ser negativo");
        }

        Categorias categoriaId = categoriasRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria nao encontrada"));

        Produtos produto = new Produtos();
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setCodigoBarras(dto.getCodigoBarras());
        produto.setEstoqueMinimo(dto.getEstoqueMinimo());
        produto.setCategoria(categoriaId);

        Produtos produtoSalvo = produtosRepository.save(produto);

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

    public ProdutosResponseDTO atualizarProduto(Long id, ProdutosRequestDTO dto) {

        Produtos produtoExiste = produtosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        if(dto.getNome() == null || dto.getNome().isBlank()){
            throw new BusinessException("o nome do produto é obrigatório");
        }

        if(dto.getPreco() == null) {
            throw new BusinessException("o preço do produto é obrigatório");
        }

        if(dto.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("o preço nao pode ser menor que 0");
        }

        if (dto.getEstoqueMinimo() == null) {
            throw new BusinessException("o estoque mínimo do produto é obrigatório");
        }

        if (dto.getEstoqueMinimo() < 0) {
            throw new BusinessException("o estoque mínimo não pode ser negativo");
        }

        if(dto.getCategoriaId() == null) {
            throw new BusinessException("a categoria do produto é obrigatória");
        }

        Categorias categoriaExiste = categoriasRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        produtoExiste.setNome(dto.getNome());
        produtoExiste.setDescricao(dto.getDescricao());
        produtoExiste.setPreco(dto.getPreco());
        produtoExiste.setCodigoBarras(dto.getCodigoBarras());
        produtoExiste.setEstoqueMinimo(dto.getEstoqueMinimo());
        produtoExiste.setCategoria(categoriaExiste);

        Produtos produtoAtualizado = produtosRepository.save(produtoExiste);

        return toResponseDTO(produtoAtualizado);
    }

    public ProdutosResponseDTO atualizarPreco(Long id, BigDecimal preco) {

        Produtos produto = produtosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        if(preco == null) {
            throw new BusinessException("o preço do produto é obrigatório");
        }

        if(preco.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("o preço deve ser maior que zero");
        }

        BigDecimal precoAntigo = produto.getPreco();

        if(precoAntigo.compareTo(preco) == 0) {
            throw new BusinessException("o novo preço deve ser diferente do preço atual");
        }

        produto.setPreco(preco);

        HistoricoPreco historico = new HistoricoPreco();
        historico.setPrecoAntigo(precoAntigo);
        historico.setProduto(produto);
        historico.setPrecoNovo(preco);

        Produtos produtoAtualizado = produtosRepository.save(produto);
        historicoPrecoRepository.save(historico);

        return toResponseDTO(produtoAtualizado);
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
                .estoqueMinimo(produto.getEstoqueMinimo())
                .categoriaId(produto.getCategoria().getId())
                .categoriaNome(produto.getCategoria().getNome())
                .build();
    }

}

