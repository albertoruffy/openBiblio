package com.openbiblio.ui.controller;

import com.openbiblio.model.EstadoLectura;
import com.openbiblio.model.Libro;
import com.openbiblio.repository.LibroRepository;
import com.openbiblio.repository.SqliteLibroRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainController {

    @FXML private TextField searchField;
    @FXML private Button addButton;

    @FXML private TableView<Libro> tablaLibros;

    @FXML private TableColumn<Libro, String> colTitulo;
    @FXML private TableColumn<Libro, String> colAutor;
    @FXML private TableColumn<Libro, String> colIsbn;
    @FXML private TableColumn<Libro, EstadoLectura> colEstado;

    private final LibroRepository repo = new SqliteLibroRepository();
    private final ObservableList<Libro> libros = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        colIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        // Si está vacío, metemos datos de prueba una vez
        if (repo.findAll().isEmpty()) {
            repo.insert(new Libro("Clean Code", "Robert C. Martin", "9780132350884"));
            repo.insert(new Libro("Effective Java", "Joshua Bloch", "9780134685991"));
        }

        // Cargar desde SQLite
        libros.setAll(repo.findAll());
        tablaLibros.setItems(libros);
    }

    @FXML
    private void onAddClicked() {
        System.out.println("Añadir libro (pendiente)...");
    }
}