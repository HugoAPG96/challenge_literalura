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
//    @ElementCollection
//    @CollectionTable(name = "autores", joinColumns = @JoinColumn(name = "libro_id"))
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Autor> autor;
//    @ElementCollection
//    @CollectionTable(name = "idiomas", joinColumns = @JoinColumn(name = "libro_id"))
//    @Column(name = "idioma")  // Necesario para la tabla de elementos simples
    private List<String> idiomas;
    private Double numeroDeDescargas;

    private static final Map<String, String> idiomasMap = Map.of(
            "en", "Inglés",
            "es", "Español",
            "fr", "Francés",
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
        return
                "(titulo='" + titulo +
                ", autor=" + autor +
                ", idiomas=" + idiomas +
                ", numeroDeDescargas=" + numeroDeDescargas+")";
    }

    public List<Autor> getEpisodios() {
        return autor;
    }

    public void setEpisodios(List<Autor> autor) {
        autor.forEach(e -> e.setLibro(this));
        this.autor = autor;
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

    // Método para obtener los nombres de los autores
    /*private String getNombresAutores() {
        if (autor == null || autor.isEmpty()) {
            return "Sin autores";
        }

        return autor.stream()
                .map(DatosAutor::getNombre)
                .collect(Collectors.joining(", "));
    }

    public List<String> getAutores() {
        return autor.stream()
                .map(Autor::getNombre)
                .collect(Collectors.toList());
    }*/
}
