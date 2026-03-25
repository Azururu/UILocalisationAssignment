package com.myapp.uilocalisationassignment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;

public class AppGUI extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        URL fxmlURL = getClass().getResource("/com/myapp/uilocalisationassignment/calculate-app.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);

        VBox root = fxmlLoader.load();

        Scene scene = new Scene(root, 400, 300);

        stage.setTitle("Fuel Cost Calculator");
        stage.setScene(scene);
        stage.show();
    }
}
