package com.openbiblio.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.openbiblio.util.DB;



public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        DB.init();
    	Parent root = FXMLLoader.load(getClass().getResource("/com/openbiblio/ui/view/main.fxml"));

        Scene scene = new Scene(root, 900, 600);

        // Cargar CSS (desde resources/css/application.css)
        scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());

        primaryStage.setTitle("OpenBiblio");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}