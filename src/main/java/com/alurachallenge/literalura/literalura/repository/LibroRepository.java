package com.alurachallenge.literalura.literalura.repository;

import com.alurachallenge.literalura.literalura.model.DatosAutor;
import com.alurachallenge.literalura.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro,Long> {
    Optional<Libro> findByTitulo(String titulo);

    @Query("SELECT l FROM Libro l WHERE :autor MEMBER OF l.autor")
    List<Libro> findByAutor(DatosAutor autor);

    @Query("SELECT l FROM Libro l JOIN l.idiomas i WHERE i = :idioma")
    List<Libro> findByIdioma(String idioma);

    @Query("SELECT l FROM Libro l WHERE l.autor IS NOT EMPTY")
    List<Libro> findAllWithAutores();
}
