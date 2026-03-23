package br.com.indra.marcelo_guedes.repository;

import br.com.indra.marcelo_guedes.model.Produtos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutosRepository extends JpaRepository<Produtos, Long> {
}