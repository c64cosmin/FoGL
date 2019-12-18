package com.stupidrat.tools.sogl;

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

import com.stupidrat.tools.fogl.Frame;

public class SpriteHandler implements ActionListener, KeyListener, ItemListener {
    public static SpriteHandler instance;

    public ArrayList<Sprite> sprites;
    public ArrayList<BufferedImage> spritesImages;
    public ArrayList<Point> spritesImagesPosition;
    public ArrayList<String> spritesImagesPath;
    private JFrame window;
    private SpriteCanvas spriteCanvas = null;
    public ArrayList<MyButton> buttons;
    private JButton zoomInButton;
    private JButton zoomOutButton;
    private JButton addImageButton;
    private JButton delImageButton;
    private JButton openImageButton;
    private JButton saveImageButton;
    private JButton exportImageButton;
    private JButton addSpriteButton;
    private JButton delSpriteButton;
    private JButton leftSpriteButton;
    private JLabel middleSpriteButton;
    private JButton rightSpriteButton;
    private JButton addFrameButton;
    private JButton delFrameButton;
    private JComboBox<String> imagesCombo;
    private JComboBox<Sprite> spritesCombo;
    private JTextArea spriteName;

    public int currentSprite;
    public int selectedFrame;
    public int numberOfFrames;
    public int zoom;

    public char isKeyPressed;

