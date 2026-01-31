package com.openbiblio.repository;

import com.openbiblio.model.EstadoLectura;
import com.openbiblio.model.Libro;
import com.openbiblio.util.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SqliteLibroRepository implements LibroRepository {

    @Override
    public List<Libro> findAll() {
        String sql = "SELECT id, titulo, autor, isbn, genero, estado, notas FROM libros ORDER BY titulo ASC";
        List<Libro> result = new ArrayList<>();

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                result.add(mapRow(rs));
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException("Error leyendo libros", e);
        }
    }

    @Override
    public Optional<Libro> findByIsbn(String isbn) {
        String sql = "SELECT id, titulo, autor, isbn, genero, estado, notas FROM libros WHERE isbn = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, isbn);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error buscando libro por ISBN", e);
        }
    }

    @Override
    public Libro insert(Libro libro) {
        String sql = "INSERT INTO libros (titulo, autor, isbn, genero, estado, notas) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getIsbn());
            ps.setString(4, libro.getGenero());
            ps.setString(5, libro.getEstado().name());
            ps.setString(6, libro.getNotas());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    libro.setId(keys.getLong(1));
                }
            }
            return libro;

        } catch (SQLException e) {
            // Si ISBN duplicado -> constraint UNIQUE
            throw new RuntimeException("Error insertando libro (¿ISBN duplicado?)", e);
        }
    }

    @Override
    public boolean update(Libro libro) {
        String sql = "UPDATE libros SET titulo=?, autor=?, isbn=?, genero=?, estado=?, notas=? WHERE id=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getIsbn());
            ps.setString(4, libro.getGenero());
            ps.setString(5, libro.getEstado().name());
            ps.setString(6, libro.getNotas());
            ps.setLong(7, libro.getId());

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new RuntimeException("Error actualizando libro", e);
        }
    }

    @Override
    public boolean deleteById(long id) {
        String sql = "DELETE FROM libros WHERE id=?";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new RuntimeException("Error borrando libro", e);
        }
    }

    private Libro mapRow(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        String titulo = rs.getString("titulo");
        String autor = rs.getString("autor");
        String isbn = rs.getString("isbn");
        String genero = rs.getString("genero");
        String estadoStr = rs.getString("estado");
        String notas = rs.getString("notas");

        EstadoLectura estado = EstadoLectura.valueOf(estadoStr);
        return new Libro(id, titulo, autor, isbn, genero, estado, notas);
    }
}