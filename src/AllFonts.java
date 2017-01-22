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
	
	private JTextArea fontSizeComp = new JTextArea("10");
	public JTextArea getFontsSize() {
		return fontSizeComp;
	}
	
	private JTextArea texSizeXComp = new JTextArea("512");
	private JTextArea texSizeYComp = new JTextArea("512");
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
		if(e.getSource() == genButton){
			System.out.println("Output file");
		}
	}

	private FontCanvas fontCanvas = new FontCanvas();
	private int texSizeX = 512;
	private int texSizeY = 512;
	private int fontSize = 10;
	public FontCanvas getCanvas() {
		return fontCanvas;
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		try{
			if(e.getSource() == texSizeXComp){
				this.texSizeX = Integer.parseInt(texSizeXComp.getText());
			}
			if(e.getSource() == texSizeYComp){
				this.texSizeY = Integer.parseInt(texSizeYComp.getText());
			}
			if(e.getSource() == fontSizeComp){
				this.fontSize  = Integer.parseInt(fontSizeComp.getText());
			}
		}
		catch(NumberFormatException ex){
			
		}
		this.fontCanvas.setText(fontStringComp.getText());
		this.fontCanvas.setSize(this.texSizeX, this.texSizeY);
		this.fontCanvas.setFontSize(this.fontSize);
		fontCanvas.repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(ItemEvent.SELECTED == e.getStateChange()){
			selectedFont = fonts[allFontsComp.getSelectedIndex()];
			fontCanvas.setFont(selectedFont);
			fontCanvas.setText(fontStringComp.getText());
			fontCanvas.repaint();
		}
	}
}
