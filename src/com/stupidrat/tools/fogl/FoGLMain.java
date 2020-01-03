package com.stupidrat.tools.fogl;

import com.stupidrat.tools.sogl.SoGL;
import com.stupidrat.tools.sogl.SpriteHandler;

public class FoGLMain {
    public static void main(String[] args){
        if(args.length >= 1) {
            if(args[0].compareTo("SoGL") == 0) {
                new SoGL();
                if(args.length >= 2) {
                    SpriteHandler.instance.sheet.openSheet(args[1]);
                }
            }
        }else {
            new FoGL();
        }
    }
}
