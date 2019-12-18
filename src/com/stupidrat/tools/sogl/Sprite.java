package com.stupidrat.tools.sogl;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Scanner;

import com.stupidrat.tools.fogl.Frame;

public class Sprite {
    public ArrayList<Frame> frames;
    private String name;

    public String getName(){
        return this.name;
    }

    @Override
    public String toString(){
        return name;
    }

    public Sprite(String name){
        this.name = name;
        frames = new ArrayList<Frame>();
    }

    public void addFrame(Rectangle box, Point center){
        frames.add(new Frame(box, center));
    }

    public int getNumberOfFrames() {
        return frames.size();
    }

    public void delFrame(int selectedFrame) {
        frames.remove(selectedFrame);
    }

    public String getSerial(){
        String ret = "";
        ret += name + " ";
        ret += frames.size() + " ";
        for(int i=0;i<frames.size();i++){
            ret += frames.get(i).getSerial();
        }
        return ret;
    }

    public static Sprite fromSerial(String line) {
        Scanner scan = new Scanner(line);
        String name = scan.next();
        Sprite spr = new Sprite(name);
        int noFrames = scan.nextInt();
        for(int i=0;i<noFrames;i++){
            int x = scan.nextInt();
            int y = scan.nextInt();
            int w = scan.nextInt();
            int h = scan.nextInt();
            int cx = scan.nextInt();
            int cy = scan.nextInt();
            Frame frame = new Frame(new Rectangle(x,y,w,h), new Point(cx,cy));
            spr.frames.add(frame);
        }
        scan.close();
        return spr;
    }
}
