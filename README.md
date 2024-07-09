# Literalura

Literalura es una aplicación Java diseñada para gestionar libros y autores. Proporciona funcionalidades para interactuar con una base de datos PostgreSQL y mostrar información sobre libros y autores.

## Tabla de Contenidos

1. [Introducción](#introducción)
2. [Tecnologías Utilizadas](#tecnologías-utilizadas)
3. [Estructura del Proyecto](#estructura-del-proyecto)
4. [Configuración](#configuración)
5. [Uso](#uso)
6. [Funcionalidades](#funcionalidades)
7. [Ejemplos](#ejemplos)

## Introducción

Literalura facilita la gestión y exploración de libros almacenados en una base de datos PostgreSQL. Utiliza Spring Boot y Hibernate para la persistencia de datos y ofrece diversas funcionalidades a través de una interfaz de línea de comandos.

## Tecnologías Utilizadas

- Java
- Spring Boot
- Hibernate
- PostgreSQL
- Maven

### Dependencias

- Jackson Databind: Para el enlace de datos JSON
- Spring Boot Starter Data JPA: Simplifica la implementación de repositorios basados en JPA
- PostgreSQL Driver: Controlador JDBC para la conectividad con la base de datos PostgreSQL

## Estructura del Proyecto

La estructura del proyecto está organizada de la siguiente manera:

- `src/`: Directorio principal de código fuente
  - `main/`: Código de la aplicación
    - `java/`: Archivos fuente de Java
      - `com/alurachallenge/literalura/`: Paquetes de la aplicación
    - `resources/`: Archivos de configuración, scripts SQL, etc.
  - `test/`: Directorio de código de prueba
    - `java/`: Archivos fuente de pruebas
    - `resources/`: Archivos de configuración de pruebas

## Configuración

Para ejecutar la aplicación Literalura localmente:

1. Clona el repositorio desde GitHub.
2. Asegúrate de tener Java y Maven instalados en tu sistema.
3. Configura el archivo `application.properties` en el directorio `resources/` con las credenciales de tu base de datos PostgreSQL. Ejemplo:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/liter_alura
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_contraseña

## Uso

Una vez configurado el proyecto:

Compila el proyecto utilizando Maven.
Ejecuta la aplicación usando mvn spring-boot:run o como una aplicación Java.
Utiliza la interfaz de línea de comandos para interactuar con las funcionalidades de la aplicación.

## Funcionalidades

Listar Libros por Idioma: Permite buscar y mostrar libros disponibles en un idioma específico.
Listar Autores Registrados: Muestra la lista de autores almacenados en la base de datos.
Listar Autores por Año de Vida: Filtra y muestra los autores que estaban vivos en un año específico.
Buscar Libro por Título: Recupera y muestra detalles de un libro específico buscado por su título.
Listar libro por idioma: Recupera y muestra los libros del idioma especificado.

## Ejemplos
```plaintext
1 - Buscar libro por titulo
2 - Listar libros registrados
3 - Listar autores registrados
4 - Listar autores vivos en un determinado año
5 - Listar libros por idioma

0 - Salir

1
Escribe el nombre del libro que deseas buscar
expedition

Libro guardado exitosamente: 
-------- LIBRO --------
Título: The Expedition of Humphry Clinker
Autor: Smollett, T. (Tobias)
Idioma(s): Inglés
Número de descargas: 42964.0
------------------------
------------------------
```
```plaintext
1 - Buscar libro por titulo
2 - Listar libros registrados
3 - Listar autores registrados
4 - Listar autores vivos en un determinado año
5 - Listar libros por idioma

0 - Salir

2
-------- LIBRO --------
Título: Middlemarch
Autor: Eliot, George
Idioma(s): Inglés
Número de descargas: 54135.0
------------------------
------------------------

-------- LIBRO --------
Título: The Expedition of Humphry Clinker
Autor: Smollett, T. (Tobias)
Idioma(s): Inglés
Número de descargas: 42964.0
------------------------
------------------------
```
```plaintext
1 - Buscar libro por titulo
2 - Listar libros registrados
3 - Listar autores registrados
4 - Listar autores vivos en un determinado año
5 - Listar libros por idioma

0 - Salir

3
-------- AUTORES --------
Autor: Eliot, George
Fecha de Nacimiento: 1819
Fecha de Muerte: 1880
Libros: Middlemarch
------------------------
-------- AUTORES --------
Autor: Smollett, T. (Tobias)
Fecha de Nacimiento: 1721
Fecha de Muerte: 1771
Libros: The Expedition of Humphry Clinker
------------------------
```
```plaintext
1 - Buscar libro por titulo
2 - Listar libros registrados
3 - Listar autores registrados
4 - Listar autores vivos en un determinado año
5 - Listar libros por idioma

0 - Salir

4
Ingrese el año para listar autores vivos:
1740
-------- AUTORES VIVOS EN 1740 --------
Autor: Gibbon, Edward
Fecha de Nacimiento: 1737
Fecha de Muerte: 1794
Libro asociado: The History of the Decline and Fall of the Roman Empire: Table of Contents with links in the HTML file to the two Project Gutenberg editions (12 volumes)
----------------------------------
Autor: Smollett, T. (Tobias)
Fecha de Nacimiento: 1721
Fecha de Muerte: 1771
Libro asociado: The Expedition of Humphry Clinker
----------------------------------
```
```plaintext
1 - Buscar libro por titulo
2 - Listar libros registrados
3 - Listar autores registrados
4 - Listar autores vivos en un determinado año
5 - Listar libros por idioma

0 - Salir

5
Seleccione el idioma para listar los libros:
es - Español
en - Inglés
fr - Francés
py - Portugués
it - Italiano
en
Título: Middlemarch
Idiomas: [Inglés]
Número de Descargas: 54135.0
----------------------------------
Título: The Expedition of Humphry Clinker
Idiomas: [Inglés]
Número de Descargas: 42964.0
----------------------------------
```
