import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class SpriteCanvas extends JPanel implements MouseMotionListener{
    public final int outputSize = 1024;
	private static final long serialVersionUID = -5593088216030819037L;
	private BufferedImage gridImage;
	private Graphics graphics;
	public int cameraX;
	public int cameraY;

	public SpriteCanvas(){
		this.setVisible(true);
		try {
		    gridImage = ImageIO.read(new File("grid.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Missing grid.png");
        }
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
	    g.setColor(Color.darkGray);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.drawImage(gridImage, -cameraX*SpriteHandler.instance.zoom, -cameraY*SpriteHandler.instance.zoom, outputSize*SpriteHandler.instance.zoom, outputSize*SpriteHandler.instance.zoom, null);
		for(int i=0;i<SpriteHandler.instance.spritesImages.size();i++){
		    int x = SpriteHandler.instance.spritesImagesPosition.get(i).x-cameraX;
            int y = SpriteHandler.instance.spritesImagesPosition.get(i).y-cameraY;
            int w = SpriteHandler.instance.spritesImages.get(i).getWidth();
            int h = SpriteHandler.instance.spritesImages.get(i).getHeight();
		    g.drawImage(SpriteHandler.instance.spritesImages.get(i),
		                x*SpriteHandler.instance.zoom, y*SpriteHandler.instance.zoom, w*SpriteHandler.instance.zoom, h*SpriteHandler.instance.zoom,
		                null);
		}
		g.setColor(Color.BLUE);

		if(SpriteHandler.instance.getSelectedSprite() != null &&
		   SpriteHandler.instance.selectedFrame > 0 &&
		   SpriteHandler.instance.getSelectedSprite().frames.size() > 0){
    		for(int i=0;i<SpriteHandler.instance.getSelectedSprite().frames.size();i++){
    		    SpriteHandler.instance.getSelectedSprite().frames.get(i).draw(g, cameraX, cameraY, SpriteHandler.instance.zoom, i+1);
    		}
    		g.setColor(Color.RED);
    		SpriteHandler.instance.getSelectedSprite().frames.get(SpriteHandler.instance.selectedFrame-1).draw(g, cameraX, cameraY, SpriteHandler.instance.zoom,0);
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

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    public int mouseX;
    public int mouseY;
    @Override
    public void mouseMoved(MouseEvent e) {
        this.mouseX = e.getX();
        this.mouseY = e.getY();
    }
}
