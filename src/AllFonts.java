import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class AllFonts implements ActionListener, KeyListener, ItemListener {
	static{
		new AllFonts();
	}
	public static AllFonts instance;
	private AllFonts(){
		AllFonts.instance = this;

		genButton = new JButton("Generate");
		genButton.addActionListener(this);

		fontStringComp = new JTextArea("`1234567890-=qwertyuiop[]\\asdfghjkl;'zxcvbnm,./~!@#$%^&*()_+QWERTYUIOP{}|ASDFGHJKL:\"ZXCVBNM<>?");
		fontStringComp.addKeyListener(this);
		
		fontSizeComp.addKeyListener(this);
		texSizeXComp.addKeyListener(this);
		texSizeYComp.addKeyListener(this);
		
		allFontsComp = new JComboBox<String>();
	    GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		fonts = e.getAllFonts(); // Get the fonts
		for (Font f : fonts) {
	    	allFontsComp.addItem(f.getFontName());
	    }
		allFontsComp.addItemListener(this);
		selectedFont = fonts[0];
	}
	
	private JFrame window;
	public void setWindow(JFrame win){
		this.window = win;
	}
	
	private Font selectedFont;
	private Font[] fonts;
	private JComboBox<String> allFontsComp;
	public JComboBox<String> getFonts(){
		return allFontsComp;
	}
	
	public Font getFont(){
		return selectedFont;
	}
	
	private JTextArea fontStringComp = null;
	public JTextArea getFontsString(){
		return fontStringComp;
	}
	
	private JTextArea fontSizeComp = new JTextArea("5");
	public JTextArea getFontsSize() {
		return fontSizeComp;
	}
	
	private JTextArea texSizeXComp = new JTextArea("128");
	private JTextArea texSizeYComp = new JTextArea("128");
	public JTextArea getTexSizeX() {
		return texSizeXComp;
	}
	
	public JTextArea getTexSizeY() {
		return texSizeYComp;
	}
	
	private JButton genButton = null;
	public JButton getGenButton(){
		return genButton;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton){
			fontSizeComp.setText("sfsdf");
			fontCanvas.setSize(1024, 1024);
		}else{
			fontCanvas.setSize(10,20);
		}
	}

	private FontCanvas fontCanvas = new FontCanvas();
	private int texSizeX = 128;
	private int texSizeY = 128;
	private int fontSize = 5;
	public FontCanvas getCanvas() {
		return fontCanvas;
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getSource() == texSizeXComp){
			this.texSizeX = Integer.parseInt(texSizeXComp.getText());
		}
		if(e.getSource() == texSizeYComp){
			this.texSizeY = Integer.parseInt(texSizeYComp.getText());
		}
		if(e.getSource() == fontSizeComp){
			this.fontSize  = Integer.parseInt(fontSizeComp.getText());
		}
		this.fontCanvas.setSize(this.texSizeX, this.texSizeY);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(ItemEvent.SELECTED == e.getStateChange()){
			selectedFont = fonts[allFontsComp.getSelectedIndex()];
		}
	}
}
