package com.stupidrat.tools.fogl;

import com.stupidrat.tools.sogl.SoGLMain;

public class FoGLMain {
    public static void main(String[] args){
        if(args.length >= 1) {
            if(args[0].compareTo("SoGL") == 0) {
                SoGLMain.main(args);
            }
        }else {
            new FoGL();
        }
    }
}
