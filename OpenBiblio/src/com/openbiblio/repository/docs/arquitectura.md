# Arquitectura del proyecto

OpenBiblio está desarrollado utilizando una arquitectura por capas inspirada en MVC y el patrón Repository.

## Estructura del proyecto

```text
src/com/openbiblio/
├── app/
├── model/
├── repository/
├── service/
├── ui/
│   ├── controller/
│   └── view/
└── util/
```

## Descripción de las capas

### app

Contiene el punto de entrada de la aplicación.

Clase principal:

* MainApp.java

Responsabilidades:

* Inicializar SQLite.
* Cargar JavaFX.
* Aplicar estilos CSS.
* Mostrar la ventana principal.

### model

Representa las entidades de negocio.

Actualmente:

* Libro
* EstadoLectura

### repository

Gestiona el acceso a datos.

Actualmente:

* LibroRepository
* SqliteLibroRepository

### service

Contiene la lógica de negocio.

Servicios disponibles:

* CsvImportService
* CsvExportService
* OpenLibraryService
* FtpBackupService
* InstallationService

### ui/view

Contiene las pantallas JavaFX (FXML).

### ui/controller

Gestiona la interacción entre las vistas y la lógica de negocio.

### util

Clases auxiliares utilizadas por el resto del sistema.

## Flujo de ejecución

1. MainApp inicia la aplicación.
2. Se inicializa SQLite.
3. Se carga main.fxml.
4. El usuario interactúa con la interfaz.
5. Los controladores invocan servicios.
6. Los servicios utilizan repositorios.
7. Los repositorios actualizan SQLite.
8. La interfaz refleja los cambios.

```
```
