import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteExport {

    public static void export(SpriteCanvas canvas, String filename) {
        int sz = canvas.outputSize;
        BufferedImage image = new BufferedImage(sz, sz, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(new Color(0,0,0,0));
        g.fillRect(0, 0, sz, sz);

        canvas.render(g, false);

        try {
            FileOutputStream imageOut;
            imageOut = new FileOutputStream(filename+".png");
            ImageIO.write(image, "png", imageOut);
            imageOut.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        g.dispose();
    }

}
