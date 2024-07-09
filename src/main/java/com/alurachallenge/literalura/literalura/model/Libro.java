package com.alurachallenge.literalura.literalura.model;

import jakarta.persistence.*;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "autores", joinColumns = @JoinColumn(name = "libro_id"))
    private List<DatosAutor> autor;
    private List<String> idiomas;
    private Double numeroDeDescargas;

    private static final Map<String, String> idiomasMap = Map.of(
            "en", "Inglés",
            "es", "Español",
            "fr", "Francés",
            "pt","Portugues",
            "it","Italiano"
            // Agregar más si es necesario
    );

    public Libro(){}

    public Libro(DatosLibros datosLibros){
        this.titulo = datosLibros.titulo();
        this.autor = datosLibros.autor();
        this.idiomas = datosLibros.idiomas().stream()
                .map(idioma -> idiomasMap.getOrDefault(idioma, idioma)) // Transforma el código a nombre legible
                .collect(Collectors.toList());
        this.numeroDeDescargas = OptionalDouble.of(Double.valueOf(datosLibros.numeroDeDescargas())).orElse(0);
    }

    @Override
    public String toString() {
        return String.format("\n"+
                        "-------- LIBRO --------\n" +
                        "Título: %s\n" +
                        "Autor: %s\n" +
                        "Idioma(s): %s\n" +
                        "Número de descargas: %.1f\n" +
                        "------------------------\n"+
                        "------------------------",
                titulo,
                getNombresAutores(),
                String.join(", ", idiomas),
                numeroDeDescargas
        );
    }

    private String getNombresAutores() {
        return autor.stream()
                .map(DatosAutor::nombre)
                .collect(Collectors.joining(", "));
    }

    public List<DatosAutor> getAutores() {
        return autor;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }
}
