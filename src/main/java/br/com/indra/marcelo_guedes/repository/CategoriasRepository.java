package br.com.indra.marcelo_guedes.repository;

import br.com.indra.marcelo_guedes.model.Categorias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriasRepository extends JpaRepository<Categorias, Long> {

    boolean existsByNomeAndCategoriaPai(String nome, Categorias categoriaPai);

    boolean existsByNomeAndCategoriaPaiAndIdNot(String nome, Categorias categoriaPai, Long id);
}
