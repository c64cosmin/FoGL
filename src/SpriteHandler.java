import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class SpriteHandler implements ActionListener, KeyListener, ItemListener {
	public SpriteHandler(){
	    zoomInButton = new JButton("+");
	    zoomInButton.addActionListener(this);

	    zoomOutButton = new JButton("-");
        zoomOutButton.addActionListener(this);

        addImageButton = new JButton("Add image");
        addImageButton.addActionListener(this);

        openImageButton = new JButton("Open sheet");
        openImageButton.addActionListener(this);

        saveImageButton = new JButton("Save sheet");
        saveImageButton.addActionListener(this);

        spritesCombo = new JComboBox<String>();
        for(int i=0;i<6;i++){
            spritesCombo.addItem("asidufhaoidsufhasoidufhaoisdufhaiufdshadsfdsafsfd");
        }
        spritesCombo.addActionListener(this);

        spriteName = new JTextArea("New sprite name");
        spriteName.addKeyListener(this);

        addSpriteButton = new JButton("Add sprite");
        addSpriteButton.addActionListener(this);

        delSpriteButton = new JButton("Del sprite");
        delSpriteButton.addActionListener(this);

        leftSpriteButton = new JButton("<");
        leftSpriteButton.addActionListener(this);

        middleSpriteButton = new JLabel("0/0");
        middleSpriteButton.setHorizontalAlignment(SwingConstants.CENTER);
        middleSpriteButton.setVerticalAlignment(SwingConstants.CENTER);

        rightSpriteButton = new JButton(">");
        rightSpriteButton.addActionListener(this);

        addFrameButton = new JButton("Add frame");
        addFrameButton.addActionListener(this);

        delFrameButton = new JButton("Del frame");
        delFrameButton.addActionListener(this);

		spriteCanvas = new SpriteCanvas();
		redrawAll();
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
    public JButton getAddImageButton(){
        return addImageButton;
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

    private JComboBox<String> spritesCombo;
    public JComboBox<String> getSpritesCombo(){
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

	@Override
	public void itemStateChanged(ItemEvent e) {
	}

	public void redrawAll(){
		this.spriteCanvas.repaint();
	}
}
