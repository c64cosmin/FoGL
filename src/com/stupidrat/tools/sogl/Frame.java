package com.stupidrat.tools.sogl;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Frame {
    public Rectangle box;
    public Rectangle boundingBox;
    public Point center;

    public Frame(Rectangle box, Rectangle boundingBox, Point center){
        this.box = box;
        this.boundingBox = boundingBox;
        this.center = center;
    }

    public Frame(Frame frame) {
		this.box = (Rectangle) frame.box.clone();
		this.boundingBox = (Rectangle) frame.boundingBox.clone();
		this.center = (Point) frame.center.clone();
	}

	public void drawFrameDecorator(Graphics g, String spriteName, int cameraX, int cameraY, int zoom, int index) {
	    int yDraw = 0;
        int x = (int) (box.getX()-cameraX);
        int y = (int) (box.getY()-cameraY);
        int w = (int) box.getWidth();
        int h = (int) box.getHeight();
        int cx = (int) (center.getX() + x)*SpriteHandler.instance.zoom;
        int cy = (int) (center.getY() + y)*SpriteHandler.instance.zoom;
        g.drawRect(x*SpriteHandler.instance.zoom, y*SpriteHandler.instance.zoom, w*SpriteHandler.instance.zoom, h*SpriteHandler.instance.zoom);
        g.drawLine(cx-4, cy-4, cx+4, cy+4);
        g.drawLine(cx+4, cy-4, cx-4, cy+4);
        g.drawString("nm:"+spriteName, x*SpriteHandler.instance.zoom+2, y*SpriteHandler.instance.zoom+15*(yDraw++));
        g.drawString("pos:"+box.getX()+","+box.getY(), x*SpriteHandler.instance.zoom+2, y*SpriteHandler.instance.zoom+15*(yDraw++));
        g.drawString("dim:"+w+","+h, x*SpriteHandler.instance.zoom+2, y*SpriteHandler.instance.zoom+15*(yDraw++));
        g.drawString("pos:"+boundingBox.getX()+","+boundingBox.getY(), x*SpriteHandler.instance.zoom+2, y*SpriteHandler.instance.zoom+15*(yDraw++));
        g.drawString("dim:"+boundingBox.getWidth()+","+boundingBox.getHeight(), x*SpriteHandler.instance.zoom+2, y*SpriteHandler.instance.zoom+15*(yDraw++));
        g.drawString("cen:"+center.x+","+center.y, x*SpriteHandler.instance.zoom+2, y*SpriteHandler.instance.zoom+15*(yDraw++));
        if(index!=0)g.drawString("n:" + index, x*SpriteHandler.instance.zoom+2, y*SpriteHandler.instance.zoom+15*(yDraw++));
    }
	
	public void drawCollider(Graphics g, int cameraX, int cameraY, int zoom) {
	    int x = (int) (box.getX()+boundingBox.getX()-cameraX);
        int y = (int) (box.getY()+boundingBox.getY()-cameraY);
        int w = (int) boundingBox.getWidth();
        int h = (int) boundingBox.getHeight();
	    g.fillRect(x*SpriteHandler.instance.zoom, y*SpriteHandler.instance.zoom, w*SpriteHandler.instance.zoom, h*SpriteHandler.instance.zoom);
	}

    public String getSerial() {
        String ret = "";
        ret += box.x + " ";
        ret += box.y + " ";
        ret += box.width + " ";
        ret += box.height + " ";
        ret += boundingBox.x + " ";
        ret += boundingBox.y + " ";
        ret += boundingBox.width + " ";
        ret += boundingBox.height + " ";
        ret += center.x + " ";
        ret += center.y + " ";
        return ret;
    }
}
