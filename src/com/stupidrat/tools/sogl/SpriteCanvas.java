package com.stupidrat.tools.sogl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class SpriteCanvas extends JPanel implements MouseMotionListener, MouseListener, MouseInputListener {
    public static final int outputSize = 4096;

    private SpriteHandler handler;
    private BufferedImage gridImage;
    private Graphics graphics;
    public int mouseX;
    public int mouseY;
    private boolean mouseInside;
    private boolean mouseOn;

    public AnimationPreviewRenderer preview;

    public SpriteCanvas(SpriteHandler handler) {
        this.setVisible(true);
        this.handler = handler;
        this.preview = new AnimationPreviewRenderer(this, handler);
        try {
            gridImage = ImageIO.read(new File("res/grid.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Missing grid.png");
        }
    }

    @Override
    public void update(Graphics g) {
        this.preview.paint(g);
        paint(g);
    }

    @Override
    public void paint(Graphics g) {
        graphics = g;
        render(g, SpriteHandler.instance.sheet, true, handler.cameraX, handler.cameraY, handler.zoom);
        this.preview.paint(g);
    }

    public void render(Graphics g, SpriteSheet sheet, boolean grid, int cameraX, int cameraY, int zoom) {
        if (grid) {
            g.setColor(Color.darkGray);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            g.drawImage(gridImage, -cameraX * zoom, -cameraY * zoom,
                    outputSize * zoom, outputSize * zoom, null);
        }

        for (int i = 0; i < sheet.spritesImages.size(); i++) {
            int x = sheet.spritesImagesPosition.get(i).x - cameraX;
            int y = sheet.spritesImagesPosition.get(i).y - cameraY;
            int w = sheet.spritesImages.get(i).getWidth();
            int h = sheet.spritesImages.get(i).getHeight();
            g.drawImage(sheet.spritesImages.get(i), x * zoom, y * zoom, w * zoom, h * zoom,
                    null);
        }

        if (grid) {
            g.setColor(Color.BLUE);
            if (sheet.sprites.getSelectedSprite() != null && SpriteHandler.instance.selectedFrame > 0
                    && sheet.sprites.getSelectedSprite().frames.size() > 0) {
                String spriteName = sheet.sprites.getSelectedSprite().getName();
                for (int i = 0; i < sheet.sprites.getSelectedSprite().frames.size(); i++) {
                    sheet.sprites.getSelectedSprite().frames.get(i).drawFrameDecorator(g, spriteName, cameraX, cameraY,
                            zoom, i + 1);
                }
                g.setColor(Color.RED);
                sheet.sprites.getSelectedSprite().frames.get(SpriteHandler.instance.selectedFrame - 1).drawFrameDecorator(g,
                        spriteName, cameraX, cameraY, zoom, 0);
            }
        }
    }

    public void mouseDragged(MouseEvent e) {

    }

    public void doMouseMove() {
        if (!mouseInside)
            return;

        if (handler.isKeyPressed == 'q')
            handler.setFramePosition(this.mouseX, this.mouseY);
        if (handler.isKeyPressed == 'e')
            handler.setFrameSize(this.mouseX, this.mouseY);
        if (handler.isKeyPressed == 'c')
            handler.setFrameCenter(this.mouseX, this.mouseY);
        if (handler.isKeyPressed == 'p')
            preview.setPosition(this.mouseX, this.mouseY);
    }

    public void mouseMoved(MouseEvent e) {
        this.mouseX = e.getX();
        this.mouseY = e.getY();
        doMouseMove();
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {
        mouseInside = true;
    }

    public void mouseExited(MouseEvent e) {
        mouseInside = false;
    }

    public void mousePressed(MouseEvent e) {
        this.mouseOn = true;
    }

    public void mouseReleased(MouseEvent e) {
        this.mouseOn = false;
    }
}
