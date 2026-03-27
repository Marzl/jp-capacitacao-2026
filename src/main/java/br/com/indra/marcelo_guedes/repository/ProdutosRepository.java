package br.com.indra.marcelo_guedes.repository;

import br.com.indra.marcelo_guedes.model.Produtos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutosRepository extends JpaRepository<Produtos, Long> {

    List<Produtos> findByAtivoTrue();
    Optional<Produtos> findByIdAndAtivoTrue(Long id);

    boolean existsByCategoriaIdAndAtivoTrue(Long categoriaId);

}