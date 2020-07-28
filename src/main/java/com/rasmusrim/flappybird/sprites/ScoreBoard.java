package com.rasmusrim.flappybird.sprites;

public class ScoreBoard extends Sprite {

    private int score = 0;

    @Override
    public String getImageName() {
        return null;
    }

    @Override
    public void render() {
        getGraphicsContext().fillText("Score: " + getScore(), getX(), getY());
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
