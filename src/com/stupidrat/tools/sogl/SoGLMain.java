package com.stupidrat.tools.sogl;

public class SoGLMain {
    public static void main(String[] args){
        new SoGL();
        if(args.length >= 2) {
            SpriteHandler.instance.sheet.openSheet(args[1]);
            if(args.length == 4) {
                if(args[2].compareTo("--export") == 0) {
                    SpriteHandler.instance.sheet.pack(SpriteHandler.instance.sheet.filename, Integer.parseInt(args[3]));
                    System.exit(0);
                }
            }
        }
    }
}
