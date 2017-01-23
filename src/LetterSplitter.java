import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;

public class LetterSplitter {
	public LetterSplitter(){
		
	}
	
	public int getRectangle(Graphics g, String c){
		return g.getFontMetrics().getHeight();
	}
	
	private Rectangle getStringBounds(Graphics g, String str){
		FontRenderContext frc = new FontRenderContext(g.getFont().getTransform(), true, true);
		GlyphVector gv = g.getFont().createGlyphVector(frc, str);
		return gv.getPixelBounds(frc, 0, 0);
	}
	
	private int leftSideBearing(Graphics g, String str){
		FontRenderContext frc = new FontRenderContext(g.getFont().getTransform(), true, true);
		return (int) g.getFont().createGlyphVector(frc, str).getGlyphMetrics(0).getLSB();
	}
	
	public CharProperty getCharProperties(Graphics g, String c){
		CharProperty prop = new CharProperty();
		if(c.isEmpty())return prop;

		Rectangle bound = getStringBounds(g, c);
		prop.width = bound.width + 2;
		prop.height = g.getFontMetrics().getHeight();
		prop.advance = g.getFontMetrics().stringWidth(c);
		prop.ascent = g.getFontMetrics().getAscent();
	    prop.bearing = leftSideBearing(g, c);
	    
	    return prop;
	}
	
	public void drawChar(Graphics g, String c, int x, int y, boolean grid){
		if(c.isEmpty())return;
		g.setColor(Color.WHITE);
		CharProperty prop = getCharProperties(g, c);
		if(grid){
			g.setColor(Color.GREEN);
			g.drawRect(x-prop.bearing + 1, y, prop.advance, prop.ascent);
			g.setColor(Color.BLUE);
			g.drawRect(x-prop.bearing + 1, y, prop.advance, prop.height);
			g.setColor(Color.RED);
			g.drawRect(x, y, prop.width, prop.height);
		}
		g.setColor(Color.WHITE);
		g.drawString(c, x-prop.bearing + 1, y+prop.ascent);
	}
}
