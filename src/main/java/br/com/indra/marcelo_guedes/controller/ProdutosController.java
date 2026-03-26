package br.com.indra.marcelo_guedes.controller;

import br.com.indra.marcelo_guedes.model.Produtos;
import br.com.indra.marcelo_guedes.service.ProdutosService;
import br.com.indra.marcelo_guedes.service.dto.ProdutosRequestDTO;
import br.com.indra.marcelo_guedes.service.dto.ProdutosResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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

    //C

    /**
     * Recomendação de desenvolvimento, ampliar responses(responseEntity)
     * possíveis além do ok.
     */
    @Operation(description = "Endpoint para criar um novo produto",
            summary = "Criação de produto")
    @PostMapping("/cria")
    public ResponseEntity<ProdutosResponseDTO> criarProduto(@RequestBody ProdutosRequestDTO dto){
        return ResponseEntity.ok(produtosService.criarProduto(dto));
    }

    /**
     * GET
     * localhost:9090/produtos
     * @return
     */
    @GetMapping
    public ResponseEntity<List<ProdutosResponseDTO>> getAll(){
        return ResponseEntity.ok(produtosService.listarProdutos());
    }

    /**
     * GET
     * localhost:9090/produtos/1
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProdutosResponseDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(produtosService.buscarProduto(id));
    }

    //U
    @PutMapping("/atualizar")
    public ResponseEntity<ProdutosResponseDTO> atualizarProduto(@RequestParam Long id,
                                                     @RequestBody ProdutosRequestDTO dto){
        return ResponseEntity.ok(produtosService.atualizarProdutos(dto));
    }

    @PatchMapping("/atualiza-preco/{id}")
    public ResponseEntity<ProdutosResponseDTO> atualizarProdutoParcial(@PathVariable Long id,
                                                            @RequestParam BigDecimal preco) {
        return ResponseEntity.ok(produtosService.atualizarPreco(id, preco));
    }

    //Mudar para delete lógico
    @DeleteMapping("/deleta/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        produtosService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }
}