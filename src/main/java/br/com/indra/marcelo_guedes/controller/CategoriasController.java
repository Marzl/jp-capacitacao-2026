package br.com.indra.marcelo_guedes.controller;

import br.com.indra.marcelo_guedes.service.CategoriasService;
import br.com.indra.marcelo_guedes.service.dto.CategoriasRequestDTO;
import br.com.indra.marcelo_guedes.service.dto.CategoriasResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Categoria pai não encontrada")
    })
    @PostMapping()
    public ResponseEntity<CategoriasResponseDTO> criarCategoria(@RequestBody CategoriasRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriasService.criarCategoria(dto));
    }

    @Operation(description = "Endpoint para listar todas as Categorias",
            summary = "Listar Categorias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categorias listadas com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<CategoriasResponseDTO>> listarCategorias() {
        return ResponseEntity.ok(categoriasService.listarCategorias());
    }

    @Operation(description = "Endpoint para buscar Categoria por Id",
            summary = "Buscar Categoria por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoriasResponseDTO> buscarCategoria(@PathVariable Long id){
        return ResponseEntity.ok(categoriasService.buscarCategoria(id));
    }

    @Operation(description = "Endpoint para atualizar uma Categoria",
            summary = "Atualizar Categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria Atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Categoria ou categoria pai não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoriasResponseDTO> atualizarCategoria(@PathVariable Long id,
                                                         @RequestBody CategoriasRequestDTO dto){
        return ResponseEntity.ok(categoriasService.atualizarCategoria(id, dto));
    }

    @Operation(description = "Endpoint para deletar uma Categoria",
            summary = "Deletar Categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCategoria(@PathVariable Long id) {
        categoriasService.deletarCategoria(id);
        return ResponseEntity.noContent().build();
    }

}
