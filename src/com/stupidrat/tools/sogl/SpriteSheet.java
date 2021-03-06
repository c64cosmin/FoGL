package com.stupidrat.tools.sogl;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class SpriteSheet {
    public ArrayList<BufferedImage> spritesImages;
    public ArrayList<Point> spritesImagesPosition;
    public ArrayList<String> spritesImagesPath;

    SpriteAnimationListArea sprites;
    public String filename;

    public SpriteSheet() {
        spritesImages = new ArrayList<BufferedImage>();
        spritesImagesPosition = new ArrayList<Point>();
        spritesImagesPath = new ArrayList<String>();

        sprites = new SpriteAnimationListArea();
    }

    public void saveSheet(String fullname) {
        filename = fullname;
        String trimmedName = filename;
        if (!trimmedName.endsWith(".sheet")) {
            trimmedName += ".sheet";
        }
        File f = new File(trimmedName);
        try {
            FileWriter out = new FileWriter(f);
            out.write(spritesImages.size() + "\n");
            for (int i = 0; i < spritesImages.size(); i++) {
                String filepath = spritesImagesPath.get(i);
                Point point = spritesImagesPosition.get(i);
                String spriteString = "\"" + filepath + "\" " + point.x + " " + point.y;
                out.write(spriteString + "\n");
            }

            out.write(sprites.entries.size() + "\n");
            for (int i = 0; i < sprites.entries.size(); i++) {
                out.write(sprites.entries.get(i).getSerial() + "\n");
            }
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void openSheet(String fullname) {
        try {
            filename = fullname;
            String trimmedName = fullname;

            String extension = ".sheet";
            String modifier = ".packed";
            String imageExtension = ".png";

            if (trimmedName.endsWith(extension)) {
                trimmedName = trimmedName.substring(0, trimmedName.length() - extension.length());
            }
            if (trimmedName.endsWith(modifier)) {
                trimmedName = trimmedName.substring(0, trimmedName.length() - modifier.length());
            }
            if (trimmedName.endsWith(imageExtension)) {
                trimmedName = trimmedName.substring(0, trimmedName.length() - imageExtension.length());
            }

            int pathLength = fullname.length() - 1;
            while (fullname.charAt(pathLength) != '\\' && fullname.charAt(pathLength) != '/') {
                pathLength--;
            }
            String path = fullname.substring(0, pathLength + 1);

            File f = new File(fullname);
            FileReader in = new FileReader(f);
            Scanner scan = new Scanner(in);

            spritesImages.clear();
            spritesImagesPosition.clear();
            SpriteHandler.instance.getImagesCombo().removeAllItems();
            sprites.entries.clear();

            int noImages = scan.nextInt();
            scan.nextLine();
            for (int i = 0; i < noImages; i++) {
                String line = scan.nextLine();
                String imageFileName = "";
                boolean foundLast = false;
                int it = 1;
                while (it < line.length() && !foundLast) {
                    imageFileName += line.charAt(it);
                    it++;
                    if (line.charAt(it) == '\"') {
                        foundLast = true;
                    }
                }
                String theRest = line.substring(it + 2);
                Scanner miniScan = new Scanner(theRest);
                int posX = miniScan.nextInt();
                int posY = miniScan.nextInt();
                miniScan.close();

                SpriteHandler.instance.addImage(imageFileName, new File(path + imageFileName), posX, posY);
                System.out.println("Loaded image : " + path + imageFileName);
            }

            int noSprites = scan.nextInt();
            scan.nextLine();
            for (int i = 0; i < noSprites; i++) {
                String line = scan.nextLine();
                Sprite spr = Sprite.fromSerial(line);
                sprites.entries.add(spr);
            }
            scan.close();
            in.close();

            SpriteHandler.instance.redrawAll();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.filename = fullname;
    }

    public SpriteSheet pack(String fullname, int targetSize) {
        SpriteSheet packedSheet = new SpriteSheet();

        String trimmedName = fullname;

        String extension = ".sheet";
        String modifier = ".packed";
        String imageExtension = ".spr.png";

        if (trimmedName.endsWith(modifier)) {
            trimmedName = trimmedName.substring(0, trimmedName.length() - modifier.length());
        }
        if (trimmedName.endsWith(extension)) {
            trimmedName = trimmedName.substring(0, trimmedName.length() - extension.length());
        }
        if (trimmedName.endsWith(imageExtension)) {
            trimmedName = trimmedName.substring(0, trimmedName.length() - imageExtension.length());
        }

        String justTheName = trimmedName;
        // look for the right most path limiter
        int position = justTheName.length() - 1;
        while (justTheName.charAt(position) != '\\' && justTheName.charAt(position) != '/') {
            position--;
        }
        justTheName = justTheName.substring(position + 1, justTheName.length());

        int sz = targetSize;
        BufferedImage image = new BufferedImage(sz, sz, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(0, 0, sz, sz);

        SpritePacker packer = new SpritePacker();

        packer.draw(g, this, targetSize);

        try {
            FileOutputStream imageOut;
            imageOut = new FileOutputStream(trimmedName + imageExtension);
            ImageIO.write(image, "png", imageOut);
            imageOut.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        packedSheet.sprites.entries = packer.sprites;

        packedSheet.spritesImages.add(image);
        packedSheet.spritesImagesPath.add(justTheName + imageExtension);
        packedSheet.spritesImagesPosition.add(new Point(0, 0));

        packedSheet.saveSheet(trimmedName + modifier);

        return packedSheet;
    }
}
