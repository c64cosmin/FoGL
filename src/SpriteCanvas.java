import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class SpriteCanvas extends Canvas{
	private static final long serialVersionUID = -5593088216030819037L;
	private Graphics graphics;

	public SpriteCanvas(){
		this.setVisible(true);
	}

	@Override
    public void update(Graphics g){
		paint(g);
	}

	@Override
    public void paint(Graphics g){
		graphics = g;
		render(g, true);
	}

	private void render(Graphics g, boolean grid){
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		for(int i=0;i<SpriteHandler.instance.spritesImages.size();i++){
		    g.drawImage(SpriteHandler.instance.spritesImages.get(i), 0, 0, null);
		}
	}

	public void exportAllLetters(String imageName, String mapName){
		/*if(text.isEmpty())text = "`1234567890-=qwertyuiop[]\\asdfghjkl;'zxcvbnm,./~!@#$%^&*()_+QWERTYUIOP{}|ASDFGHJKL:\"ZXCVBNM<>?";
		BufferedImage image = new BufferedImage(getWidth(), getHeight(),BufferedImage.TYPE_INT_RGB);
	    Graphics2D g = image.createGraphics();
	    g.setRenderingHint(
		        RenderingHints.KEY_TEXT_ANTIALIASING,
		        RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		font = font.deriveFont((float)fontSize);
		g.setFont(font);

		try {
			PrintWriter mapOut = new PrintWriter(new File(mapName));
			mapOut.println(this.getWidth() + " " + this.getHeight());
			int x = 0;
			int y = 0;
			int n = text.length();
			for(int i=0;i<n;i++){
				String c = text.substring(i, i+1);
				CharProperty prop = split.getCharProperties(graphics, c);
				if(x+prop.width >= this.getWidth()){
					y += prop.height + 1;
					x = 0;
				}
				split.drawChar(g, c, x, y, false);
				mapOut.println(c + " " + x + " " + y + " " + prop.width + " " + prop.height + " " + prop.advance + " " + prop.bearing + " " + prop.ascent);
				x += prop.width;
			}
			mapOut.close();

			g.dispose();
			FileOutputStream imageOut = new FileOutputStream(imageName);
	        ImageIO.write(image, "png", imageOut);
	        imageOut.close();
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
	        e.printStackTrace();
	    }*/
	}
}
