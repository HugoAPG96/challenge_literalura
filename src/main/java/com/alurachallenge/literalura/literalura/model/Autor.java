package com.alurachallenge.literalura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;


import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Entity
@Table(name = "Autores")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private LocalDate fechaDeNacimiento;
    private LocalDate fechaDeMuerte;

    @ManyToOne
    @JoinColumn(name = "libro_id")
    private Libro libro;

    public Autor() {
    }

    public Autor(String nombre, LocalDate fechaDeNacimiento, LocalDate fechaDeMuerte) {
        this.nombre = nombre;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.fechaDeMuerte = fechaDeMuerte;
    }

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.fechaDeNacimiento = parseFecha(datosAutor.fechaDeNacimiento());
        this.fechaDeMuerte = parseFecha(datosAutor.fechaDeMuerte());
    }

    private LocalDate parseFecha(String fecha) {
        try {
            return LocalDate.parse(fecha);
        } catch (DateTimeParseException | NullPointerException e) {
            return null;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(LocalDate fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public LocalDate getFechaDeMuerte() {
        return fechaDeMuerte;
    }

    public void setFechaDeMuerte(LocalDate fechaDeMuerte) {
        this.fechaDeMuerte = fechaDeMuerte;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fechaDeNacimiento=" + fechaDeNacimiento +
                ", fechaDeMuerte=" + fechaDeMuerte +
                ", libro=" + (libro != null ? libro.getTitulo() : "null") +
                '}';
    }
}

