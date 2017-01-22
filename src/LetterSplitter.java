import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;

public class LetterSplitter {
	public LetterSplitter(){
		
	}
	
	public int getRectangle(Graphics g, String c){
		return g.getFontMetrics().getHeight();
	}
	
	private Rectangle getStringBounds(Graphics g, String str, int x, int y){
		FontRenderContext frc = new FontRenderContext(g.getFont().getTransform(), true, true);
		GlyphVector gv = g.getFont().createGlyphVector(frc, str);
		return gv.getPixelBounds(frc, x, y);
	}
	
	private int leftSideBearing(Graphics g, String str){
		FontRenderContext frc = new FontRenderContext(g.getFont().getTransform(), true, true);
		return (int) g.getFont().createGlyphVector(frc, str).getGlyphMetrics(0).getLSB();
	}
	
	public void drawChar(Graphics g, String c){
		int x = 100;
		int y = 100;
		g.setColor(Color.WHITE);
		g.drawString(c, x, y);
		Rectangle bound = getStringBounds(g, c, x, y);
		int w = bound.width;
		int h = g.getFontMetrics().getHeight();
		int aboveLine = g.getFontMetrics().getAscent();
		int leftBearing = leftSideBearing(g, c);
		g.setColor(Color.WHITE);
		g.drawRect(x+leftBearing-1, y-aboveLine, w, h);
		g.setColor(Color.RED);
		w = g.getFontMetrics().stringWidth(c);
		//g.drawRect(x, y-aboveLine, w, h);
	}
}
