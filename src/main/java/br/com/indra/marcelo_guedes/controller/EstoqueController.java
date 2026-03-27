package br.com.indra.marcelo_guedes.controller;

import br.com.indra.marcelo_guedes.service.EstoqueService;
import br.com.indra.marcelo_guedes.service.dto.EstoqueResponseDTO;
import br.com.indra.marcelo_guedes.service.dto.MovimentacaoEstoqueRequestDTO;
import br.com.indra.marcelo_guedes.service.dto.MovimentacaoEstoqueResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Estoque", description = "Endpoints para gerenciamento de estoque")
@RequestMapping("/estoque")
public class EstoqueController {

    private final EstoqueService estoqueService;

    @Operation(description = "Endpoint para adicionar estoque de um produto",
            summary = "Adicionar Estoque Produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estoque adicionado com sucesso"),
            @ApiResponse(responseCode = "400", description = "qquantidade inválida"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PostMapping("/{produtoId}/entrada")
    public ResponseEntity<EstoqueResponseDTO> adicionarEstoque (@PathVariable Long produtoId,
                                                                @RequestBody MovimentacaoEstoqueRequestDTO dto) {
        return ResponseEntity.ok(estoqueService.adicionarEstoque(produtoId, dto));
    }

    @Operation(description = "Endpoint para remover estoque de um produto",
            summary = "Remover Estoque Produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estoque removido com sucesso"),
            @ApiResponse(responseCode = "400", description = "quantidade inválida ou estoque insuficiente"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PostMapping("/{produtoId}/saida")
    public ResponseEntity<EstoqueResponseDTO> removerEstoque(@PathVariable Long produtoId,
                                                             @RequestBody MovimentacaoEstoqueRequestDTO dto) {
        return ResponseEntity.ok(estoqueService.removerEstoque(produtoId, dto));
    }

    @Operation(description = "Endpoint para buscar estoque de um produto pelo id",
            summary = "Buscar estoque")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @GetMapping("/{produtoId}")
    public ResponseEntity<EstoqueResponseDTO> consultarEstoque (@PathVariable Long produtoId) {
        return ResponseEntity.ok(estoqueService.consultarEstoque(produtoId));
    }

    @Operation(description = "Endpoint para listar todas as movimentações no estoque de um produto pelo id",
            summary = "Listar Movimentações Estoque")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimentações no estoque listadas com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @GetMapping("/{produtoId}/movimentacoes")
    public ResponseEntity<List<MovimentacaoEstoqueResponseDTO>> listarMovimentacoes (@PathVariable Long produtoId) {
        return ResponseEntity.ok(estoqueService.listarMovimentacoes(produtoId));
    }

}
