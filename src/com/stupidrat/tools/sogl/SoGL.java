package com.stupidrat.tools.sogl;
import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SoGL {
    public SpriteHandler handler;
    public SoGL(){
        handler = new SpriteHandler();
        JFrame win = new JFrame();
        handler.setWindow(win);

        //tool side
        JPanel toolPanel = new JPanel();
        toolPanel.setLayout(new GridLayout(6,1));
        toolPanel.add(handler.getZoomInOutButton());
        toolPanel.add(handler.getAddImageButton());
        toolPanel.add(handler.getImagesCombo());
        toolPanel.add(handler.getImageButton());
        toolPanel.add(handler.getSpriteIndicator());
        toolPanel.add(handler.getFrameButton());

        //loop side
        LoopPanel loopPanel = new LoopPanel(handler);

        //componded panel
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(1,2));
        leftPanel.add(loopPanel.getJPanel());
        leftPanel.add(toolPanel);

        //window panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(handler.getCanvas(), BorderLayout.CENTER);

        win.setExtendedState(JFrame.MAXIMIZED_BOTH);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.add(mainPanel);

        SpriteCanvas canvas = handler.getCanvas();
        canvas.setFocusable(true);
        canvas.addKeyListener(handler);
        canvas.addMouseMotionListener(canvas);
        canvas.addMouseListener(canvas);

        win.setVisible(true);

        canvas.requestFocus();
    }
}
