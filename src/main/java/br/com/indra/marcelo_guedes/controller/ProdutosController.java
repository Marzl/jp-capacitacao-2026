package br.com.indra.marcelo_guedes.controller;

import br.com.indra.marcelo_guedes.service.HistoricoPrecoService;
import br.com.indra.marcelo_guedes.service.ProdutosService;
import br.com.indra.marcelo_guedes.service.dto.HistoricoPrecoResponseDTO;
import br.com.indra.marcelo_guedes.service.dto.ProdutosRequestDTO;
import br.com.indra.marcelo_guedes.service.dto.ProdutosResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Produtos", description = "Endpoints para gerenciamento de produtos")
@RequestMapping("/produtos")
public class ProdutosController {

    private final ProdutosService produtosService;
    private final HistoricoPrecoService historicoPrecoService;

    @Operation(description = "Endpoint para criar um novo produto",
            summary = "Criação de produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @PostMapping()
    public ResponseEntity<ProdutosResponseDTO> criarProduto(@RequestBody ProdutosRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(produtosService.criarProduto(dto));
    }

    @Operation(description = "Endpoint para listar todos os produtos",
            summary = "Listar produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos listados com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<ProdutosResponseDTO>> listarProdutos(){
        return ResponseEntity.ok(produtosService.listarProdutos());
    }

    @Operation(description = "Endpoint para listar o histórico de preço de um produto pelo id",
            summary = "Listar histórico preço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Histórico de preço encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @GetMapping("/{id}/historico-preco")
    public ResponseEntity<List<HistoricoPrecoResponseDTO>> listarHistoricoPreco(@PathVariable Long id) {
        return ResponseEntity.ok(historicoPrecoService.listarHistoricoPreco(id));
    }

    @Operation(description = "Endpoint para buscar um produto pelo id",
            summary = "Buscar produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProdutosResponseDTO> buscarProduto(@PathVariable Long id){
        return ResponseEntity.ok(produtosService.buscarProduto(id));
    }

    @Operation(description = "Endpoint para atualizar um produto completamente",
            summary = "Atualizar produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProdutosResponseDTO> atualizarProduto(@PathVariable Long id,
                                                     @RequestBody ProdutosRequestDTO dto){
        return ResponseEntity.ok(produtosService.atualizarProduto(id, dto));
    }

    @Operation(description = "Endpoint para atualizar o preço de um produto",
            summary = "Atualizar preço produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Preço atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PatchMapping("/atualiza-preco/{id}")
    public ResponseEntity<ProdutosResponseDTO> atualizarPrecoProduto(@PathVariable Long id,
                                                                     @RequestParam BigDecimal preco) {
        return ResponseEntity.ok(produtosService.atualizarPreco(id, preco));
    }

    //Mudar para delete lógico
    @Operation(description = "Endpoint para deletar um produto",
            summary = "Deletar produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        produtosService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }
}