package com.openbiblio.service;

import com.openbiblio.model.Libro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class OpenLibraryService {

    public List<Libro> buscarLibros(String busqueda) throws Exception {

        List<Libro> resultados = new ArrayList<>();

        String query = URLEncoder.encode(busqueda, "UTF-8");

        String urlString =
                "https://openlibrary.org/search.json?title="
                        + query
                        + "&limit=5";

        URL url = new URL(urlString);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "UTF-8")
        );

        StringBuilder response = new StringBuilder();
        String line;

        while ((line = in.readLine()) != null) {
            response.append(line);
        }

        in.close();

        String json = response.toString();

        int docsInicio = json.indexOf("\"docs\":[");
        if (docsInicio == -1) {
            return resultados;
        }

        String docs = json.substring(docsInicio);

        String[] bloques = docs.split("\\{\"author");

        for (int i = 1; i < bloques.length && resultados.size() < 5; i++) {
            String bloque = "{\"author" + bloques[i];

            String titulo = extraerValor(bloque, "\"title\":\"");
            String autor = extraerAutor(bloque);
            String isbn = extraerIsbn(bloque);

            if (titulo == null || titulo.trim().isEmpty()) {
                continue;
            }

            if (autor == null || autor.trim().isEmpty()) {
                autor = "Autor desconocido";
            }

            if (isbn == null || isbn.trim().isEmpty()) {
                isbn = "N/A-" + System.currentTimeMillis() + "-" + i;
            }

            resultados.add(new Libro(titulo, autor, isbn));
        }

        return resultados;
    }

    public Libro buscarPrimerResultado(String busqueda) throws Exception {
        List<Libro> libros = buscarLibros(busqueda);

        if (libros.isEmpty()) {
            return null;
        }

        return libros.get(0);
    }

    private String extraerValor(String json, String clave) {
        int inicio = json.indexOf(clave);

        if (inicio == -1) {
            return null;
        }

        inicio += clave.length();

        int fin = json.indexOf("\"", inicio);

        if (fin == -1) {
            return null;
        }

        return limpiarTexto(json.substring(inicio, fin));
    }

    private String extraerAutor(String json) {
        int autores = json.indexOf("\"author_name\":[");

        if (autores == -1) {
            return null;
        }

        int inicio = json.indexOf("\"", autores + 15);

        if (inicio == -1) {
            return null;
        }

        inicio++;

        int fin = json.indexOf("\"", inicio);

        if (fin == -1) {
            return null;
        }

        return limpiarTexto(json.substring(inicio, fin));
    }

    private String extraerIsbn(String json) {
        int isbn = json.indexOf("\"isbn\":[");

        if (isbn == -1) {
            return null;
        }

        int inicio = json.indexOf("\"", isbn + 8);

        if (inicio == -1) {
            return null;
        }

        inicio++;

        int fin = json.indexOf("\"", inicio);

        if (fin == -1) {
            return null;
        }

        return json.substring(inicio, fin);
    }

    private String limpiarTexto(String texto) {
        return texto
                .replace("\\u0026", "&")
                .replace("\\'", "'")
                .replace("\\\"", "\"");
    }
}