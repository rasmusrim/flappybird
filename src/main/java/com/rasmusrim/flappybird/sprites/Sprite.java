package com.rasmusrim.flappybird.sprites;

import com.rasmusrim.flappybird.exceptions.GraphicsContextMissingException;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

import java.awt.*;
import java.awt.geom.Rectangle2D;

abstract public class Sprite {

    private GraphicsContext graphicsContext;
    private float x;
    private float y;
    private int height;
    private int width;

    protected Image image;

    public Sprite() {
        if (getImageName() != null) {
            image = new Image(getClass().getResource(getImageName()).toString());
            setHeight((int) image.getHeight());
            setWidth((int) image.getWidth());
        }
        setX(0);
        setY(0);

    }

    public void update(long now) {
        this.render();
    }

    abstract public String getImageName();

    public void render() {
        if (getGraphicsContext() == null) {
            System.out.println("Graphics context is missing. Did you forget to add it?");
            System.exit(1);

        }
        getGraphicsContext().drawImage(image, x, y, width, height);
    }

    public void render(Image imageToRender) {
        if (getGraphicsContext() == null) {
            System.out.println("Graphics context is missing. Did you forget to add it?");
            System.exit(1);

        }
        getGraphicsContext().drawImage(imageToRender, x, y, width, height);
    }


    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }

    public void setGraphicsContext(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    protected Dimension getImageDimensions() {
        Dimension dimensions = new Dimension((int) image.getWidth(), (int) image.getHeight());
        return dimensions;
    }

    public Rectangle getBounds() {
        Dimension dimensions = getImageDimensions();
        return new Rectangle((int)x, (int)y, (int) dimensions.getWidth(), (int) dimensions.getHeight());
    }

    public boolean collidesWith(Sprite otherSprite) {
        Rectangle thisSpriteBoundaries = getBounds();
        Rectangle otherSpriteBoundaries = otherSprite.getBounds();

        int collidingPixels = 0;

        if (thisSpriteBoundaries.intersects(otherSpriteBoundaries)) {
            Rectangle2D intersection = thisSpriteBoundaries.createIntersection(otherSpriteBoundaries);

            int otherSpriteImageStartY = (int) intersection.getY() - (int) otherSprite.getY();
            int thisSpriteImageStartY = (int) intersection.getY() - (int) getY();

            PixelReader otherSpritePixelReader = otherSprite.getImage().getPixelReader();
            PixelReader thisSpritePixelReader = getImage().getPixelReader();

            for (int x = 0; x < intersection.getWidth(); x++) {
                for (int y = 0; y < intersection.getHeight(); y++) {
                    Color thisSpritePixel = thisSpritePixelReader.getColor(x, y + thisSpriteImageStartY);
                    Color otherSpritePixel = otherSpritePixelReader.getColor((int) (otherSprite.getImage().getWidth() - 1 - x), y + otherSpriteImageStartY);
                    if (thisSpritePixel.getOpacity() == 1 && otherSpritePixel.getOpacity() == 1) {
                        collidingPixels++;
                    }
                }
            }
        }


        return collidingPixels > 5;
    }

    public Image getImage() {
        return image;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }



}
