package com.rasmusrim.flappybird.controllers;

import com.rasmusrim.flappybird.Config;
import com.rasmusrim.flappybird.sprites.Bird;
import com.rasmusrim.flappybird.sprites.Pipes;
import com.rasmusrim.flappybird.sprites.ScoreBoard;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PipeController {
    private GraphicsContext graphicsContext;
    private long lastTimePipeWasCreated = 0;
    private List<Pipes> pipesList = new ArrayList<>();
    private boolean moving = true;
    private int pipeGap = Config.initialPipeGap;
    private ScoreBoard scoreBoard;

    public PipeController(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;


    }

    public void update(long l) {
        if (moving) {
            if (l - lastTimePipeWasCreated > Config.pipeFrequency) {
                Pipes pipes = new Pipes(pipeGap);
                pipes.setX(Config.sceneWidth);
                pipes.setHeight(Config.sceneHeight);
                pipes.setWidth(Config.pipeWidth);
                pipes.setGraphicsContext(graphicsContext);
                this.pipesList.add(pipes);
                lastTimePipeWasCreated = l;

                pipeGap -= Config.pipeGapReduction;
            }
        }

        Iterator<Pipes> i = pipesList.iterator();
        while (i.hasNext()) {
            Pipes pipes = i.next(); // must be called before you can call i.remove()
            pipes.update(l);

            if (pipes.getX() < -pipes.getWidth()) {
                i.remove();
                scoreBoard.setScore(scoreBoard.getScore() + 1);
            }
        }



    }

    public boolean isCollision(Bird bird) {
        for (Pipes pipes : pipesList) {
            if (pipes.collidesWith(bird)) {
                return true;
            }
        }

        return false;
    }

    public void stop() {
        moving = false;

        pipesList.forEach(pipes -> {
            pipes.stop();
        });

    }

    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }

    public void setScoreBoard(ScoreBoard scoreBoard) {
        this.scoreBoard = scoreBoard;
    }
}
