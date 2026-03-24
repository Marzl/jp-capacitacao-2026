package br.com.indra.marcelo_guedes.service;

import br.com.indra.marcelo_guedes.model.Categorias;
import br.com.indra.marcelo_guedes.repository.CategoriasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriasService {

    private final CategoriasRepository categoriasRepository;

    public Categorias criarCategoria(Categorias categoria){
        if (categoria.getNome() == null || categoria.getNome().isBlank()) {
            throw new RuntimeException("Nome da categoria é obrigatório");
        }

        if (categoriasRepository.existsByNomeAndCategoriaPai(categoria.getNome(), categoria.getCategoriaPai())) {
            throw new RuntimeException("já existe uma categoria com esse nome dentro da mesma categoria pai");
        }

        return categoriasRepository.save(categoria);
    }

    public List<Categorias> listarCategorias() {
        return categoriasRepository.findAll();
    }

    public Categorias buscarCategoria(Long id) {
        return categoriasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
    }

    public Categorias atualizarCategoria(Long id, Categorias categoriaAtualizada) {

        Categorias categoriaExistente = categoriasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        if (categoriaAtualizada.getNome() == null || categoriaAtualizada.getNome().isBlank()) {
            throw new RuntimeException("Nome da categoria é obrigatório");
        }

        if (categoriasRepository.existsByNomeAndCategoriaPaiAndIdNot(
                categoriaAtualizada.getNome(),
                categoriaAtualizada.getCategoriaPai(),
                id
        )) {
            throw new RuntimeException("Já existe uma categoria com esse nome na mesma categoria pai");
        }

        categoriaExistente.setNome(categoriaAtualizada.getNome());
        categoriaExistente.setCategoriaPai(categoriaAtualizada.getCategoriaPai());

        return categoriasRepository.save(categoriaExistente);
    }

    public void deletarCategoria(Long id) {
        if (categoriasRepository.existsById(id) == false) {
            throw new RuntimeException("Categoria não encontrada");
        }

        categoriasRepository.deleteById(id);
    }

}
