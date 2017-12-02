package com.stupidrat.tools.fogl;
import java.awt.Point;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SpriteSheet {
    public static void saveSheet(String filename){
        File f = new File(filename+".sheet");
        try {
            FileWriter out = new FileWriter(f);
            SpriteHandler h = SpriteHandler.instance;
            out.write(h.spritesImages.size()+"\n");
            for(int i=0;i<h.spritesImages.size();i++){
                String filepath = h.spritesImagesPath.get(i);
                Point point = h.spritesImagesPosition.get(i);
                String spriteString = "\""+filepath+"\" " + point.x + " " + point.y;
                out.write(spriteString + "\n");
            }

            out.write(h.sprites.size()+"\n");
            for(int i=0;i<h.sprites.size();i++){
                out.write(h.sprites.get(i).getSerial());
            }
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void openSheet(String filename) {
        File f = new File(filename);
        try {
            SpriteHandler h = SpriteHandler.instance;

            FileReader in = new FileReader(f);
            Scanner scan = new Scanner(in);

            h.sprites.clear();
            h.spritesImages.clear();
            h.spritesImagesPosition.clear();
            h.getImagesCombo().removeAllItems();
            h.getSpritesCombo().removeAllItems();

            int noImages = scan.nextInt();scan.nextLine();
            for(int i=0;i<noImages;i++){
                String line = scan.nextLine();
                String filepath = "";
                boolean foundLast = false;
                int it = 1;
                while(it < line.length() && !foundLast){
                    filepath += line.charAt(it);
                    it++;
                    if(line.charAt(it) == '\"'){
                        foundLast = true;
                    }
                }
                String theRest = line.substring(it+2);
                Scanner miniScan = new Scanner(theRest);
                int posX = miniScan.nextInt();
                int posY = miniScan.nextInt();
                miniScan.close();

                h.addImage(new File(filepath), posX, posY);
            }

            int noSprites = scan.nextInt();scan.nextLine();
            for(int i=0;i<noSprites;i++){
                String line = scan.nextLine();
                Sprite spr = Sprite.fromSerial(line);
                h.sprites.add(spr);
                h.getSpritesCombo().addItem(spr);
            }
            scan.close();
            in.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
