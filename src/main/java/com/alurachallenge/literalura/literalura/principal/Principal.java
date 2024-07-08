package com.alurachallenge.literalura.literalura.principal;


import com.alurachallenge.literalura.literalura.model.DatosLibros;
import com.alurachallenge.literalura.literalura.model.Libro;
import com.alurachallenge.literalura.literalura.repository.LibroRepository;
import com.alurachallenge.literalura.literalura.service.ConsumoAPI;
import com.alurachallenge.literalura.literalura.service.ConvierteDatos;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;


import java.util.*;
import java.util.stream.Collectors;

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
                /*case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosPorAno();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;*/

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
}
