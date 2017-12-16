package com.stupidrat.tools.fogl;
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

    public static void export(SpriteCanvas canvas, String filename) {
        int sz = canvas.outputSize;
        BufferedImage image = new BufferedImage(sz, sz, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(new Color(0,0,0,0));
        g.fillRect(0, 0, sz, sz);

        int zoom = SpriteHandler.instance.zoom;
        SpriteHandler.instance.zoom = 1;
        canvas.render(g, false);
        SpriteHandler.instance.zoom = zoom;

        try {
            FileOutputStream imageOut;
            imageOut = new FileOutputStream(filename+".png");
            ImageIO.write(image, "png", imageOut);
            imageOut.close();

            SpriteHandler h = SpriteHandler.instance;

            FileWriter out = new FileWriter(new File(filename + ".sprmap"));

            int noSprites = h.sprites.size();
            out.write(noSprites+"\n");
            for(int i=0;i<h.sprites.size();i++){
                out.write(h.sprites.get(i).getSerial()+"\n");
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
