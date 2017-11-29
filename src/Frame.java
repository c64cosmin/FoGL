import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Frame {
    public Rectangle box;
    public Point center;

    public Frame(Rectangle box, Point center){
        this.box = box;
        this.center = center;
    }

    public void draw(Graphics g, int cameraX, int cameraY, int zoom, int index) {
        int x = (int) (box.getX()-cameraX);
        int y = (int) (box.getY()-cameraY);
        int w = (int) box.getWidth();
        int h = (int) box.getHeight();
        int cx = (int) (center.getX()-cameraX)*SpriteHandler.instance.zoom;
        int cy = (int) (center.getY()-cameraY)*SpriteHandler.instance.zoom;
        g.drawRect(x*SpriteHandler.instance.zoom, y*SpriteHandler.instance.zoom, w*SpriteHandler.instance.zoom, h*SpriteHandler.instance.zoom);
        g.drawLine(cx-4, cy-4, cx+4, cy+4);
        g.drawLine(cx+4, cy-4, cx-4, cy+4);
        //g.drawString("nm:"+spriteName, x*SpriteHandler.instance.zoom, y*SpriteHandler.instance.zoom+15);
        g.drawString("pos:"+x+","+y, x*SpriteHandler.instance.zoom, y*SpriteHandler.instance.zoom+30);
        g.drawString("dim:"+w+","+h, x*SpriteHandler.instance.zoom, y*SpriteHandler.instance.zoom+45);
        if(index!=0)g.drawString("n:" + index, x*SpriteHandler.instance.zoom, y*SpriteHandler.instance.zoom+60);

    }
}
