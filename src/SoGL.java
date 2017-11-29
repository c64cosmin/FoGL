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

        //left side
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(8,1));
        leftPanel.add(handler.getZoomInOutButton());
        leftPanel.add(handler.getAddImageButton());
        leftPanel.add(handler.getImageButton());
        leftPanel.add(handler.getSpritesCombo());
        leftPanel.add(handler.getSpriteNameArea());
        leftPanel.add(handler.getSpriteButton());
        leftPanel.add(handler.getSpriteIndicator());
        leftPanel.add(handler.getFrameButton());

        //window panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(handler.getCanvas(), BorderLayout.CENTER);

        win.setExtendedState(JFrame.MAXIMIZED_BOTH);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.add(mainPanel);
        win.setVisible(true);
    }
}
