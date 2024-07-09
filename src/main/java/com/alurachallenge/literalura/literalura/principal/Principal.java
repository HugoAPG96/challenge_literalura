package com.alurachallenge.literalura.literalura.principal;


import com.alurachallenge.literalura.literalura.model.DatosAutor;
import com.alurachallenge.literalura.literalura.model.DatosLibros;
import com.alurachallenge.literalura.literalura.model.Libro;
import com.alurachallenge.literalura.literalura.repository.LibroRepository;
import com.alurachallenge.literalura.literalura.service.ConsumoAPI;
import com.alurachallenge.literalura.literalura.service.ConvierteDatos;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataIntegrityViolationException;


import java.util.*;

public class Principal {

    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private Scanner teclado = new Scanner(System.in);
    private ConvierteDatos conversor =new ConvierteDatos();
    private List<DatosLibros> datosLibros = new ArrayList<>();
    @Autowired
    private LibroRepository repositorio;

    public Principal(LibroRepository repository) {
        this.repositorio = repository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por titulo 
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                                                      
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrado();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosPorAno();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private DatosLibros getDatosLibro() {
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var tituloLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        System.out.println(json);

        List<DatosLibros> listaLibros = conversor.obtenerListaDeDatos(json, DatosLibros.class);

        if (listaLibros.isEmpty()) {
            System.out.println("No se encontraron libros con el título proporcionado.");
            return null;
        } else {
            DatosLibros datos = listaLibros.get(0); // Suponiendo que deseas el primer libro de la lista
            return datos;
        }
    }

    @Transactional
    private void buscarLibroPorTitulo() {
        DatosLibros datos = getDatosLibro();

        if (datos == null) {
            return;
        }

        try {
            Libro libro = new Libro(datos);
            repositorio.save(libro);
            System.out.println("Libro guardado exitosamente: " + libro);
        } catch (DataIntegrityViolationException e) {
            // Manejar la excepción de clave duplicada
            Optional<Libro> libroExistente = repositorio.findByTitulo(datos.titulo());
            if (libroExistente.isPresent()) {
                System.out.println(libroExistente.get());
            } else {
                System.out.println("Ocurrió un error al guardar el libro y no se pudo encontrar el libro existente.");
                e.printStackTrace();
            }
        }
    }

    private void listarLibrosRegistrado() {
        List<Libro> libros = repositorio.findAll();
        libros.stream()
                .sorted(Comparator.comparing(Libro::toString))
                .forEach(System.out::println);
    }

    private void listarAutoresRegistrados() {
        List<Libro> librosConAutores = repositorio.findAllWithAutores();

        for (Libro libro : librosConAutores) {
            System.out.println("-------- AUTORES --------");
            for (DatosAutor autor : libro.getAutores()) {
                System.out.println("Autor: " + autor.nombre());
                System.out.println("Fecha de Nacimiento: " + (autor.fechaDeNacimiento() != null ? autor.fechaDeNacimiento() : ""));
                System.out.println("Fecha de Muerte: " + (autor.fechaDeMuerte() != null ? autor.fechaDeMuerte() : ""));
            }
            System.out.println("Libros: " + libro.getTitulo());
            System.out.println("------------------------");
        }
    }

    private void listarAutoresVivosPorAno() {
        System.out.println("Ingrese el año para listar autores vivos:");

        int anio;
        try {
            anio = Integer.parseInt(teclado.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Por favor, ingrese un año válido.");
            return;
        }

        String anoString = String.valueOf(anio);
        List<Libro> librosConAutoresVivos = repositorio.findLibrosWithAutoresVivosEnAno(anoString);

        if (librosConAutoresVivos.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año " + anio);
        } else {
            System.out.println("-------- AUTORES VIVOS EN " + anio + " --------");
            for (Libro libro : librosConAutoresVivos) {
                for (DatosAutor autor : libro.getAutores()) {
                    String nombre = autor.nombre();
                    String fechaNacimientoStr = autor.fechaDeNacimiento();
                    String fechaMuerteStr = autor.fechaDeMuerte();

                    // Verificar si las fechas tienen al menos 4 caracteres
                    String anioNacimiento = (fechaNacimientoStr != null && fechaNacimientoStr.length() >= 4)
                            ? fechaNacimientoStr.substring(0, 4)
                            : "Desconocida";
                    String anioMuerte = (fechaMuerteStr != null && fechaMuerteStr.length() >= 4)
                            ? fechaMuerteStr.substring(0, 4)
                            : "Aún vivo";

                    System.out.println("Autor: " + nombre);
                    System.out.println("Fecha de Nacimiento: " + anioNacimiento);
                    System.out.println("Fecha de Muerte: " + anioMuerte);
                    System.out.println("Libro asociado: " + libro.getTitulo());
                    System.out.println("----------------------------------");
                }
            }
        }
    }

    private void listarLibrosPorIdioma() {
        Scanner teclado = new Scanner(System.in);

        // Mostrar menú de idiomas disponibles
        System.out.println("Seleccione el idioma para listar los libros:");
        System.out.println("es - Español");
        System.out.println("en - Inglés");
        System.out.println("fr - Francés");
        System.out.println("py - Portugués");
        System.out.println("it - Italiano");

        // Leer la entrada del usuario
        String opcion = teclado.nextLine().trim().toLowerCase(); // Convertir a minúsculas y quitar espacios

        // Mapa de equivalencias de idiomas
        Map<String, String> idiomasMap = Map.of(
                "es", "Español",
                "en", "Inglés",
                "fr", "Francés",
                "py", "Portugués",
                "it", "Italiano"
        );

        // Verificar si la opción ingresada está en el mapa de idiomas
        if (idiomasMap.containsKey(opcion)) {
            String idiomaSeleccionado = idiomasMap.get(opcion);

            // Obtener libros por el idioma seleccionado
            List<Libro> librosPorIdioma = repositorio.findByIdioma(idiomaSeleccionado);

            if (librosPorIdioma.isEmpty()) {
                System.out.println("No se encontraron libros en el idioma " + idiomaSeleccionado);
            } else {
                System.out.println("-------- LIBROS EN " + idiomaSeleccionado.toUpperCase() + " --------");
                for (Libro libro : librosPorIdioma) {
                    System.out.println("Título: " + libro.getTitulo());
                    System.out.println("Idiomas: " + libro.getIdiomas());
                    System.out.println("Número de Descargas: " + libro.getNumeroDeDescargas());
                    System.out.println("----------------------------------");
                }
            }
        } else {
            System.out.println("Opción de idioma no válida. Por favor, seleccione una opción válida.");
        }
    }
}
