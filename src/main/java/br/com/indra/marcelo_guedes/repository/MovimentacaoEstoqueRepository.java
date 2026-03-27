package br.com.indra.marcelo_guedes.repository;

import br.com.indra.marcelo_guedes.model.MovimentacaoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimentacaoEstoqueRepository extends JpaRepository<MovimentacaoEstoque, Long> {

    List<MovimentacaoEstoque> findByProdutoId(Long produtoId);
}
