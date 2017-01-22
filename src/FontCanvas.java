import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class FontCanvas extends Canvas{
	private int sizeX = 64;
	private int sizeY = 64;
	private Font font;
	
	public FontCanvas(){
		this.setSize(sizeX, sizeY);
		this.setVisible(true);
	}
	
	public void update(Graphics g){
		paint(g);
	}
	
	public void paint(Graphics g){
		
		g.setColor(Color.BLUE);
		g.fillRect(0, 0,  1000, 1000);
		g.setColor(Color.RED);
		g.drawLine(0, 0, 600, 600);
	}
	
	public void setFont(Font f){
		this.font = f;
	}
}
