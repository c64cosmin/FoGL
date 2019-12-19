package com.stupidrat.tools.sogl;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteExport {

    private static String extension = ".sprmap";
	private static String imageExtension = ".png";
	private static int exportSize = 256;

	public static void export(SpriteCanvas canvas, String filename) {
        if(filename.endsWith(extension)){
        	filename = filename.substring(0, filename.length() - extension.length());
        }
        if(filename.endsWith(imageExtension )){
        	filename = filename.substring(0, filename.length() - imageExtension.length());
        }
        
        int sz = exportSize ;
        BufferedImage image = new BufferedImage(sz, sz, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(new Color(0,0,0,0));
        g.fillRect(0, 0, sz, sz);

        canvas.render(g, false, 0, 0, 1);

        try {
            FileOutputStream imageOut;
            imageOut = new FileOutputStream(filename+imageExtension);
            ImageIO.write(image, "png", imageOut);
            imageOut.close();

            SpriteHandler h = SpriteHandler.instance;

            FileWriter out = new FileWriter(new File(filename + extension));

            int noSprites = h.sprites.entries.size();
            out.write(noSprites+"\n");
            for(int i=0;i<h.sprites.entries.size();i++){
                out.write(h.sprites.entries.get(i).getSerial()+"\n");
            }

            out.close();
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
