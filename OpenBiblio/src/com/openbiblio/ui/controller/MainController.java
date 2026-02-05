package com.openbiblio.ui.controller;

import com.openbiblio.model.Libro;
import com.openbiblio.repository.LibroRepository;
import com.openbiblio.repository.SqliteLibroRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainController {

    @FXML private TextField searchField;
    @FXML private Button addButton;

    @FXML private FlowPane libraryPane;
    @FXML private FlowPane recommendationsPane;

    private final LibroRepository repo = new SqliteLibroRepository();
    private final ObservableList<Libro> libros = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Cargar libros desde SQLite
        libros.setAll(repo.findAll());

        // Renderizar panel izquierdo (tu biblioteca)
        renderLibraryMiniCards();

        // Renderizar recomendaciones (mock por ahora)
        renderRecommendationCards();
    }

    @FXML
    private void onAddClicked() {
        System.out.println("Añadir libro (pendiente)...");
        // Paso 5: aquí abriremos el diálogo real y luego refrescamos:
        // libros.setAll(repo.findAll());
        // renderLibraryMiniCards();
    }

    private void renderLibraryMiniCards() {
        libraryPane.getChildren().clear();

        if (libros.isEmpty()) {
            Label empty = new Label("No hay libros todavía");
            empty.setStyle("-fx-text-fill: #666;");
            libraryPane.getChildren().add(empty);
            return;
        }

        for (Libro l : libros) {
            libraryPane.getChildren().add(makeMiniCover(l.getTitulo()));
        }
    }

    private void renderRecommendationCards() {
        recommendationsPane.getChildren().clear();

        recommendationsPane.getChildren().add(makeBigCover("Harry Potter y la piedra filosofal"));
        recommendationsPane.getChildren().add(makeBigCover("Cruce de caminos"));
        recommendationsPane.getChildren().add(makeBigCover("Los hombres del norte"));
    }

    private StackPane makeMiniCover(String title) {
        StackPane card = new StackPane();
        card.setPrefSize(90, 130);
        card.setMinSize(90, 130);
        card.setMaxSize(90, 130);
        card.setStyle("-fx-background-color: #dddddd; -fx-border-color: #cfcfcf; -fx-border-width: 1;");

        Label lbl = new Label(shorten(title, 18));
        lbl.setWrapText(true);
        lbl.setStyle("-fx-font-size: 11px; -fx-padding: 8;");

        card.getChildren().add(lbl);
        return card;
    }

    private VBox makeBigCover(String title) {
        StackPane cover = new StackPane();
        cover.setPrefSize(180, 240);
        cover.setMinSize(180, 240);
        cover.setMaxSize(180, 240);
        cover.setStyle("-fx-background-color: #e1e1e1; -fx-border-color: #cfcfcf; -fx-border-width: 1;");

        Label lbl = new Label(shorten(title, 30));
        lbl.setWrapText(true);
        lbl.setStyle("-fx-font-size: 12px; -fx-padding: 10;");

        cover.getChildren().add(lbl);

        VBox box = new VBox(8);
        box.getChildren().add(cover);
        return box;
    }

    private String shorten(String s, int max) {
        if (s == null) return "";
        String t = s.trim();
        return t.length() <= max ? t : t.substring(0, max - 1) + "…";
    }
}