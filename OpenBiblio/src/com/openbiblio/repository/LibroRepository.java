package com.openbiblio.repository;

import com.openbiblio.model.Libro;

import java.util.List;
import java.util.Optional;

public interface LibroRepository {

    List<Libro> buscar();

    Optional<Libro> buscaPorIsbn(String isbn);

    Libro insertar(Libro libro);

    boolean update(Libro libro);

    boolean deleteById(long id);
}