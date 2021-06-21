package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Main extends Application {
    private Stage primaryStage;

    public static void main(String[] args) {
        Application.launch(args);
    }

    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setTitle("Parsing Products");
        initRootLayout();
    }

    private void initRootLayout() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/main.fxml"));
        Parent parent = fxmlLoader.load();

        Scene scene = new Scene(parent);
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
