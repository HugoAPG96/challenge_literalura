package com.alurachallenge.literalura.literalura.repository;

import com.alurachallenge.literalura.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro,Long> {
}
