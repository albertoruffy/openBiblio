# Base de datos

OpenBiblio utiliza SQLite como sistema de almacenamiento local.

## Archivos de base de datos

- openbiblio.db
- bibliografía abierta.db

## Entidades principales

### Libro

Representa un libro registrado por el usuario.

Campos esperados:

- id
- título
- autor
- ISBN
- género
- estado de lectura
- notas personales

### EstadoLectura

Representa el estado actual del libro:

- Pendiente
- Leyendo
- Finalizado

## Persistencia

El acceso a datos se realiza mediante:

- LibroRepository.java
- SqliteLibroRepository.java
