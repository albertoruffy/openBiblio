# OpenBiblio

OpenBiblio es una aplicación de escritorio desarrollada en Java para la gestión de bibliotecas. El proyecto ha sido realizado como práctica de la asignatura de Informática I y tiene como objetivo aplicar los conocimientos adquiridos sobre programación orientada a objetos, diseño de interfaces gráficas y trabajo colaborativo mediante Git.

La aplicación permite gestionar usuarios, libros, ejemplares, préstamos y devoluciones desde una interfaz gráfica desarrollada con JavaFX.

## Funcionalidades

Actualmente la aplicación incluye las siguientes funcionalidades:

- Gestión de usuarios.
- Gestión de libros y ejemplares.
- Registro de préstamos.
- Registro de devoluciones.
- Búsqueda de información.
- Interfaz gráfica desarrollada con JavaFX.
- Sistema de control de licencias.

## Capturas de pantalla

### Pantalla principal

![Pantalla principal](docs/img/inicio.png)

### Gestión de usuarios

![Gestión de usuarios](docs/img/usuarios.png)

### Gestión de préstamos

![Gestión de préstamos](docs/img/prestamos.png)

## Tecnologías utilizadas

El proyecto ha sido desarrollado utilizando las siguientes tecnologías:

- Java 8
- JavaFX
- Maven
- Git
- GitHub

## Diseño de la aplicación

La aplicación sigue una estructura basada en el patrón Modelo-Vista-Controlador (MVC), lo que permite separar la lógica de negocio de la interfaz gráfica y facilita el mantenimiento del código.

La documentación técnica detallada puede consultarse en la carpeta `docs`.

## Sistema de licencias

Durante el desarrollo del proyecto se ha incorporado un sistema de licencias que permite controlar la distribución y el acceso a determinadas funcionalidades de la aplicación.

Este sistema incluye:

- Generación de claves de licencia.
- Validación de licencias durante la ejecución.
- Restricción de funcionalidades según el tipo de licencia.
- Comprobaciones de integridad básicas.

La implementación de este sistema ha servido para estudiar mecanismos habituales en aplicaciones comerciales.

## Estructura del proyecto

```text
openBiblio/
│
├── src/
├── docs/
├── README.md
├── LICENSE
└── pom.xml
```

## Instalación

### Requisitos

- Java 8.
- Maven 3.x.

### Clonar el repositorio

```bash
git clone https://github.com/albertoruffy/openBiblio.git
```

### Compilar el proyecto

```bash
mvn clean package
```

### Ejecutar la aplicación

```bash
mvn javafx:run
```

## Documentación

La carpeta `docs` contiene documentación adicional sobre el proyecto:

- Arquitectura de la aplicación.
- Guía de instalación.
- Diagramas UML.
- Memoria del proyecto.
- Planificación y futuras mejoras.

## Objetivos del proyecto

Los principales objetivos perseguidos durante el desarrollo han sido:

- Aplicar los conceptos estudiados en la asignatura.
- Desarrollar una aplicación completa utilizando programación orientada a objetos.
- Diseñar una interfaz gráfica funcional y fácil de utilizar.
- Aprender a trabajar con control de versiones mediante Git.
- Documentar adecuadamente el desarrollo del software.

## Licencia

Este proyecto ha sido desarrollado con fines exclusivamente académicos.
