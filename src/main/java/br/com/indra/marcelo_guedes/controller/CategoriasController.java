package br.com.indra.marcelo_guedes.controller;

import br.com.indra.marcelo_guedes.model.Categorias;
import br.com.indra.marcelo_guedes.service.CategoriasService;
import br.com.indra.marcelo_guedes.service.dto.CategoriasResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Categorias", description = "Endpoints para gerenciamento de categorias")
@RequestMapping("/categorias")
public class CategoriasController {

    private final CategoriasService categoriasService;

    @Operation(description = "Endpoint para criar uma nova Categoria",
            summary = "Criação de Categoria")
    @PostMapping()
    public ResponseEntity<Categorias> criarCategoria(@RequestBody Categorias categoria) {
        return ResponseEntity.ok(categoriasService.criarCategoria(categoria));
    }

    @Operation(description = "Endpoint para listar todas as Categorias",
            summary = "Listar Categorias")
    @GetMapping
    public ResponseEntity<List<CategoriasResponseDTO>> listarCategorias() {
        return ResponseEntity.ok(categoriasService.listarCategorias());
    }

    @Operation(description = "Endpoint para buscar Categoria por Id",
            summary = "Buscar Categoria por Id")
    @GetMapping("/{id}")
    public ResponseEntity<CategoriasResponseDTO> buscarCategoria(@PathVariable Long id){
        return ResponseEntity.ok(categoriasService.buscarCategoria(id));
    }

    @Operation(description = "Endpoint para atualizar uma Categoria",
            summary = "Atualizar Categoria")
    @PutMapping("/{id}")
    public ResponseEntity<Categorias> atualizarCategoria(@PathVariable Long id,
                                                         @RequestBody Categorias categoriaAtualizada){
        return ResponseEntity.ok(categoriasService.atualizarCategoria(id, categoriaAtualizada));
    }

    @Operation(description = "Endpoint para deletar uma Categoria",
            summary = "Deletar Categoria")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCategoria(@PathVariable Long id) {
        categoriasService.deletarCategoria(id);
        return ResponseEntity.noContent().build();
    }

}
