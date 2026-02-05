package com.openbiblio.service;

import com.openbiblio.model.Libro;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CsvExportService {

    public void export(List<Libro> libros, File file) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {

            bw.write("titulo,autor,isbn,genero,estado,notas");
            bw.newLine();

            for (Libro l : libros) {
                bw.write(csv(l.getTitulo()) + "," +
                         csv(l.getAutor()) + "," +
                         csv(l.getIsbn()) + "," +
                         csv(l.getGenero()) + "," +
                         csv(l.getEstado().name()) + "," +
                         csv(l.getNotas()));
                bw.newLine();
            }
        }
    }

    private String csv(String s) {
        if (s == null) return "\"\"";
        return "\"" + s.replace("\"", "\"\"") + "\"";
    }
}