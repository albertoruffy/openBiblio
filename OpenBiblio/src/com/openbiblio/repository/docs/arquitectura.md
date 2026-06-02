# Arquitectura del proyecto

OpenBiblio está organizado mediante una arquitectura por capas.

## Estructura principal

src/com/openbiblio/
├── app/
├── model/
├── repository/
├── service/
├── ui/
└── util/

## Capas del sistema

### app
Contiene la clase principal `MainApp`, encargada de iniciar la aplicación.

### model
Contiene las entidades principales del sistema:
- Libro
- EstadoLectura

### repository
Gestiona el acceso a datos mediante el patrón Repository.

### service
Contiene la lógica de negocio.

### ui
Contiene la interfaz gráfica JavaFX.

### util
Contiene clases auxiliares, como la conexión con SQLite.

## Flujo general

1. El usuario interactúa con la interfaz.
2. La interfaz llama a los servicios.
3. Los servicios usan repositorios.
4. Los repositorios acceden a SQLite.
5. Los datos vuelven a la interfaz.
