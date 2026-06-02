# Casos de uso

## Registrar libro

1. El usuario abre la aplicación.
2. Selecciona la opción para añadir libro.
3. Introduce título, autor, ISBN, género y estado de lectura.
4. Guarda el registro.
5. La aplicación valida la información
6. El libro queda almacenado en SQLite.

## Buscar libro

1. El usuario introduce un criterio de búsqueda.
2. La aplicación filtra por título, autor, género o ISBN.
3. Se muestran los resultados.

## Importar colección 

1. El usuario selecciona un archivo CSV.
2. CsvImportService procesa el archivo..
3. Los libros se incorporan a la biblioteca.

## Exportar Colección

1. El usuario solicita una exportación.
2. CsvExportService genera un CSV.
3. El archivo puede guardarse o compartirse.

## Consultar Open Library

1.El usuario introduce un ISBN.
2. OpenLibraryService consulta la API
3. Se recupera información bibliográfica.

## Crear copia de seguridad

1. El usuario configura un servidor FTP.
2. FtpBackupService genera el respaldo.
3. El archivo se almacena remotamente..

8.
9. as.
