package com.alurachallenge.literalura.literalura.repository;

import com.alurachallenge.literalura.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro,Long> {
    Optional<Libro> findByTitulo(String titulo);
}
