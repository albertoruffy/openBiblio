package com.openbiblio.util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class DB {

    private static final String APP_FOLDER =
            System.getProperty("user.home") + File.separator + ".openbiblio";

    private static final String DB_PATH =
            APP_FOLDER + File.separator + "openbiblio.db";

    private static final String DB_URL = "jdbc:sqlite:" + DB_PATH;

    private DB() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void init() {
        ensureFolderExists();

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

    private static void ensureFolderExists() {
        File dir = new File(APP_FOLDER);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new RuntimeException(
                        "No se pudo crear la carpeta local de OpenBiblio: " + dir.getAbsolutePath()
                );
            }
        }
    }

    // Útil para depuración o mostrar en pantalla
    public static String getDatabasePath() {
        return DB_PATH;
    }
}