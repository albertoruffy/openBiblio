package com.openbiblio.ui.controller;

import com.openbiblio.model.EstadoLectura;
import com.openbiblio.model.Libro;
import com.openbiblio.repository.LibroRepository;
import com.openbiblio.repository.SqliteLibroRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Optional;

public class MainController {

    @FXML private TextField searchField;
    @FXML private Button addButton;

    @FXML private FlowPane libraryPane;
    @FXML private FlowPane recommendationsPane;

    private final LibroRepository repo = new SqliteLibroRepository();
    private final ObservableList<Libro> libros = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        libros.setAll(repo.findAll());
        renderLibraryMiniCards();
        renderRecommendationCards();
    }

    /* =========================================================
       AÑADIR LIBRO
       ========================================================= */

    @FXML
    private void onAddClicked() {
        Dialog<Libro> dialog = new Dialog<>();
        dialog.setTitle("Añadir libro");
        dialog.setHeaderText("Introduce los datos del libro");

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);

        TextField tfTitulo = new TextField();
        TextField tfAutor = new TextField();
        TextField tfIsbn = new TextField();

        tfTitulo.setPromptText("Título");
        tfAutor.setPromptText("Autor");
        tfIsbn.setPromptText("ISBN");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        grid.add(new Label("Título:"), 0, 0);
        grid.add(tfTitulo, 1, 0);
        grid.add(new Label("Autor:"), 0, 1);
        grid.add(tfAutor, 1, 1);
        grid.add(new Label("ISBN:"), 0, 2);
        grid.add(tfIsbn, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                return new Libro(tfTitulo.getText(), tfAutor.getText(), tfIsbn.getText());
            }
            return null;
        });

        Optional<Libro> result = dialog.showAndWait();
        if (!result.isPresent()) return;

        Libro nuevo = result.get();

        if (isBlank(nuevo.getTitulo()) || isBlank(nuevo.getAutor()) || isBlank(nuevo.getIsbn())) {
            showError("Campos obligatorios", "Título, autor e ISBN no pueden estar vacíos.");
            return;
        }

        try {
            repo.insert(nuevo);
        } catch (RuntimeException e) {
            showError("Error al guardar", "No se pudo guardar el libro.\n¿Puede que el ISBN ya exista?");
            return;
        }

        libros.setAll(repo.findAll());
        renderLibraryMiniCards();
    }

    /* =========================================================
       RENDER LIBRERÍA
       ========================================================= */

    private void renderLibraryMiniCards() {
        libraryPane.getChildren().clear();

        if (libros.isEmpty()) {
            Label empty = new Label("No hay libros todavía");
            empty.setStyle("-fx-text-fill: #666;");
            libraryPane.getChildren().add(empty);
            return;
        }

        for (Libro libro : libros) {
            libraryPane.getChildren().add(makeMiniCover(libro));
        }
    }

    private StackPane makeMiniCover(Libro libro) {
        StackPane card = new StackPane();
        card.setPrefSize(90, 130);
        card.setMinSize(90, 130);
        card.setMaxSize(90, 130);

        // Color según estado
        String color = libro.getEstado() == EstadoLectura.LEIDO ? "#c8e6c9" : "#eeeeee";
        card.setStyle("-fx-background-color: " + color +
                "; -fx-border-color: #cfcfcf; -fx-border-width: 1;");

        Label lbl = new Label(shorten(libro.getTitulo(), 18));
        lbl.setWrapText(true);
        lbl.setStyle("-fx-font-size: 11px; -fx-padding: 8;");

        card.getChildren().add(lbl);

        // Click -> editar libro
        card.setOnMouseClicked(e -> openEditDialog(libro));

        return card;
    }

    /* =========================================================
       EDITAR LIBRO (ESTADO + NOTAS)
       ========================================================= */

    private void openEditDialog(Libro libro) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Editar libro");
        dialog.setHeaderText(libro.getTitulo());

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);

        ComboBox<EstadoLectura> estadoBox = new ComboBox<>();
        estadoBox.getItems().addAll(EstadoLectura.PENDIENTE, EstadoLectura.LEIDO);
        estadoBox.setValue(libro.getEstado());

        TextArea notasArea = new TextArea();
        notasArea.setPromptText("Notas personales sobre el libro...");
        notasArea.setWrapText(true);
        notasArea.setPrefRowCount(6);
        notasArea.setText(libro.getNotas() == null ? "" : libro.getNotas());

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        grid.add(new Label("Estado:"), 0, 0);
        grid.add(estadoBox, 1, 0);
        grid.add(new Label("Notas:"), 0, 1);
        grid.add(notasArea, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(btn -> {
            if (btn == ButtonType.OK) {
                libro.setEstado(estadoBox.getValue());
                libro.setNotas(notasArea.getText());

                try {
                    repo.update(libro);
                } catch (RuntimeException e) {
                    showError("Error guardando cambios", e.getMessage());
                }
            }
            return null;
        });

        dialog.showAndWait();

        libros.setAll(repo.findAll());
        renderLibraryMiniCards();
    }

    /* =========================================================
       RECOMENDACIONES (MOCK)
       ========================================================= */

    private void renderRecommendationCards() {
        recommendationsPane.getChildren().clear();
        recommendationsPane.getChildren().add(makeBigCover("Harry Potter y la piedra filosofal"));
        recommendationsPane.getChildren().add(makeBigCover("Cruce de caminos"));
        recommendationsPane.getChildren().add(makeBigCover("Los hombres del norte"));
    }

    private VBox makeBigCover(String title) {
        StackPane cover = new StackPane();
        cover.setPrefSize(180, 240);
        cover.setStyle("-fx-background-color: #e1e1e1; -fx-border-color: #cfcfcf;");

        Label lbl = new Label(shorten(title, 30));
        lbl.setWrapText(true);
        lbl.setStyle("-fx-font-size: 12px; -fx-padding: 10;");

        cover.getChildren().add(lbl);

        VBox box = new VBox(8);
        box.getChildren().add(cover);
        return box;
    }

    /* =========================================================
       UTILIDADES
       ========================================================= */

    private String shorten(String s, int max) {
        if (s == null) return "";
        String t = s.trim();
        return t.length() <= max ? t : t.substring(0, max - 1) + "…";
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}