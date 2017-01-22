import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class FontCanvas extends Canvas{
	private Font font;
	private String text;
	private int fontSize;

	public FontCanvas(){
		this.setSize(512, 512);
		this.setVisible(true);
		fontSize = 10;
	}
	
	public void update(Graphics g){
		paint(g);
	}
	
	public void paint(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.WHITE);
		font = font.deriveFont((float)fontSize);
		g.setFont(font);
		g.drawString(text, 30, 30);
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
}
