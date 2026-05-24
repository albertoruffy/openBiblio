package com.openbiblio.ui.controller;

import com.openbiblio.model.EstadoLectura;
import com.openbiblio.model.Libro;
import com.openbiblio.repository.LibroRepository;
import com.openbiblio.repository.SqliteLibroRepository;
import com.openbiblio.service.CsvExportService;
import com.openbiblio.service.CsvImportService;
import com.openbiblio.service.OpenLibraryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import java.util.List;
import java.io.File;
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
        libros.setAll(repo.buscar());
        renderLibraryMiniCards();
        renderRecommendationCards();
    }


    @FXML
    private void onExportClicked() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Exportar biblioteca");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV (*.csv)", "*.csv"));
        fc.setInitialFileName("openbiblio.csv");

        File file = fc.showSaveDialog(addButton.getScene().getWindow());
        if (file == null) return;

        try {
            new CsvExportService().export(repo.buscar(), file);
            showInfo("Exportación completada", "Archivo guardado en:\n" + file.getAbsolutePath());
        } catch (Exception e) {
            showError("Error exportando", e.getMessage());
        }
    }
    @FXML
    private void onAddClicked() {
        Dialog<Libro> dialog = new Dialog<>();
        dialog.setTitle("Añadir libro");
        dialog.setHeaderText("Introduce los datos del libro");

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);

        TextField tfTitulo = new TextField();
        TextField tfAutor = new TextField();
        TextField tfIsbn = new TextField();

        Button buscarOnlineButton = new Button("Buscar online");
        buscarOnlineButton.getStyleClass().add("primary-button");

        buscarOnlineButton.setOnAction(e -> {
            String busqueda = tfTitulo.getText();

            if (isBlank(busqueda)) {
                showError("Búsqueda vacía", "Escribe primero un título para buscar online.");
                return;
            }

            try {
            	List<Libro> encontrados = new OpenLibraryService().buscarLibros(busqueda);

            	if (encontrados.isEmpty()) {
            	    showError("Sin resultados", "No se encontró ningún libro con ese título.");
            	    return;
            	}

            	ChoiceDialog<Libro> selector = new ChoiceDialog<>(encontrados.get(0), encontrados);
            	selector.setTitle("Resultados encontrados");
            	selector.setHeaderText("Selecciona el libro correcto");
            	selector.setContentText("Libro:");

            	Optional<Libro> seleccionado = selector.showAndWait();

            	if (!seleccionado.isPresent()) {
            	    return;
            	}

            	Libro encontrado = seleccionado.get();

            	tfTitulo.setText(encontrado.getTitulo());
            	tfAutor.setText(encontrado.getAutor());
            	tfIsbn.setText(encontrado.getIsbn());

            } catch (Exception ex) {
                ex.printStackTrace();
                showError("Error de conexión", "No se pudo consultar Open Library:\n" + ex.getMessage());
            }
        });

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
        grid.add(buscarOnlineButton, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(btn -> {
            if (btn == ButtonType.OK) {
                return new Libro(tfTitulo.getText(), tfAutor.getText(), tfIsbn.getText());
            }
            return null;
        });

        Optional<Libro> result = dialog.showAndWait();
        if (!result.isPresent()) return;

        Libro libro = result.get();

        if (isBlank(libro.getTitulo()) || isBlank(libro.getAutor()) || isBlank(libro.getIsbn())) {
            showError("Campos obligatorios", "Título, autor e ISBN no pueden estar vacíos.");
            return;
        }

        try {
            repo.insertar(libro);
        } catch (RuntimeException e) {
            showError("Error", "No se pudo guardar el libro (ISBN duplicado).");
            return;
        }

        refresh();
    }
    @FXML
    private void onImportClicked() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Importar biblioteca");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV (*.csv)", "*.csv"));

        File file = fc.showOpenDialog(addButton.getScene().getWindow());
        if (file == null) return;

        try {
            int n = new CsvImportService().importToRepository(file, repo);
            refresh();
            showInfo("Importación completada", "Libros importados/actualizados: " + n);
        } catch (Exception e) {
            showError("Error importando", e.getMessage());
        }
    }

    /* =========================
       UI
       ========================= */

    private void refresh() {
        libros.setAll(repo.buscar());
        renderLibraryMiniCards();
    }

    private void renderLibraryMiniCards() {
        libraryPane.getChildren().clear();

        if (libros.isEmpty()) {
            libraryPane.getChildren().add(new Label("No hay libros todavía"));
            return;
        }

        for (Libro libro : libros) {
            libraryPane.getChildren().add(makeMiniCover(libro));
        }
    }

    private VBox makeMiniCover(Libro libro) {

        VBox card = new VBox(8);
        card.setPrefWidth(180);

        card.getStyleClass().add("book-card");

        Label titulo = new Label(shorten(libro.getTitulo(), 35));
        titulo.setWrapText(true);
        titulo.getStyleClass().add("book-title");

        Label autor = new Label(libro.getAutor());
        autor.getStyleClass().add("book-author");

        Label isbn = new Label("ISBN: " + libro.getIsbn());
        isbn.getStyleClass().add("book-meta");

        Label estado = new Label(libro.getEstado().toString());

        if (libro.getEstado() == EstadoLectura.LEIDO) {
            estado.getStyleClass().add("status-read");
        } else {
            estado.getStyleClass().add("status-pending");
        }

        card.getChildren().addAll(
                titulo,
                autor,
                isbn,
                estado
        );

        card.setOnMouseClicked(e -> openEditDialog(libro));

        return card;
    }

    private void openEditDialog(Libro libro) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Editar libro");
        dialog.setHeaderText(libro.getTitulo());

        ButtonType deleteBtn = new ButtonType("ELIMINAR", ButtonBar.ButtonData.LEFT);
        dialog.getDialogPane().getButtonTypes().addAll(deleteBtn, ButtonType.CANCEL, ButtonType.OK);

        ComboBox<EstadoLectura> estadoBox = new ComboBox<>();
        estadoBox.getItems().addAll(EstadoLectura.PENDIENTE, EstadoLectura.LEIDO);
        estadoBox.setValue(libro.getEstado());

        TextArea notasArea = new TextArea(libro.getNotas() == null ? "" : libro.getNotas());
        notasArea.setPrefRowCount(6);

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
            if (btn == ButtonType.OK) return "SAVE";
            if (btn == deleteBtn) return "DELETE";
            return "CANCEL";
        });

        Optional<String> result = dialog.showAndWait();
        if (!result.isPresent()) return;

        if ("DELETE".equals(result.get())) {
            // Confirmacion
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                    "¿Seguro que quieres eliminar este libro?\n\n" + libro.getTitulo(),
                    ButtonType.CANCEL, ButtonType.OK);
            confirm.setTitle("Confirmar eliminación");
            confirm.setHeaderText("Eliminar libro");

            Optional<ButtonType> c = confirm.showAndWait();
            if (c.isPresent() && c.get() == ButtonType.OK) {
                try {
                    repo.deleteById(libro.getId());
                } catch (RuntimeException e) {
                    showError("Error eliminando", e.getMessage());
                    return;
                }
                refresh();
            }
            return;
        }

        if ("SAVE".equals(result.get())) {
            libro.setEstado(estadoBox.getValue());
            libro.setNotas(notasArea.getText());

            try {
                repo.update(libro);
            } catch (RuntimeException e) {
                showError("Error guardando cambios", e.getMessage());
                return;
            }

            refresh();
        }
    }

    private void renderRecommendationCards() {
        recommendationsPane.getChildren().clear();
        recommendationsPane.getChildren().add(new Label("Próximamente..."));
    }

    /* =========================
       HELPERS
       ========================= */

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private String shorten(String s, int max) {
        if (s == null) return "";
        return s.length() <= max ? s : s.substring(0, max - 1) + "…";
    }

    private void showError(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        a.setTitle(title);
        a.setHeaderText(title);
        a.showAndWait();
    }

    private void showInfo(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        a.setTitle(title);
        a.setHeaderText(title);
        a.showAndWait();
    }
}