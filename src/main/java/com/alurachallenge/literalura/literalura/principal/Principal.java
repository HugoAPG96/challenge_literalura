package com.alurachallenge.literalura.literalura.principal;


import com.alurachallenge.literalura.literalura.model.DatosLibros;
import com.alurachallenge.literalura.literalura.service.ConsumoAPI;
import com.alurachallenge.literalura.literalura.service.ConvierteDatos;


import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private Scanner teclado = new Scanner(System.in);
    private ConvierteDatos conversor =new ConvierteDatos();
    private List<DatosLibros> datosLibros = new ArrayList<>();

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

    private void buscarLibroPorTitulo() {
        DatosLibros datos = getDatosLibro();
        datosLibros.add(datos);
        System.out.println(datos);
    }

    private void listarLibrosRegistrado() {
        datosLibros.forEach(System.out::println);
    }
}
