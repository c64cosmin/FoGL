import java.awt.GridLayout;
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
	public SpriteHandler(){
	    SpriteHandler.instance = this;

	    buttons = new ArrayList<MyButton>();
	    buttons.add(new MyButton("+", new Runnable(){
	        @Override
            public void run(){
	            SpriteHandler.instance.zoom <<= 1;
	        }
	    }));
	   zoomInButton = buttons.get(buttons.size()-1);

        buttons.add(new MyButton("-", new Runnable(){
            @Override
            public void run(){
                if(SpriteHandler.instance.zoom > 1)SpriteHandler.instance.zoom >>= 1;
            }
         }));
        zoomOutButton = buttons.get(buttons.size()-1);

        buttons.add(new MyButton("Add image", new Runnable(){
           @Override
           public void run(){
               JFileChooser fileChooser = new JFileChooser();
               if (fileChooser.showSaveDialog(window) == JFileChooser.APPROVE_OPTION) {
                 File file = fileChooser.getSelectedFile();

                 SpriteHandler.instance.addImage(file);
               }
           }
       }));
       addImageButton = buttons.get(buttons.size()-1);

       buttons.add(new MyButton("Del image", new Runnable(){
           @Override
           public void run(){
               int index = imagesCombo.getSelectedIndex();
               SpriteHandler.instance.removeImage(index);
           }
       }));
       delImageButton = buttons.get(buttons.size()-1);

       buttons.add(new MyButton("Open sheet", new Runnable(){
           @Override
           public void run(){

           }
       }));
	    openImageButton= buttons.get(buttons.size()-1);

	       buttons.add(new MyButton("Save sheet", new Runnable(){
	           @Override
	           public void run(){

	           }
	       }));
        saveImageButton = buttons.get(buttons.size()-1);

        buttons.add(new MyButton("Add sprite", new Runnable(){
            @Override
            public void run(){
                SpriteHandler.instance.addSprite(spriteName.getText());
            }
        }));
        addSpriteButton = buttons.get(buttons.size()-1);

        buttons.add(new MyButton("Del sprite", new Runnable(){
            @Override
            public void run(){
                int index = spritesCombo.getSelectedIndex();
                SpriteHandler.instance.removeSprite(index);
            }
        }));
        delSpriteButton = buttons.get(buttons.size()-1);

        buttons.add(new MyButton(">", new Runnable(){
            @Override
            public void run(){
                SpriteHandler.instance.selectedFrame++;
                refreshCounter();
            }
        }));
        rightSpriteButton = buttons.get(buttons.size()-1);

        buttons.add(new MyButton("<", new Runnable(){
            @Override
            public void run(){
                SpriteHandler.instance.selectedFrame--;
                refreshCounter();
            }
        }));
        leftSpriteButton = buttons.get(buttons.size()-1);

        buttons.add(new MyButton("Add frame", new Runnable(){
            @Override
            public void run(){
                SpriteHandler.instance.getSelectedSprite().addFrame(new Rectangle());
            }
        }));
        addFrameButton = buttons.get(buttons.size()-1);

        buttons.add(new MyButton("Del frame", new Runnable(){
            @Override
            public void run(){
                SpriteHandler.instance.getSelectedSprite().delFrame(SpriteHandler.instance.selectedFrame);
            }
        }));
        delFrameButton = buttons.get(buttons.size()-1);

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
        sprites = new ArrayList<Sprite>();

        currentSprite = 0;
        selectedFrame = 0;
        numberOfFrames = 0;

        zoom = 1;
        for(JButton button:buttons){
            button.addActionListener(this);
        }
	    redrawAll();
	}

	protected Sprite getSelectedSprite() {
	    refreshCounter();
        return this.sprites.get(currentSprite);
    }

    protected void removeSprite(int index) {
        sprites.remove(index);
        spritesCombo.removeItemAt(index);
    }

    public ArrayList<Sprite> sprites;
	protected void addSprite(String name) {
	    sprites.add(new Sprite(name));
	    spritesCombo.addItem(sprites.get(sprites.size()-1));
	    spritesCombo.setSelectedIndex(sprites.size()-1);
    }

    protected void removeImage(int index) {
        if(index != -1 && index <= spritesImages.size()){
            spritesImages.remove(index);
            imagesCombo.removeItemAt(index);
        }
    }

    public ArrayList<BufferedImage> spritesImages;
	protected void addImage(File file) {
	    BufferedImage img = null;
	    try {
	        img = ImageIO.read(file);
	        spritesImages.add(img);
	        imagesCombo.addItem(file.getName());
	    } catch (IOException e) {
	    }
    }

    private JFrame window;
	public void setWindow(JFrame win){
		this.window = win;
	}

	private JButton zoomInButton;
	private JButton zoomOutButton;
    public JPanel getZoomInOutButton(){
        JPanel doublePanel = new JPanel();
        doublePanel.setLayout(new GridLayout(1,2));
        doublePanel.add(zoomInButton);
        doublePanel.add(zoomOutButton);
        return doublePanel;
    }

    private JButton addImageButton;
    private JButton delImageButton;
    public JPanel getAddImageButton(){
        JPanel doublePanel = new JPanel();
        doublePanel.setLayout(new GridLayout(1,2));
        doublePanel.add(addImageButton);
        doublePanel.add(delImageButton);
        return doublePanel;
    }

    private JButton openImageButton;
    private JButton saveImageButton;
    public JPanel getImageButton(){
        JPanel doublePanel = new JPanel();
        doublePanel.setLayout(new GridLayout(1,2));
        doublePanel.add(openImageButton);
        doublePanel.add(saveImageButton);
        return doublePanel;
    }

    private JComboBox<String> imagesCombo;
    public JComboBox<String> getImagesCombo(){
        return imagesCombo;
    }

    private JComboBox<Sprite> spritesCombo;
    public JComboBox<Sprite> getSpritesCombo(){
        return spritesCombo;
    }

    private JTextArea spriteName;
    public JTextArea getSpriteNameArea(){
        return spriteName;
    }

    private JButton addSpriteButton;
    private JButton delSpriteButton;
    public JPanel getSpriteButton(){
        JPanel doublePanel = new JPanel();
        doublePanel.setLayout(new GridLayout(1,2));
        doublePanel.add(addSpriteButton);
        doublePanel.add(delSpriteButton);
        return doublePanel;
    }

    private JButton leftSpriteButton;
    private JLabel middleSpriteButton;
    private JButton rightSpriteButton;
    public JPanel getSpriteIndicator(){
        JPanel doublePanel = new JPanel();
        doublePanel.setLayout(new GridLayout(1,3));
        doublePanel.add(leftSpriteButton);
        doublePanel.add(middleSpriteButton);
        doublePanel.add(rightSpriteButton);
        return doublePanel;
    }

    private JButton addFrameButton;
    private JButton delFrameButton;
    public JPanel getFrameButton(){
        JPanel doublePanel = new JPanel();
        doublePanel.setLayout(new GridLayout(1,2));
        doublePanel.add(addFrameButton);
        doublePanel.add(delFrameButton);
        return doublePanel;
    }

	@Override
	public void actionPerformed(ActionEvent e) {
	    if(e.getSource() instanceof MyButton){
            MyButton button = (MyButton)e.getSource();
            button.doAction();
        }
	    redrawAll();
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
		redrawAll();
	}

	private int currentSprite;
	private int selectedFrame;
	private int numberOfFrames;
	@Override
	public void itemStateChanged(ItemEvent e) {
	    refreshCounter();
	    redrawAll();
	}

	private void refreshCounter() {
	    this.currentSprite = this.spritesCombo.getSelectedIndex();
	    System.out.println(this.currentSprite);
        if(this.currentSprite != -1 && this.currentSprite < this.sprites.size()){
            this.numberOfFrames = sprites.get(this.currentSprite).getNumberOfFrames();
            if(this.selectedFrame > this.numberOfFrames)this.selectedFrame = this.numberOfFrames;
            if(this.selectedFrame <= 0)this.selectedFrame = 1;
            this.middleSpriteButton.setText(this.selectedFrame+ "/" + this.numberOfFrames);
        }
    }

    public void redrawAll(){
        refreshCounter();
		this.spriteCanvas.repaint();
		System.out.println(zoom);
	}
}
