package com.rasmusrim.flappybird.sprites;

import com.rasmusrim.flappybird.Config;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class Pipes extends Sprite {
    private BufferedImage pipeBodyImage;
    private BufferedImage pipeTipImage;
    private BufferedImage rotatedPipeTipImage;
    private boolean moving = true;

    public Pipes(int pipeGap) {
        loadImages();
        image = createNewPipeImage(pipeGap);
    }

    private Image createNewPipeImage(int pipeGap) {
        double pipeHeight = Math.random() * (Config.sceneHeight - 20) / 2;

        BufferedImage pipe = new BufferedImage(Config.pipeWidth, Config.sceneHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D image = pipe.createGraphics();

        for(int y = 0; y < pipeHeight - pipeBodyImage.getHeight(); y+= pipeBodyImage.getHeight()) {
            image.drawImage(pipeBodyImage, 0, y, null);
        }

        image.drawImage(pipeTipImage, 0, (int)pipeHeight - pipeTipImage.getHeight(), null);

        pipeHeight = Config.sceneHeight - pipeHeight - pipeGap;

        for(int y = Config.sceneHeight; y > Config.sceneHeight - pipeHeight; y-= pipeBodyImage.getHeight()) {
            image.drawImage(pipeBodyImage, 0, y, null);
        }

        image.drawImage(rotatedPipeTipImage, 0, (int)Config.sceneHeight - (int)pipeHeight, null);

        Image pipeImage = SwingFXUtils.toFXImage(pipe, null);

        return pipeImage;
    }

    private void loadImages() {
        try {
            pipeBodyImage = ImageIO.read(new File(getClass().getResource("/pipe-body.png").toURI()));
            pipeTipImage = ImageIO.read(new File(getClass().getResource("/pipe-tip.png").toURI()));
            rotatedPipeTipImage = rotateAndMirror(pipeTipImage);
        } catch (IOException | URISyntaxException e) {
            System.out.println("ERROR: " + e.getMessage());
            System.exit(0);
        }

    }

    public BufferedImage rotateAndMirror(BufferedImage bimg) {

        int w = bimg.getWidth();
        int h = bimg.getHeight();

        BufferedImage rotated = new BufferedImage(w, h, bimg.getType());
        Graphics2D graphic = rotated.createGraphics();
        graphic.rotate(Math.toRadians(180), w/2, h/2);
        graphic.drawImage(bimg, null, 0, 0);
        graphic.drawImage(bimg, 0 + bimg.getWidth(), 0, -bimg.getWidth(), bimg.getHeight(), null);
        graphic.dispose();
        return rotated;
    }

    @Override
    public void update(long now) {
        if (moving) {
            setX(getX() - Config.pipeSpeed);
        }
        super.update(now);
    }

    @Override
    public String getImageName() {
        return null;
    }

    public void stop() {
        moving = false;
    }
}
