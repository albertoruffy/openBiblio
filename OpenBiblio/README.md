OpenBiblio

OpenBiblio es una aplicación de escritorio desarrollada en Java y JavaFx para gestionar una biblioteca personal de forma local  .

La idea principal del proyecto es que el usuario pueda llevar un control de su propia colección de libros desde una aplicación local, sin depender necesariamente de plataformas externas. Desde la interfaz se pueden registrar libros, consultar la información guardada, modificar datos, añadir notas personales información bibliografica, el estado de lectura y visualizar el estado general de la biblioteca.

Características principales:

Alta, modificación y eliminación de libros.
Búsqueda por título, autor, género o ISBN.
Consulta detallada de información bibliográfica.
Gestión del estado de lectura.
Registro de notas y observaciones personales.
Exportación de listados.
Recomendaciones y enlaces relacionados con libros.
Almacenamiento local mediante SQLite.
Interfaz gráfica desarrollada con JavaFX.

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

* Java 8          Lógica del negocio
* JavaFX          Interfaz gráfica
* SQLite          Base de datos local
* Git             Control de versiones
* GitHub          Gestión del repositorio

Diseño de la aplicación

La aplicación sigue una estructura basada en el patrón Modelo-Vista-Controlador. Esta organización permite separar la parte visual de la lógica de la aplicación y de la gestión de datos.

De forma general, el proyecto se divide en:

* Modelo: clases que representan los datos principales, como: Libro, Autor, Género, Estado de lectura.
* Vista: pantallas e interfaz gráfica desarrolladas con JavaFX que muestran formularios, permite visualizar libros y Gestionar la interacción con el usuario.
* Controlador: clases encargadas de conectar la interfaz con la lógica de la aplicación. Cuyas funciones principales son:  Validar datos, Procesar acciones del usuario,  Actualizar la base de datos, Refrescar la interfaz.
* Persistencia: almacenamiento local de la información de la biblioteca mediante SQLite usando los archivos: openbiblio.db y bibliografía abierta.db

Objetivo del proyecto

El objetivo principal de OpenBiblio es desarrollar una aplicación completa que permita al usuario gestionar su colección de libros de forma sencilla, visual y organizada.

Estructura del proyecto

openBiblio/
│
├── src/                  Código fuente Java
├── docs/                 Librerías externas
├── resources/css/        Hojas de estilo de la interfaz 
├── openbiblio.db         Base de datos SQLite
├── README.md             Documentación principal
├── build.fxbuild         Configuración JavaFX
└── .gitignore
├── LICENSE
└── pom.xml

Instalación

Requisitos previos

* Java 8. o superior
* Maven 3.x.
* Git, para clonar el repositorio.

Clonar el repositorio

git clone https://github.com/albertoruffy/openBiblio.git

Compilar el proyecto

mvn clean package

Ejecutar la aplicación

mvn javafx:run

También se puede importar el proyecto en Eclipse/Netbeans y ejecutarlo desde el propio entorno de desarrollo.

Funcionamiento general

El usuario inicia la aplicación.
Se carga la base de datos SQLite local.
La interfaz muestra el catálogo disponible.
El usuario puede:
    Añadir libros.
    Editar registros.
    Eliminar libros.
    Buscar información.
    Añadir notas.
Los cambios se guardan automáticamente en la base de datos.

Futuras mejoras
*Sistema de usuarios.
*Sincronización en la nube.
*Estadísticas de lectura.
*Importación desde ISBN.
*Integración con APIs bibliográficas.
*Exportación avanzada a PDF y Excel.

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
* Rosario Garifa Ccapira

Licencia

Proyecto desarrollado con fines académicos y formativos.
Este proyecto ha sido desarrollado con fines académicos.
