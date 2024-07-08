package com.alurachallenge.literalura.literalura.service;

import com.alurachallenge.literalura.literalura.model.DatosAutor;
import com.alurachallenge.literalura.literalura.model.Libro;
import com.alurachallenge.literalura.literalura.repository.LibroRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibroService {
    @Autowired
    private LibroRepository libroRepository;


    public List<DatosAutor> listarAutoresRegistrados() {
        return libroRepository.findAllWithAutores().stream()
                .flatMap(libro -> libro.getAutores().stream())
                .distinct()
                .collect(Collectors.toList());
    }

    public List<DatosAutor> listarAutoresVivosPorAno(int ano) {
        return libroRepository.findAllWithAutores().stream()
                .flatMap(libro -> libro.getAutores().stream())
                .filter(autor -> autor.fechaDeMuerte() == null &&
                        autor.fechaDeNacimiento() != null &&
                        autor.fechaDeNacimiento().contains(String.valueOf(ano)))
                .distinct()
                .collect(Collectors.toList());
    }

    @Transactional
    public List<Libro> listarLibrosPorIdioma(String idioma) {
        return libroRepository.findByIdioma(idioma);
    }
}
