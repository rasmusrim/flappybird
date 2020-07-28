package com.rasmusrim.flappybird.sprites;

import com.rasmusrim.flappybird.Config;

public class Background extends Sprite {

    private long startTime;
    private boolean moving = true;

    @Override
    public String getImageName() {
        return "/background.png";
    }

    @Override
    public void update(long now) {
        if (moving) {
            if (startTime == 0) {
                startTime = now;
            }

            if (getX() < getWidth() * -1) {
                setX(getWidth());

            }

            setX(getX() - Config.backgroundSpeed);

        }
        super.update(now);

    }

    public void stop() {
        moving = false;
    }
}
