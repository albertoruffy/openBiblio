OpenBiblio

OpenBiblio es una aplicación de escritorio desarrollada en Java para gestionar una biblioteca personal.

La idea principal del proyecto es que el usuario pueda llevar un control de su propia colección de libros desde una aplicación local, sin depender necesariamente de plataformas externas. Desde la interfaz se pueden registrar libros, consultar la información guardada, modificar datos, añadir notas personales y visualizar el estado general de la biblioteca.

Funcionalidades

Actualmente el proyecto está orientado a las siguientes funcionalidades:

* Registro de libros personales.
* Edición y eliminación de libros.
* Búsqueda y filtrado por título, autor, género o ISBN.
* Consulta del detalle de cada libro.
* Gestión del estado de lectura.
* Añadir notas personales o citas.
* Exportación de listados.
* Recomendaciones o enlaces relacionados con libros.
* Interfaz gráfica desarrollada con JavaFX.

* Tecnologías utilizadas

El proyecto ha sido desarrollado utilizando principalmente:

* Java 8
* JavaFX
* SQLite
* Maven
* Git
* GitHub

Diseño de la aplicación

La aplicación sigue una estructura basada en el patrón Modelo-Vista-Controlador. Esta organización permite separar la parte visual de la lógica de la aplicación y de la gestión de datos.

De forma general, el proyecto se divide en:

* Modelo: clases que representan los datos principales, como los libros.
* Vista: pantallas e interfaz gráfica desarrolladas con JavaFX.
* Controlador: clases encargadas de conectar la interfaz con la lógica de la aplicación.
* Persistencia: almacenamiento local de la información de la biblioteca.

Objetivo del proyecto

El objetivo principal de OpenBiblio es desarrollar una aplicación completa que permita al usuario gestionar su colección de libros de forma sencilla, visual y organizada.

Estructura del proyecto

openBiblio/
│
├── src/
├── docs/
├── README.md
├── LICENSE
└── pom.xml

Instalación

Requisitos

* Java 8.
* Maven 3.x.
* Git, para clonar el repositorio.

Clonar el repositorio

git clone https://github.com/albertoruffy/openBiblio.git

Compilar el proyecto

mvn clean package

Ejecutar la aplicación

mvn javafx:run

También se puede importar el proyecto en Eclipse y ejecutarlo desde el propio entorno de desarrollo.

Documentación

La carpeta docs contiene documentación adicional del proyecto:

* Guía de instalación.
* Arquitectura de la aplicación.
* Diagramas UML.
* Memoria del proyecto.
* Planificación y futuras mejoras.

Autores

Proyecto desarrollado por:

* Carlos Vallecillo Jiménez
* Adrián Ramos Godino
* Alberto Osorio Bautista
* Rosario 

Licencia

Este proyecto ha sido desarrollado con fines académicos.
