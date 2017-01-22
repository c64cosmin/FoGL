import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextArea;

public class AllFonts {
	static{
		new AllFonts();
	}
	public static AllFonts instance;
	private AllFonts(){
		AllFonts.instance = this;
	}
	
	public JComboBox<String> getFonts(){
		JComboBox<String> dropbox = new JComboBox<String>();
	    GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    Font[] fonts = e.getAllFonts(); // Get the fonts
	    for (Font f : fonts) {
	    	dropbox.addItem(f.getFontName());
	    }
	    dropbox.setSize(new Dimension(200,30));
	    dropbox.setVisible(true);
	    return dropbox;
	}
	
	private JTextArea fontStringComp = new JTextArea("ABC");
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
	
	private JButton genButton = new JButton("Generate");
	public JButton getGenButton(){
		return genButton;
	}
}
