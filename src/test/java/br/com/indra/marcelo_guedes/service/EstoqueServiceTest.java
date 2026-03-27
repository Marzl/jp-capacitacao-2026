package br.com.indra.marcelo_guedes.service;

import br.com.indra.marcelo_guedes.exceptions.BusinessException;
import br.com.indra.marcelo_guedes.model.MovimentacaoEstoque;
import br.com.indra.marcelo_guedes.model.Produtos;
import br.com.indra.marcelo_guedes.repository.MovimentacaoEstoqueRepository;
import br.com.indra.marcelo_guedes.repository.ProdutosRepository;
import br.com.indra.marcelo_guedes.service.dto.EstoqueResponseDTO;
import br.com.indra.marcelo_guedes.service.dto.MovimentacaoEstoqueRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstoqueServiceTest {

    @Mock
    private ProdutosRepository produtosRepository;

    @Mock
    private MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;

    @InjectMocks
    private EstoqueService estoqueService;

    @Test
    void AdicionarEntradaEstoque() {

        Produtos produto = new Produtos();
        produto.setId(1L);
        produto.setNome("Iphone 12");
        produto.setQuantidadeEstoque(5);
        produto.setEstoqueMinimo(2);
        produto.setAtivo(true);

        MovimentacaoEstoqueRequestDTO dto = new MovimentacaoEstoqueRequestDTO();
        dto.setQuantidade(3);

        Produtos produtoSalvo = new Produtos();
        produtoSalvo.setId(1L);
        produtoSalvo.setNome("Iphone 12");
        produtoSalvo.setQuantidadeEstoque(8);
        produtoSalvo.setEstoqueMinimo(2);
        produtoSalvo.setAtivo(true);

        when(produtosRepository.findByIdAndAtivoTrue(1L))
                .thenReturn(Optional.of(produto));

        when(produtosRepository.save(any(Produtos.class)))
                .thenReturn(produtoSalvo);

        EstoqueResponseDTO response = estoqueService.adicionarEstoque(1L, dto);

        assertNotNull(response);
        assertEquals(8, response.getQuantidadeEstoque());
        assertEquals(2, response.getEstoqueMinimo());
        assertEquals(false, response.getEstoqueBaixo());

        verify(produtosRepository).save(any(Produtos.class));
        verify(movimentacaoEstoqueRepository).save(any(MovimentacaoEstoque.class));

    }

    @Test
    void naoPermitirSaidaEstoqueInsuficiente() {

        Produtos produto = new Produtos();
        produto.setId(1L);
        produto.setNome("Iphone 712");
        produto.setQuantidadeEstoque(7);
        produto.setEstoqueMinimo(4);
        produto.setAtivo(true);

        MovimentacaoEstoqueRequestDTO dto = new MovimentacaoEstoqueRequestDTO();
        dto.setQuantidade(9);

        when(produtosRepository.findByIdAndAtivoTrue(1L))
                .thenReturn(Optional.of(produto));

        assertThrows(BusinessException.class,
                () -> estoqueService.removerEstoque(1L, dto));

        verify(produtosRepository, never()).save(any());
        verify(movimentacaoEstoqueRepository, never()).save(any());

    }

}
