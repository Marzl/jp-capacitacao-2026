package br.com.indra.marcelo_guedes.repository;

import br.com.indra.marcelo_guedes.model.Categorias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriasRepository extends JpaRepository<Categorias, Long> {

    List<Categorias> findByAtivoTrue();
    Optional<Categorias> findByIdAndAtivoTrue(Long id);

    boolean existsByNomeAndCategoriaPaiAndAtivoTrue(String nome, Categorias categoriaPai);

    boolean existsByNomeAndCategoriaPaiAndIdNotAndAtivoTrue(String nome, Categorias categoriaPai, Long id);

    boolean existsByCategoriaPaiIdAndAtivoTrue(Long categoriaPaiId);

}
