package br.com.indra.marcelo_guedes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categorias")
public class Categorias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoria_id")
    private Long id;

    @Column(name= "nome", nullable = false)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "categoria_pai_id")
    private Categorias categoriaPai;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

}
