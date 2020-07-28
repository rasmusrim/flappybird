package com.rasmusrim.flappybird.sprites;

import com.rasmusrim.flappybird.Config;

public class Bird extends Sprite {

    private float fallingSpeed = 0;
    private boolean alive = true;

    @Override
    public String getImageName() {
        return "/bird.png";
    }

    public void flapWings() {
        if (canFlapWings()) {
            fallingSpeed = -Config.flapStrength;
        }
    }

    @Override
    public void update(long now) {
        fallingSpeed += Config.gravity;

        setY(getY() + fallingSpeed);
        super.update(now);
    }

    public void die() {
        flapWings();
        alive = false;

    }

    private boolean canFlapWings() {
        return alive && getY() > 0;
    }
}
