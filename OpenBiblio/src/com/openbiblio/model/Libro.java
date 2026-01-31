package com.openbiblio.model;

import java.util.Objects;

public class Libro {

    private long id;                 // SQLite AUTOINCREMENT
    private String titulo;
    private String autor;
    private String isbn;             // idealmente unico
    private String genero;
    private EstadoLectura estado;    // PENDIENTE / LEIDO
    private String notas;

    // Constructor vacío (útil para frameworks, tablas, etc.)
    public Libro() {
        this.estado = EstadoLectura.PENDIENTE;
    }

    // Constructor típico para crear un libro nuevo (sin id todavía)
    public Libro(String titulo, String autor, String isbn) {
        this(0, titulo, autor, isbn, null, EstadoLectura.PENDIENTE, null);
    }

    // Constructor completo
    public Libro(long id, String titulo, String autor, String isbn, String genero,
                 EstadoLectura estado, String notas) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.genero = genero;
        this.estado = (estado == null) ? EstadoLectura.PENDIENTE : estado;
        this.notas = notas;
    }

    // Getters / Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public EstadoLectura getEstado() { return estado; }
    public void setEstado(EstadoLectura estado) {
        this.estado = (estado == null) ? EstadoLectura.PENDIENTE : estado;
    }

    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }

    // equals/hashCode: por ISBN (si existe); si no, por id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Libro)) return false;
        Libro libro = (Libro) o;

        if (isbn != null && !isbn.trim().isEmpty() &&
            libro.isbn != null && !libro.isbn.trim().isEmpty()) {
            return isbn.trim().equalsIgnoreCase(libro.isbn.trim());
        }
        return id != 0 && id == libro.id;
    }

    @Override
    public int hashCode() {
        if (isbn != null && !isbn.trim().isEmpty()) {
            return Objects.hash(isbn.trim().toLowerCase());
        }
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", isbn='" + isbn + '\'' +
                ", genero='" + genero + '\'' +
                ", estado=" + estado +
                '}';
    }
}