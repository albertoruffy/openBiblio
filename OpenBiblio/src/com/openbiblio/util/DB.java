package com.openbiblio.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class DB {

    private static final String DB_URL = "jdbc:sqlite:openbiblio.db";

    private DB() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void init() {
        // Crea tablas si no existen (id autoincrement + isbn unico)
        String sql = "CREATE TABLE IF NOT EXISTS libros ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "titulo TEXT NOT NULL,"
                + "autor TEXT NOT NULL,"
                + "isbn TEXT NOT NULL UNIQUE,"
                + "genero TEXT,"
                + "estado TEXT NOT NULL,"
                + "notas TEXT"
                + ");";

        try (Connection conn = getConnection();
             Statement st = conn.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Error inicializando la base de datos", e);
        }
    }
}