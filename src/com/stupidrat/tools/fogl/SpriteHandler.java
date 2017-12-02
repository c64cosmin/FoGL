package com.stupidrat.tools.fogl;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class SpriteHandler implements ActionListener, KeyListener, ItemListener {
    public static SpriteHandler instance;
    public ArrayList<MyButton> buttons;
    protected int zoom;

    public SpriteHandler() {
        SpriteHandler.instance = this;

        buttons = new ArrayList<MyButton>();
        buttons.add(new MyButton("+", new Runnable() {
            @Override
            public void run() {
                SpriteHandler.instance.zoomIn();
            }
        }));
        zoomInButton = buttons.get(buttons.size() - 1);

        buttons.add(new MyButton("-", new Runnable() {
            @Override
            public void run() {
                SpriteHandler.instance.zoomOut();
            }
        }));
        zoomOutButton = buttons.get(buttons.size() - 1);

        buttons.add(new MyButton("Add image", new Runnable() {
            @Override
            public void run() {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showSaveDialog(window) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();

                    SpriteHandler.instance.addImage(file);
                }
            }
        }));
        addImageButton = buttons.get(buttons.size() - 1);

        buttons.add(new MyButton("Del image", new Runnable() {
            @Override
            public void run() {
                int index = imagesCombo.getSelectedIndex();
                SpriteHandler.instance.removeImage(index);
            }
        }));
        delImageButton = buttons.get(buttons.size() - 1);

        buttons.add(new MyButton("Open sheet", new Runnable() {
            @Override
            public void run() {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showSaveDialog(window) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();

                    SpriteSheet.openSheet(file.getAbsolutePath());
                }
            }
        }));
        openImageButton = buttons.get(buttons.size() - 1);

        buttons.add(new MyButton("Save sheet", new Runnable() {
            @Override
            public void run() {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showSaveDialog(window) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();

                    SpriteSheet.saveSheet(file.getAbsolutePath());
                }
            }
        }));
        saveImageButton = buttons.get(buttons.size() - 1);

        buttons.add(new MyButton("Export sheet", new Runnable() {
            @Override
            public void run() {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showSaveDialog(window) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();

                    SpriteExport.export(SpriteHandler.instance.getCanvas(), file.getAbsolutePath());
                }
            }
        }));
        exportImageButton = buttons.get(buttons.size() - 1);

        buttons.add(new MyButton("Add sprite", new Runnable() {
            @Override
            public void run() {
                SpriteHandler.instance.addSprite(spriteName.getText());
            }
        }));
        addSpriteButton = buttons.get(buttons.size() - 1);

        buttons.add(new MyButton("Del sprite", new Runnable() {
            @Override
            public void run() {
                int index = spritesCombo.getSelectedIndex();
                SpriteHandler.instance.removeSprite(index);
            }
        }));
        delSpriteButton = buttons.get(buttons.size() - 1);

        buttons.add(new MyButton(">", new Runnable() {
            @Override
            public void run() {
                SpriteHandler.instance.selectedFrame++;
                refreshCounter();
            }
        }));
        rightSpriteButton = buttons.get(buttons.size() - 1);

        buttons.add(new MyButton("<", new Runnable() {
            @Override
            public void run() {
                SpriteHandler.instance.selectedFrame--;
                refreshCounter();
            }
        }));
        leftSpriteButton = buttons.get(buttons.size() - 1);

        buttons.add(new MyButton("Add frame", new Runnable() {
            @Override
            public void run() {
                int w = 64;
                int h = 64;
                int cx = 32;
                int cy = 32;
                if (SpriteHandler.instance.getSelectedSprite() != null) {
                    ArrayList<Frame> frames = SpriteHandler.instance.getSelectedSprite().frames;
                    if (frames.size() > 0) {
                        w = frames.get(frames.size() - 1).box.width;
                        h = frames.get(frames.size() - 1).box.height;
                        cx = frames.get(frames.size() - 1).center.x;
                        cy = frames.get(frames.size() - 1).center.y;
                    }
                }
                SpriteHandler.instance.getSelectedSprite().addFrame(new Rectangle(0, 0, w, h), new Point(cx, cy));
            }
        }));
        addFrameButton = buttons.get(buttons.size() - 1);

        buttons.add(new MyButton("Del frame", new Runnable() {
            @Override
            public void run() {
                SpriteHandler.instance.getSelectedSprite().delFrame(SpriteHandler.instance.selectedFrame - 1);
            }
        }));
        delFrameButton = buttons.get(buttons.size() - 1);

        spritesCombo = new JComboBox<Sprite>();
        spritesCombo.addActionListener(this);

        imagesCombo = new JComboBox<String>();
        imagesCombo.addActionListener(this);

        spriteName = new JTextArea("New sprite name");
        spriteName.addKeyListener(this);

        middleSpriteButton = new JLabel("0/0");
        middleSpriteButton.setHorizontalAlignment(SwingConstants.CENTER);
        middleSpriteButton.setVerticalAlignment(SwingConstants.CENTER);

        spriteCanvas = new SpriteCanvas();

        spritesImages = new ArrayList<BufferedImage>();
        spritesImagesPosition = new ArrayList<Point>();
        spritesImagesPath = new ArrayList<String>();
        sprites = new ArrayList<Sprite>();

        currentSprite = 0;
        selectedFrame = 0;
        numberOfFrames = 0;

        zoom = 1;
        for (JButton button : buttons) {
            button.addActionListener(this);
        }
        redrawAll();
    }

    protected void zoomIn() {
        SpriteHandler.instance.zoom <<= 1;
    }

    protected void zoomOut() {
        if (SpriteHandler.instance.zoom > 1)
            SpriteHandler.instance.zoom >>= 1;
    }

    protected Sprite getSelectedSprite() {
        refreshCounter();
        if (this.currentSprite != -1 && this.currentSprite < this.sprites.size())
            return this.sprites.get(currentSprite);
        return null;
    }

    protected void removeSprite(int index) {
        sprites.remove(index);
        spritesCombo.removeItemAt(index);
    }

    public ArrayList<Sprite> sprites;

    protected void addSprite(String name) {
        sprites.add(new Sprite(name));
        spritesCombo.addItem(sprites.get(sprites.size() - 1));
        spritesCombo.setSelectedIndex(sprites.size() - 1);
    }

    protected void removeImage(int index) {
        if (index != -1 && index <= spritesImages.size()) {
            spritesImages.remove(index);
            spritesImagesPosition.remove(index);
            imagesCombo.removeItemAt(index);
        }
    }

    public ArrayList<BufferedImage> spritesImages;
    public ArrayList<Point> spritesImagesPosition;
    public ArrayList<String> spritesImagesPath;

    protected void addImage(File file) {
        addImage(file, 0, 0);
    }

    public void addImage(File file, int x, int y) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(file);
            spritesImages.add(img);
            spritesImagesPosition.add(new Point(x, y));
            spritesImagesPath.add(file.getAbsolutePath());
            imagesCombo.addItem(file.getName());
        } catch (IOException e) {}
    }

    private JFrame window;

    public void setWindow(JFrame win) {
        this.window = win;
    }

    private JButton zoomInButton;
    private JButton zoomOutButton;

    public JPanel getZoomInOutButton() {
        JPanel doublePanel = new JPanel();
        doublePanel.setLayout(new GridLayout(1, 2));
        doublePanel.add(zoomInButton);
        doublePanel.add(zoomOutButton);
        return doublePanel;
    }

    private JButton addImageButton;
    private JButton delImageButton;

    public JPanel getAddImageButton() {
        JPanel doublePanel = new JPanel();
        doublePanel.setLayout(new GridLayout(1, 2));
        doublePanel.add(addImageButton);
        doublePanel.add(delImageButton);
        return doublePanel;
    }

    private JButton openImageButton;
    private JButton saveImageButton;
    private JButton exportImageButton;

    public JPanel getImageButton() {
        JPanel doublePanel = new JPanel();
        doublePanel.setLayout(new GridLayout(1, 3));
        doublePanel.add(openImageButton);
        doublePanel.add(saveImageButton);
        doublePanel.add(exportImageButton);
        return doublePanel;
    }

    private JComboBox<String> imagesCombo;

    public JComboBox<String> getImagesCombo() {
        return imagesCombo;
    }

    private JComboBox<Sprite> spritesCombo;

    public JComboBox<Sprite> getSpritesCombo() {
        return spritesCombo;
    }

    private JTextArea spriteName;

    public JTextArea getSpriteNameArea() {
        return spriteName;
    }

    private JButton addSpriteButton;
    private JButton delSpriteButton;

    public JPanel getSpriteButton() {
        JPanel doublePanel = new JPanel();
        doublePanel.setLayout(new GridLayout(1, 2));
        doublePanel.add(addSpriteButton);
        doublePanel.add(delSpriteButton);
        return doublePanel;
    }

    private JButton leftSpriteButton;
    private JLabel middleSpriteButton;
    private JButton rightSpriteButton;

    public JPanel getSpriteIndicator() {
        JPanel doublePanel = new JPanel();
        doublePanel.setLayout(new GridLayout(1, 3));
        doublePanel.add(leftSpriteButton);
        doublePanel.add(middleSpriteButton);
        doublePanel.add(rightSpriteButton);
        return doublePanel;
    }

    private JButton addFrameButton;
    private JButton delFrameButton;

    public JPanel getFrameButton() {
        JPanel doublePanel = new JPanel();
        doublePanel.setLayout(new GridLayout(1, 2));
        doublePanel.add(addFrameButton);
        doublePanel.add(delFrameButton);
        return doublePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof MyButton) {
            MyButton button = (MyButton) e.getSource();
            button.doAction();
        }
        redrawAll();
        resetFocus();
    }

    private SpriteCanvas spriteCanvas = null;

    public SpriteCanvas getCanvas() {
        return spriteCanvas;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        redrawAll();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        redrawAll();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource() == this.getSpriteNameArea())
            return;
        char c = e.getKeyChar();
        int move = 16;
        if (c == 'w')
            spriteCanvas.cameraY -= move;
        if (c == 's')
            spriteCanvas.cameraY += move;
        if (c == 'a')
            spriteCanvas.cameraX -= move;
        if (c == 'd')
            spriteCanvas.cameraX += move;
        if (c == 'q') {
            int x = spriteCanvas.mouseX / zoom + spriteCanvas.cameraX;
            int y = spriteCanvas.mouseY / zoom + spriteCanvas.cameraY;
            this.getSelectedSprite().frames.get(selectedFrame - 1).box.x = x;
            this.getSelectedSprite().frames.get(selectedFrame - 1).box.y = y;
        }
        if (c == 'e')
            if (this.getSelectedSprite() != null) {
                int x = this.getSelectedSprite().frames.get(selectedFrame - 1).box.x;
                int y = this.getSelectedSprite().frames.get(selectedFrame - 1).box.y;
                int w = spriteCanvas.mouseX / zoom + spriteCanvas.cameraX;
                int h = spriteCanvas.mouseY / zoom + spriteCanvas.cameraY;
                this.getSelectedSprite().frames.get(selectedFrame - 1).box.width = w - x;
                this.getSelectedSprite().frames.get(selectedFrame - 1).box.height = h - y;
            }
        if (c == 'c')
            if (this.getSelectedSprite() != null) {
                int x = spriteCanvas.mouseX / zoom + spriteCanvas.cameraX;
                int y = spriteCanvas.mouseY / zoom + spriteCanvas.cameraY;
                int xb = this.getSelectedSprite().frames.get(selectedFrame - 1).box.x;
                int yb = this.getSelectedSprite().frames.get(selectedFrame - 1).box.y;
                this.getSelectedSprite().frames.get(selectedFrame - 1).center.x = x - xb;
                this.getSelectedSprite().frames.get(selectedFrame - 1).center.y = y - yb;
            }
        if (c == 'r')
            spriteCanvas.cameraX = spriteCanvas.cameraY = 0;
        if (c == 'z') {
            int x = spriteCanvas.mouseX / zoom + spriteCanvas.cameraX;
            int y = spriteCanvas.mouseY / zoom + spriteCanvas.cameraY;
            this.spritesImagesPosition.get(imagesCombo.getSelectedIndex()).x = x;
            this.spritesImagesPosition.get(imagesCombo.getSelectedIndex()).y = y;
        }
        if (c == '+') {
            this.zoomIn();
        }
        if (c == '-') {
            this.zoomOut();
        }
        if (spriteCanvas.cameraX < -4)
            spriteCanvas.cameraX = -4;
        if (spriteCanvas.cameraY < -4)
            spriteCanvas.cameraY = -4;
        redrawAll();
    }

    private int currentSprite;
    public int selectedFrame;
    private int numberOfFrames;

    @Override
    public void itemStateChanged(ItemEvent e) {
        refreshCounter();
        redrawAll();
        resetFocus();
    }

    private void refreshCounter() {
        this.currentSprite = this.spritesCombo.getSelectedIndex();
        if (this.currentSprite != -1 && this.currentSprite < this.sprites.size()) {
            this.numberOfFrames = sprites.get(this.currentSprite).getNumberOfFrames();
            if (this.selectedFrame > this.numberOfFrames)
                this.selectedFrame = this.numberOfFrames;
            if (this.selectedFrame <= 0)
                this.selectedFrame = 1;
            this.middleSpriteButton.setText(this.selectedFrame + "/" + this.numberOfFrames);
        }
    }

    public void redrawAll() {
        refreshCounter();
        this.spriteCanvas.repaint();
    }

    public void resetFocus() {
        this.spriteCanvas.requestFocus();
    }
}
