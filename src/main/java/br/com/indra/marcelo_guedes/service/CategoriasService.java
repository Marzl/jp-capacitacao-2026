package br.com.indra.marcelo_guedes.service;

import br.com.indra.marcelo_guedes.exceptions.BusinessException;
import br.com.indra.marcelo_guedes.exceptions.ResourceNotFoundException;
import br.com.indra.marcelo_guedes.model.Categorias;
import br.com.indra.marcelo_guedes.repository.CategoriasRepository;
import br.com.indra.marcelo_guedes.repository.ProdutosRepository;
import br.com.indra.marcelo_guedes.service.dto.CategoriasRequestDTO;
import br.com.indra.marcelo_guedes.service.dto.CategoriasResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriasService {

    private final CategoriasRepository categoriasRepository;
    private final ProdutosRepository produtosRepository;

    public CategoriasResponseDTO criarCategoria(CategoriasRequestDTO dto) {

        if (dto.getNome() == null || dto.getNome().isBlank()) {
            throw new RuntimeException("Nome da categoria é obrigatório");
        }

        Categorias categoriaPai = null;

        if (dto.getCategoriaPaiId() != null) {
            categoriaPai = categoriasRepository.findByIdAndAtivoTrue(dto.getCategoriaPaiId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoria pai não encontrada"));
        }

        if (categoriasRepository.existsByNomeAndCategoriaPaiAndAtivoTrue(dto.getNome(), categoriaPai)) {
            throw new BusinessException("já existe uma categoria com esse nome dentro da mesma categoria pai");
        }

        Categorias categoria = new Categorias();
        categoria.setNome(dto.getNome());
        categoria.setCategoriaPai(categoriaPai);
        categoria.setAtivo(true);

        Categorias categoriaSalva = categoriasRepository.save(categoria);

        return toResponseDTO(categoriaSalva);
    }

    public List<CategoriasResponseDTO> listarCategorias() {
        return categoriasRepository.findByAtivoTrue()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public CategoriasResponseDTO buscarCategoria(Long id) {

        Categorias categoria = categoriasRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        return toResponseDTO(categoria);
    }

    public CategoriasResponseDTO atualizarCategoria(Long id, CategoriasRequestDTO dto) {

        Categorias categoriaExistente = categoriasRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        if (dto.getNome() == null || dto.getNome().isBlank()) {
            throw new RuntimeException("Nome da categoria é obrigatório");
        }

        if (dto.getCategoriaPaiId() != null && dto.getCategoriaPaiId().equals(id)) {
            throw new BusinessException("uma categoria não pode ser categoria pai dela mesma");
        }

        Categorias categoriaPai = null;

        if (dto.getCategoriaPaiId() != null) {
            categoriaPai = categoriasRepository.findByIdAndAtivoTrue(dto.getCategoriaPaiId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoria pai não encontrada"));
        }

        if (categoriasRepository.existsByNomeAndCategoriaPaiAndIdNotAndAtivoTrue(
                dto.getNome(),
                categoriaPai,
                id
        )) {
            throw new BusinessException("Já existe uma categoria com esse nome na mesma categoria pai");
        }

        categoriaExistente.setNome(dto.getNome());
        categoriaExistente.setCategoriaPai(categoriaPai);

        Categorias categoriaAtualizada = categoriasRepository.save(categoriaExistente);

        return toResponseDTO(categoriaAtualizada);
    }

    public void deletarCategoria(Long id) {

        Categorias categoria = categoriasRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        if (categoriasRepository.existsByCategoriaPaiIdAndAtivoTrue(id)) {
            throw new BusinessException("não é possivel inativar categoria porque existem subcategorias ativas vinculadas");
        }

        if (produtosRepository.existsByCategoriaIdAndAtivoTrue(id)) {
            throw new BusinessException("não é possivel inativar categoria porque existem produtos ativos vinculados a ela");
        }

        categoria.setAtivo(false);
        categoriasRepository.save(categoria);
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
