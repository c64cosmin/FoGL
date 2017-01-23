import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

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
		render(g, true);
	}
	
	private void render(Graphics g, boolean grid){
		((Graphics2D)g).setRenderingHint(
		        RenderingHints.KEY_TEXT_ANTIALIASING,
		        RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		font = font.deriveFont((float)fontSize);
		g.setFont(font);
		
		drawAllLetters(g, text, grid);
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
				y += prop.height + 1;
				x = 0;
			}
			split.drawChar(g, c, x, y, grid);
			x += prop.width;
		}
	}
	
	public void exportImage(String imageName) {
	    BufferedImage image = new BufferedImage(getWidth(), getHeight(),BufferedImage.TYPE_INT_RGB);
	    Graphics2D graphics = image.createGraphics();
	    render(graphics, false);
	    graphics.dispose();
	    try {
	        System.out.println("Exporting image: "+imageName);
	        FileOutputStream out = new FileOutputStream(imageName);
	        ImageIO.write(image, "png", out);
	        out.close();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public void exportMap(String filename_map) {
		try {
			FileOutputStream out = new FileOutputStream(filename_map);
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
