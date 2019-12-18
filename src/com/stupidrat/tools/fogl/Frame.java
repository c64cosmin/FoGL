package com.stupidrat.tools.fogl;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import com.stupidrat.tools.sogl.SpriteHandler;

public class Frame {
    public Rectangle box;
    public Point center;

    public Frame(Rectangle box, Point center){
        this.box = box;
        this.center = center;
    }

    public void draw(Graphics g, String spriteName, int cameraX, int cameraY, int zoom, int index) {
        int x = (int) (box.getX()-cameraX);
        int y = (int) (box.getY()-cameraY);
        int w = (int) box.getWidth();
        int h = (int) box.getHeight();
        int cx = (int) (center.getX() + x)*SpriteHandler.instance.zoom;
        int cy = (int) (center.getY() + y)*SpriteHandler.instance.zoom;
        g.drawRect(x*SpriteHandler.instance.zoom, y*SpriteHandler.instance.zoom, w*SpriteHandler.instance.zoom, h*SpriteHandler.instance.zoom);
        g.drawLine(cx-4, cy-4, cx+4, cy+4);
        g.drawLine(cx+4, cy-4, cx-4, cy+4);
        g.drawString("nm:"+spriteName, x*SpriteHandler.instance.zoom+2, y*SpriteHandler.instance.zoom+15);
        g.drawString("pos:"+box.getX()+","+box.getY(), x*SpriteHandler.instance.zoom+2, y*SpriteHandler.instance.zoom+30);
        g.drawString("dim:"+w+","+h, x*SpriteHandler.instance.zoom+2, y*SpriteHandler.instance.zoom+45);
        g.drawString("cen:"+center.x+","+center.y, x*SpriteHandler.instance.zoom+2, y*SpriteHandler.instance.zoom+60);
        if(index!=0)g.drawString("n:" + index, x*SpriteHandler.instance.zoom+2, y*SpriteHandler.instance.zoom+75);

    }

    public String getSerial() {
        String ret = "";
        ret += box.x + " ";
        ret += box.y + " ";
        ret += box.width + " ";
        ret += box.height + " ";
        ret += center.x + " ";
        ret += center.y + " ";
        return ret;
    }
}
