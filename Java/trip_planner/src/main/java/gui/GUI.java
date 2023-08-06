package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI extends Application {
    //method that starts the code
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 440, 330);
        scene.getStylesheets().add(String.valueOf(GUI.class.getResource("style.css")));
        stage.setTitle("Trip Planner");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}