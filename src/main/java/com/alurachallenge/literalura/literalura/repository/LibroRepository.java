package com.alurachallenge.literalura.literalura.repository;

import com.alurachallenge.literalura.literalura.model.DatosAutor;
import com.alurachallenge.literalura.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro,Long> {
    Optional<Libro> findByTitulo(String titulo);

    @Query("SELECT l FROM Libro l WHERE l.autor IS NOT EMPTY")
    List<Libro> findAllWithAutores();

    @Query("SELECT DISTINCT l FROM Libro l JOIN l.autor a " +
            "WHERE SUBSTRING(a.fechaDeNacimiento, 1, 4) <= :ano " +
            "AND (a.fechaDeMuerte IS NULL OR SUBSTRING(a.fechaDeMuerte, 1, 4) >= :ano)")
    List<Libro> findLibrosWithAutoresVivosEnAno(@Param("ano") String ano);

    @Query(value = "SELECT * FROM libros l WHERE :idioma = ANY(l.idiomas)", nativeQuery = true)
    List<Libro> findByIdioma(@Param("idioma") String idioma);

}
