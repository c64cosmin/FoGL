package com.stupidrat.tools.fogl;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class SpriteCanvas extends JPanel implements MouseMotionListener {
    public static final int outputSize = 1024;
    private static final long serialVersionUID = -5593088216030819037L;
    private BufferedImage gridImage;
    private Graphics graphics;
    public int cameraX;
    public int cameraY;

    public SpriteCanvas() {
        this.setVisible(true);
        try {
            gridImage = ImageIO.read(new File("res/grid.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Missing grid.png");
        }
    }

    @Override public void update(Graphics g) {
        paint(g);
    }

    @Override public void paint(Graphics g) {
        graphics = g;
        render(g, true);
    }

    public void render(Graphics g, boolean grid) {
        if (grid) {
            g.setColor(Color.darkGray);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            g.drawImage(gridImage, -cameraX * SpriteHandler.instance.zoom, -cameraY * SpriteHandler.instance.zoom, outputSize * SpriteHandler.instance.zoom, outputSize * SpriteHandler.instance.zoom, null);
        }

        for (int i = 0; i < SpriteHandler.instance.spritesImages.size(); i++) {
            int x = SpriteHandler.instance.spritesImagesPosition.get(i).x - cameraX;
            int y = SpriteHandler.instance.spritesImagesPosition.get(i).y - cameraY;
            int w = SpriteHandler.instance.spritesImages.get(i).getWidth();
            int h = SpriteHandler.instance.spritesImages.get(i).getHeight();
            g.drawImage(SpriteHandler.instance.spritesImages.get(i), x * SpriteHandler.instance.zoom, y * SpriteHandler.instance.zoom, w * SpriteHandler.instance.zoom, h * SpriteHandler.instance.zoom, null);
        }

        if (grid) {
            g.setColor(Color.BLUE);
            if (SpriteHandler.instance.getSelectedSprite() != null && SpriteHandler.instance.selectedFrame > 0 && SpriteHandler.instance.getSelectedSprite().frames.size() > 0) {
                String spriteName = SpriteHandler.instance.getSelectedSprite().getName();
                for (int i = 0; i < SpriteHandler.instance.getSelectedSprite().frames.size(); i++) {
                    SpriteHandler.instance.getSelectedSprite().frames.get(i).draw(g, spriteName, cameraX, cameraY, SpriteHandler.instance.zoom, i + 1);
                }
                g.setColor(Color.RED);
                SpriteHandler.instance.getSelectedSprite().frames.get(SpriteHandler.instance.selectedFrame - 1).draw(g, spriteName, cameraX, cameraY, SpriteHandler.instance.zoom, 0);
            }
        }
    }

    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    public int mouseX;
    public int mouseY;

    public void mouseMoved(MouseEvent e) {
        this.mouseX = e.getX();
        this.mouseY = e.getY();
    }
}
