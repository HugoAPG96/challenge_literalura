# Literalura

Literalura is a Java application designed for managing books and authors. It provides functionality to interact with a PostgreSQL database to retrieve and display information about books and authors.

## Table of Contents

1. [Introduction](#introduction)
2. [Technologies Used](#technologies-used)
3. [Project Structure](#project-structure)
4. [Setup](#setup)
5. [Usage](#usage)
6. [Functionality](#functionality)
7. [Examples](#examples)
8. [Contributing](#contributing)
9. [License](#license)

## Introduction

Literalura facilitates the management and exploration of books stored in a PostgreSQL database. It leverages Spring Boot and Hibernate for data persistence and provides various functionalities through a command-line interface.

## Technologies Used

- Java
- Spring Boot
- Hibernate
- PostgreSQL
- Maven

### Dependencies

- Jackson Databind: For JSON data binding
- Spring Boot Starter Data JPA: Simplifies implementation of JPA-based repositories
- PostgreSQL Driver: JDBC driver for PostgreSQL database connectivity

## Project Structure

The project structure is organized as follows:

- `src/`: Main source code directory
  - `main/`: Application code
    - `java/`: Java source files
      - `com/alurachallenge/literalura/`: Application packages
    - `resources/`: Configuration files, SQL scripts, etc.
  - `test/`: Test code directory
    - `java/`: Test source files
    - `resources/`: Test configuration files

## Setup

To run the Literalura application locally:

1. Clone the repository from GitHub.
2. Ensure you have Java and Maven installed on your system.
3. Configure the `application.properties` file in the `resources/` directory with your PostgreSQL database credentials. Example:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/liter_alura
   spring.datasource.username=your_username
   spring.datasource.password=your_password
