package br.com.indra.marcelo_guedes.service;

import br.com.indra.marcelo_guedes.model.Categorias;
import br.com.indra.marcelo_guedes.repository.CategoriasRepository;
import br.com.indra.marcelo_guedes.service.dto.CategoriasResponseDTO;
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

    public List<CategoriasResponseDTO> listarCategorias() {
        return categoriasRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public CategoriasResponseDTO buscarCategoria(Long id) {

        Categorias categoria = categoriasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        return toResponseDTO(categoria);
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

    private CategoriasResponseDTO toResponseDTO(Categorias categoria){

        Long categoriaPaiId = null;
        String categoriaPaiNome = null;

        if (categoria.getCategoriaPai() != null) {
            categoriaPaiId = categoria.getCategoriaPai().getId();
            categoriaPaiNome = categoria.getCategoriaPai().getNome();
        }

        return CategoriasResponseDTO.builder()
                .id(categoria.getId())
                .nome(categoria.getNome())
                .categoriaPaiId(categoriaPaiId)
                .categoriaPaiNome(categoriaPaiNome)
                .build();

    }
}