    public SpriteHandler() {
        SpriteHandler.instance = this;

        {
            buttons = new ArrayList<MyButton>();
            buttons.add(new MyButton("+", new Runnable() {
                public void run() {
                    SpriteHandler.instance.zoomIn();
                }
            }));
            zoomInButton = buttons.get(buttons.size() - 1);

            buttons.add(new MyButton("-", new Runnable() {
                public void run() {
                    SpriteHandler.instance.zoomOut();
                }
            }));
            zoomOutButton = buttons.get(buttons.size() - 1);

            buttons.add(new MyButton("Add image", new Runnable() {
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
                public void run() {
                    int index = imagesCombo.getSelectedIndex();
                    SpriteHandler.instance.removeImage(index);
                }
            }));
            delImageButton = buttons.get(buttons.size() - 1);

            buttons.add(new MyButton("Open sheet", new Runnable() {
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
                public void run() {
                    SpriteHandler.instance.addSprite(spriteName.getText());
                }
            }));
            addSpriteButton = buttons.get(buttons.size() - 1);

            buttons.add(new MyButton("Del sprite", new Runnable() {
                public void run() {
                    int index = spritesCombo.getSelectedIndex();
                    SpriteHandler.instance.removeSprite(index);
                }
            }));
            delSpriteButton = buttons.get(buttons.size() - 1);

            buttons.add(new MyButton(">", new Runnable() {
                public void run() {
                    SpriteHandler.instance.selectedFrame++;
                    refreshCounter();
                }
            }));
            rightSpriteButton = buttons.get(buttons.size() - 1);

            buttons.add(new MyButton("<", new Runnable() {
                public void run() {
                    SpriteHandler.instance.selectedFrame--;
                    refreshCounter();
                }
            }));
            leftSpriteButton = buttons.get(buttons.size() - 1);

            buttons.add(new MyButton("Add frame", new Runnable() {
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
                public void run() {
                    SpriteHandler.instance.getSelectedSprite().delFrame(SpriteHandler.instance.selectedFrame - 1);
                }
            }));
            delFrameButton = buttons.get(buttons.size() - 1);
        }

        spritesCombo = new JComboBox<Sprite>();
        spritesCombo.addActionListener(this);

        imagesCombo = new JComboBox<String>();
        imagesCombo.addActionListener(this);

        spriteName = new JTextArea("New sprite name");
        spriteName.addKeyListener(this);

        middleSpriteButton = new JLabel("0/0");
        middleSpriteButton.setHorizontalAlignment(SwingConstants.CENTER);
        middleSpriteButton.setVerticalAlignment(SwingConstants.CENTER);

        spriteCanvas = new SpriteCanvas(this);

        spritesImages = new ArrayList<BufferedImage>();
        spritesImagesPosition = new ArrayList<Point>();
        spritesImagesPath = new ArrayList<String>();
        sprites = new ArrayList<Sprite>();

        currentSprite = 0;
        selectedFrame = 0;
        numberOfFrames = 0;

        zoom = 1;

        isKeyPressed = ' ';

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
        } catch (IOException e) {
        }
    }

    public void setWindow(JFrame win) {
        this.window = win;
    }

    public JPanel getZoomInOutButton() {
        JPanel doublePanel = new JPanel();
        doublePanel.setLayout(new GridLayout(1, 2));
        doublePanel.add(zoomInButton);
        doublePanel.add(zoomOutButton);
        return doublePanel;
    }

    public JPanel getAddImageButton() {
        JPanel doublePanel = new JPanel();
        doublePanel.setLayout(new GridLayout(1, 2));
        doublePanel.add(addImageButton);
        doublePanel.add(delImageButton);
        return doublePanel;
    }

    public JPanel getImageButton() {
        JPanel doublePanel = new JPanel();
        doublePanel.setLayout(new GridLayout(1, 3));
        doublePanel.add(openImageButton);
        doublePanel.add(saveImageButton);
        doublePanel.add(exportImageButton);
        return doublePanel;
    }

    public JComboBox<String> getImagesCombo() {
        return imagesCombo;
    }

    public JComboBox<Sprite> getSpritesCombo() {
        return spritesCombo;
    }

    public JTextArea getSpriteNameArea() {
        return spriteName;
    }

    public JPanel getSpriteButton() {
        JPanel doublePanel = new JPanel();
        doublePanel.setLayout(new GridLayout(1, 2));
        doublePanel.add(addSpriteButton);
        doublePanel.add(delSpriteButton);
        return doublePanel;
    }

    public JPanel getSpriteIndicator() {
        JPanel doublePanel = new JPanel();
        doublePanel.setLayout(new GridLayout(1, 3));
        doublePanel.add(leftSpriteButton);
        doublePanel.add(middleSpriteButton);
        doublePanel.add(rightSpriteButton);
        return doublePanel;
    }

    public JPanel getFrameButton() {
        JPanel doublePanel = new JPanel();
        doublePanel.setLayout(new GridLayout(1, 2));
        doublePanel.add(addFrameButton);
        doublePanel.add(delFrameButton);
        return doublePanel;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof MyButton) {
            MyButton button = (MyButton) e.getSource();
            button.doAction();
        }
        redrawAll();
        resetFocus();
    }

    public SpriteCanvas getCanvas() {
        return spriteCanvas;
    }

    public void keyPressed(KeyEvent e) {
        char c = e.getKeyChar();
        if (c == 'q' || c == 'e' || c == 'c' || c == 'p') {
            isKeyPressed = c;
            this.spriteCanvas.doMouseMove();
        }
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_LEFT) {
            Sprite sprite = this.getSelectedSprite();
            if (sprite != null) {
                this.selectedFrame--;
                if (this.selectedFrame == 0) {
                    this.selectedFrame = sprite.frames.size();
                }
            }
        }
        if (code == KeyEvent.VK_RIGHT) {
            Sprite sprite = this.getSelectedSprite();
            if (sprite != null) {
                this.selectedFrame++;
                if (this.selectedFrame > sprite.getNumberOfFrames()) {

                    this.selectedFrame = 1;
                }
            }
        }
        redrawAll();
    }

    public void keyReleased(KeyEvent e) {
        char c = e.getKeyChar();
        if (c == 'q' || c == 'e' || c == 'c' || c == 'p') {
            isKeyPressed = ' ';
        }
        redrawAll();
    }

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

    public void setFramePosition(int x, int y) {
        if (this.getSelectedSprite() == null)
            return;

        x = x / zoom + spriteCanvas.cameraX;
        y = y / zoom + spriteCanvas.cameraY;
        this.getSelectedSprite().frames.get(selectedFrame - 1).box.x = x;
        this.getSelectedSprite().frames.get(selectedFrame - 1).box.y = y;
    }

    public void setFrameSize(int newX, int newY) {
        if (this.getSelectedSprite() == null)
            return;

        int x = this.getSelectedSprite().frames.get(selectedFrame - 1).box.x;
        int y = this.getSelectedSprite().frames.get(selectedFrame - 1).box.y;
        int w = newX / zoom + spriteCanvas.cameraX;
        int h = newY / zoom + spriteCanvas.cameraY;
        this.getSelectedSprite().frames.get(selectedFrame - 1).box.width = w - x;
        this.getSelectedSprite().frames.get(selectedFrame - 1).box.height = h - y;
    }

    public void setFrameCenter(int x, int y) {
        if (this.getSelectedSprite() == null)
            return;

        x = x / zoom + spriteCanvas.cameraX;
        y = y / zoom + spriteCanvas.cameraY;
        int xb = this.getSelectedSprite().frames.get(selectedFrame - 1).box.x;
        int yb = this.getSelectedSprite().frames.get(selectedFrame - 1).box.y;
        this.getSelectedSprite().frames.get(selectedFrame - 1).center.x = x - xb;
        this.getSelectedSprite().frames.get(selectedFrame - 1).center.y = y - yb;
    }
}
