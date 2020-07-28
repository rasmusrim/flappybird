package com.rasmusrim.flappybird;

import com.rasmusrim.flappybird.controllers.GameController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {

        try {
            loadStage(stage);
            GameController gameController = new GameController(stage);
            gameController.initializeUI();
            gameController.start();
            stage.show();

        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    private Stage loadStage(Stage stage) throws IOException, URISyntaxException {
        URL url = getClass().getResource("/stage.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        Scene scene = new Scene(root, Config.sceneWidth, Config.sceneHeight);

        stage.setTitle("Flappy bird");
        stage.setWidth(Config.sceneWidth);
        stage.setHeight(Config.sceneHeight);
        stage.setScene(scene);

        return stage;
    }
}
