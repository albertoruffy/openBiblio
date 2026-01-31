package com.openbiblio.repository;

import com.openbiblio.model.Libro;

import java.util.List;
import java.util.Optional;

public interface LibroRepository {

    List<Libro> findAll();

    Optional<Libro> findByIsbn(String isbn);

    Libro insert(Libro libro);

    boolean update(Libro libro);

    boolean deleteById(long id);
}