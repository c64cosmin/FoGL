package com.stupidrat.tools.fogl;
import javax.swing.JButton;

public class MyButton extends JButton{
    private Runnable action;

    public MyButton(String caption, Runnable action){
        this.action = action;
        this.setText(caption);
    }

    public void doAction(){
        this.action.run();
        SpriteHandler.instance.resetFocus();
    }
}
