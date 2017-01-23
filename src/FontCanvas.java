import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class FontCanvas extends Canvas{
	private Font font;
	private String text;
	private int fontSize;
	private LetterSplitter split;

	public FontCanvas(){
		//this.setSize(512, 512);
		this.setVisible(true);
		fontSize = 10;
		split = new LetterSplitter();
	}
	
	public void update(Graphics g){
		paint(g);
	}
	
	public void paint(Graphics g){
		((Graphics2D)g).setRenderingHint(
		        RenderingHints.KEY_TEXT_ANTIALIASING,
		        RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		font = font.deriveFont((float)fontSize);
		g.setFont(font);
		
		drawAllLetters(g, text, true);
	}
	
	public void setFont(Font f){
		this.font = f;
	}
	
	public void setText(String s){
		this.text = s;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	
	public void drawAllLetters(Graphics g, String str, boolean grid){
		if(str.isEmpty())str = "`1234567890-=qwertyuiop[]\\asdfghjkl;'zxcvbnm,./~!@#$%^&*()_+QWERTYUIOP{}|ASDFGHJKL:\"ZXCVBNM<>?";
		int x = 0;
		int y = 0;
		int n = str.length();
		for(int i=0;i<n;i++){
			String c = str.substring(i, i+1);
			CharProperty prop = split.getCharProperties(g, c);
			if(x+prop.width >= this.getWidth()){
				y += prop.height;
				x = 0;
			}
			split.drawChar(g, c, x, y, grid);
			x += prop.width;
		}
	}
}
