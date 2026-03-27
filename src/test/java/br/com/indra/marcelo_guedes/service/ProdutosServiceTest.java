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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutosServiceTest {

    @Mock
    private ProdutosRepository produtosRepository;

    @Mock
    private HistoricoPrecoRepository historicoPrecoRepository;

    @Mock
    private CategoriasRepository categoriasRepository;

    @InjectMocks
    private ProdutosService produtosService;

    @Test
    void CriarProduto() {

        ProdutosRequestDTO dto = new ProdutosRequestDTO();
        dto.setNome("Iphone 15");
        dto.setDescricao("16gb");
        dto.setPreco(new BigDecimal("1500.00"));
        dto.setCodigoBarras("GR389JFGH");
        dto.setCategoriaId(1L);
        dto.setEstoqueMinimo(5);

        Categorias categoria = new Categorias();
        categoria.setId(1L);
        categoria.setNome("Celular");
        categoria.setAtivo(true);

        Produtos produtoSalvo = new Produtos();
        produtoSalvo.setId(1L);
        produtoSalvo.setNome(dto.getNome());
        produtoSalvo.setDescricao(dto.getDescricao());
        produtoSalvo.setPreco(dto.getPreco());
        produtoSalvo.setCodigoBarras(dto.getCodigoBarras());
        produtoSalvo.setCategoria(categoria);
        produtoSalvo.setEstoqueMinimo(dto.getEstoqueMinimo());
        produtoSalvo.setAtivo(true);

        when(categoriasRepository.findByIdAndAtivoTrue(1L))
                .thenReturn(Optional.of(categoria));

        when(produtosRepository.save(any(Produtos.class)))
                .thenReturn(produtoSalvo);

        ProdutosResponseDTO response = produtosService.criarProduto(dto);

        assertNotNull(response);
        assertEquals("Iphone 15", response.getNome());
        assertEquals(new BigDecimal("1500.00"), response.getPreco());
        assertEquals(5, response.getEstoqueMinimo());

        verify(categoriasRepository).findByIdAndAtivoTrue(1L);
        verify(produtosRepository).save(any(Produtos.class));

    }

    @Test
    void naoCriarProdutoComCategoriaInexistente() {

        ProdutosRequestDTO dto = new ProdutosRequestDTO();
        dto.setNome("Iphone 15");
        dto.setDescricao("16gb");
        dto.setPreco(new BigDecimal("1500.00"));
        dto.setCodigoBarras("AHWI2O8F93");
        dto.setCategoriaId(1L);
        dto.setEstoqueMinimo(5);

        when(categoriasRepository.findByIdAndAtivoTrue(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> produtosService.criarProduto(dto));

        verify(produtosRepository, never()).save(any());

    }

    @Test
    void naoCriarProdutoSemEstoqueMinimo() {

        ProdutosRequestDTO dto = new ProdutosRequestDTO();
        dto.setNome("Iphone 16");
        dto.setDescricao("32gb");
        dto.setPreco(new BigDecimal("1500.00"));
        dto.setCodigoBarras("I3OA98DFG");
        dto.setCategoriaId(1L);
        dto.setEstoqueMinimo(null);

        assertThrows(BusinessException.class,
                () -> produtosService.criarProduto(dto));

        verify(categoriasRepository, never()).findByIdAndAtivoTrue(any());
        verify(produtosRepository, never()).save(any());

    }

    @Test
    void InativarProduto() {

        Produtos produto = new Produtos();
        produto.setId(1L);
        produto.setNome("Iphone 12");
        produto.setAtivo(true);

        when(produtosRepository.findByIdAndAtivoTrue(1L))
                .thenReturn(Optional.of(produto));

        produtosService.deletarProduto(1L);

        assertFalse(produto.getAtivo());
        verify(produtosRepository).save(produto);

    }

    @Test
    void criarHistoricoAoAtualizarPreco() {

        Categorias categoria = new Categorias();
        categoria.setId(1L);
        categoria.setNome("Celulares");
        categoria.setAtivo(true);

        Produtos produto = new Produtos();
        produto.setId(1L);
        produto.setNome("Iphone 13");
        produto.setPreco(new BigDecimal("1500.00"));
        produto.setCategoria(categoria);
        produto.setCodigoBarras("GGOOPE3024");
        produto.setEstoqueMinimo(5);
        produto.setAtivo(true);

        Produtos produtoSalvo = new Produtos();
        produtoSalvo.setId(1L);
        produtoSalvo.setNome("Iphone 13");
        produtoSalvo.setPreco(new BigDecimal("2000.00"));
        produtoSalvo.setCategoria(categoria);
        produtoSalvo.setCodigoBarras("GGOOPE3024");
        produtoSalvo.setEstoqueMinimo(5);
        produtoSalvo.setAtivo(true);

        when(produtosRepository.findById(1L))
                .thenReturn(Optional.of(produto));

        when(produtosRepository.save(any(Produtos.class)))
                .thenReturn(produtoSalvo);

        ProdutosResponseDTO response =
                produtosService.atualizarPreco(1L, new BigDecimal("2000.00"));

        assertNotNull(response);
        assertEquals(new BigDecimal("2000.00"), response.getPreco());
        assertEquals("Iphone 13", response.getNome());

        verify(historicoPrecoRepository).save(any(HistoricoPreco.class));
        verify(produtosRepository).save(any(Produtos.class));

    }

    @Test
    void naoAtualizarPrecoComValorInvalido() {

        Produtos produto = new Produtos();
        produto.setId(1L);
        produto.setNome("Iphone XR");
        produto.setPreco(new BigDecimal("900.00"));
        produto.setAtivo(true);

        when(produtosRepository.findById(1L))
                .thenReturn(Optional.of(produto));

        assertThrows(BusinessException.class,
                () -> produtosService.atualizarPreco(1L, BigDecimal.ZERO));

        verify(historicoPrecoRepository, never()).save(any());
        verify(produtosRepository, never()).save(any());

    }

}
