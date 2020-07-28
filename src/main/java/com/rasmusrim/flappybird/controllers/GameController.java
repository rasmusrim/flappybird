package com.rasmusrim.flappybird.controllers;

import com.rasmusrim.flappybird.Config;
import com.rasmusrim.flappybird.sprites.Background;
import com.rasmusrim.flappybird.sprites.Bird;
import com.rasmusrim.flappybird.sprites.ScoreBoard;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class GameController extends AnimationTimer {
    private Stage stage;
    private Bird bird;
    private Background background;
    private Background background2;
    private PipeController pipeController;


    @FXML
    private Canvas mainCanvas;
    private ScoreBoard scoreBoard;

    public GameController() {
        // Needed for javafx to work.
    }

    public GameController(Stage stage) {
        setStage(stage);
    }

    public void initializeUI() {
        initializeBackground();
        initializeBird();
        initializeEvents();
        initializeScoreBoard();
        pipeController = new PipeController(getGraphicsContext());
        pipeController.setScoreBoard(scoreBoard);
    }

    private void initializeScoreBoard() {
        scoreBoard = new ScoreBoard();
        scoreBoard.setGraphicsContext(getGraphicsContext());
        scoreBoard.setX(30);
        scoreBoard.setY(50);
        scoreBoard.render();
    }

    private void initializeEvents() {
        Scene scene = getStage().getScene();
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.SPACE)) {
                bird.flapWings();
            }
        });

    }

    @Override
    public void handle(long l) {
        l = l / 1000_000;

        background.update(l);
        background2.update(l);
        pipeController.update(l);
        bird.update(l);
        scoreBoard.update(l);

        if (pipeController.isCollision(bird)) {
            gameOver();
        };
    }

    private void gameOver() {
        bird.die();
        background.stop();
        background2.stop();

        pipeController.stop();
    }

    private void initializeBackground() {
        background = new Background();
        background.setGraphicsContext(getGraphicsContext());
        background.render();

        background2 = new Background();
        background2.setGraphicsContext(getGraphicsContext());
        background2.setX(background.getWidth());
        background2.render();

    }

    private void initializeBird() {

        bird = new Bird();
        bird.setX(150);
        bird.setY((int) Config.sceneHeight / 2 - bird.getHeight() / 2);

        bird.setGraphicsContext(getGraphicsContext());
        bird.render();
    }

    private GraphicsContext getGraphicsContext() {
        Canvas canvas = (Canvas) stage.getScene().lookup("#mainCanvas");
        return canvas.getGraphicsContext2D();

    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}

