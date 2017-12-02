import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

public class FontCanvas extends Canvas {
    private static final long serialVersionUID = -5593088216030819037L;
    private Font font;
    private String text;
    private int fontSize;
    private LetterSplitter split;
    private Graphics graphics;

    public FontCanvas() {
        this.setVisible(true);
        fontSize = 10;
        split = new LetterSplitter();
    }

    @Override public void update(Graphics g) {
        paint(g);
    }

    @Override public void paint(Graphics g) {
        graphics = g;
        render(g, true);
    }

    private void render(Graphics g, boolean grid) {
        int w = AllFonts.instance.texSizeX;
        int h = AllFonts.instance.texSizeY;
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, w, h);
        font = font.deriveFont((float) fontSize);
        g.setFont(font);

        drawAllLetters(g, text, grid);
    }

    @Override public void setFont(Font f) {
        this.font = f;
    }

    public void setText(String s) {
        this.text = s;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void drawAllLetters(Graphics g, String str, boolean grid) {
        if (str.isEmpty())
            str = "`1234567890-=qwertyuiop[]\\asdfghjkl;'zxcvbnm,./~!@#$%^&*()_+QWERTYUIOP{}|ASDFGHJKL:\"ZXCVBNM<>?";
        int x = 0;
        int y = 0;
        int n = str.length();
        for (int i = 0; i < n; i++) {
            String c = str.substring(i, i + 1);
            CharProperty prop = split.getCharProperties(g, c);
            if (x + prop.width >= AllFonts.instance.texSizeX) {
                y += prop.height + 1;
                x = 0;
            }
            split.drawChar(g, c, x, y, grid);
            x += prop.width;
        }
    }

    public void exportAllLetters(String imageName, String mapName) {
        int w = AllFonts.instance.texSizeX;
        int h = AllFonts.instance.texSizeY;
        if (text.isEmpty())
            text = "`1234567890-=qwertyuiop[]\\asdfghjkl;'zxcvbnm,./~!@#$%^&*()_+QWERTYUIOP{}|ASDFGHJKL:\"ZXCVBNM<>?";
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, w, h);
        font = font.deriveFont((float) fontSize);
        g.setFont(font);

        try {
            PrintWriter mapOut = new PrintWriter(new File(mapName));
            mapOut.println(w + " " + h);
            int x = 0;
            int y = 0;
            int n = text.length();
            for (int i = 0; i < n; i++) {
                String c = text.substring(i, i + 1);
                CharProperty prop = split.getCharProperties(graphics, c);
                if (x + prop.width >= w) {
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
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
