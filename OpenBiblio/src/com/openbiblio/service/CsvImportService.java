package com.openbiblio.service;

import com.openbiblio.model.EstadoLectura;
import com.openbiblio.model.Libro;
import com.openbiblio.repository.LibroRepository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CsvImportService {

    public int importToRepository(File file, LibroRepository repo) throws IOException {
        List<String[]> rows = readCsv(file);

        int importedOrUpdated = 0;

        // Saltar cabecera si existe
        for (int i = 0; i < rows.size(); i++) {
            String[] r = rows.get(i);
            if (i == 0 && r.length > 0 && "titulo".equalsIgnoreCase(clean(r[0]))) {
                continue;
            }

            // Esperamos: titulo,autor,isbn,genero,estado,notas
            String titulo = get(r, 0);
            String autor  = get(r, 1);
            String isbn   = get(r, 2);

            if (isBlank(titulo) || isBlank(autor) || isBlank(isbn)) continue;

            String genero = get(r, 3);
            String estadoStr = get(r, 4);
            String notas  = get(r, 5);

            EstadoLectura estado = EstadoLectura.PENDIENTE;
            if (!isBlank(estadoStr)) {
                try {
                    estado = EstadoLectura.valueOf(estadoStr.trim().toUpperCase());
                } catch (Exception ignored) {}
            }

            Libro libro = new Libro(0, titulo, autor, isbn, genero, estado, notas);

            Optional<Libro> existing = repo.findByIsbn(isbn);
            if (existing.isPresent()) {
                libro.setId(existing.get().getId());
                repo.update(libro);
            } else {
                repo.insert(libro);
            }

            importedOrUpdated++;
        }

        return importedOrUpdated;
    }

    /* =========================
       CSV parsing (comillas)
       ========================= */

    private List<String[]> readCsv(File file) throws IOException {
        List<String[]> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                rows.add(parseCsvLine(line));
            }
        }
        return rows;
    }

    // Parser simple compatible con lo que exportamos (valores entre comillas)
    private String[] parseCsvLine(String line) {
        List<String> out = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    cur.append('"'); // escape ""
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                out.add(cur.toString());
                cur.setLength(0);
            } else {
                cur.append(c);
            }
        }
        out.add(cur.toString());
        return out.toArray(new String[0]);
    }

    private String get(String[] r, int idx) {
        if (r == null || idx < 0 || idx >= r.length) return "";
        return clean(r[idx]);
    }

    private String clean(String s) {
        return s == null ? "" : s.trim();
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}