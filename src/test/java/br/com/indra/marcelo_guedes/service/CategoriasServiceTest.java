package br.com.indra.marcelo_guedes.service;

import br.com.indra.marcelo_guedes.exceptions.BusinessException;
import br.com.indra.marcelo_guedes.exceptions.ResourceNotFoundException;
import br.com.indra.marcelo_guedes.model.Categorias;
import br.com.indra.marcelo_guedes.repository.CategoriasRepository;
import br.com.indra.marcelo_guedes.repository.ProdutosRepository;
import br.com.indra.marcelo_guedes.service.dto.CategoriasRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriasServiceTest {

    @Mock
    private CategoriasRepository categoriasRepository;

    @Mock
    private ProdutosRepository produtosRepository;

    @InjectMocks
    private CategoriasService categoriasService;

    @Test
    void inativarCategoria() {

        Categorias categoria = new Categorias();
        categoria.setId(1L);
        categoria.setNome("Celular");
        categoria.setAtivo(true);

        when(categoriasRepository.findByIdAndAtivoTrue(1L))
                .thenReturn(Optional.of(categoria));

        when(categoriasRepository.existsByCategoriaPaiIdAndAtivoTrue(1L))
                .thenReturn(false);

        when(produtosRepository.existsByCategoriaIdAndAtivoTrue(1L))
                .thenReturn(false);

        categoriasService.deletarCategoria(1L);

        assertFalse(categoria.getAtivo());
        verify(categoriasRepository).save(categoria);

    }

    @Test
    void naoDeletarCategoriaInexistente() {

        when(categoriasRepository.findByIdAndAtivoTrue(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> categoriasService.deletarCategoria(1L));

        verify(categoriasRepository, never()).save(any());

    }

    @Test
    void naoInativarCategoriaComSubcategorias() {

        Categorias categoria = new Categorias();
        categoria.setId(1L);
        categoria.setNome("Celular");
        categoria.setAtivo(true);

        when(categoriasRepository.findByIdAndAtivoTrue(1L))
                .thenReturn(Optional.of(categoria));

        when(categoriasRepository.existsByCategoriaPaiIdAndAtivoTrue(1L))
                .thenReturn(true);

        assertThrows(BusinessException.class,
                () -> categoriasService.deletarCategoria(1L));

        verify(categoriasRepository, never()).save(any());

    }

    @Test
    void naoInativarCategoriaComProdutos() {

        Categorias categoria = new Categorias();
        categoria.setId(1L);
        categoria.setNome("Celular");
        categoria.setAtivo(true);

        when(categoriasRepository.findByIdAndAtivoTrue(1L))
                .thenReturn(Optional.of(categoria));

        when(categoriasRepository.existsByCategoriaPaiIdAndAtivoTrue(1L))
                .thenReturn(false);

        when(produtosRepository.existsByCategoriaIdAndAtivoTrue(1L))
                .thenReturn(true);

        assertThrows(BusinessException.class,
                () -> categoriasService.deletarCategoria(1L));

        verify(categoriasRepository, never()).save(any());

    }

    @Test
    void naoPermitirCategoriaIgualPropriaCategoria() {

        Categorias categoria = new Categorias();
        categoria.setId(1L);
        categoria.setNome("Celular");
        categoria.setAtivo(true);

        CategoriasRequestDTO dto = new CategoriasRequestDTO();
        dto.setNome("Celular");
        dto.setCategoriaPaiId(1L);

        when(categoriasRepository.findByIdAndAtivoTrue(1L))
                .thenReturn(Optional.of(categoria));

        assertThrows(BusinessException.class,
                () -> categoriasService.atualizarCategoria(1L, dto));

        verify(categoriasRepository, never()).save(any());

    }

}
