import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JoGL {
	AllFonts fonts = null;
	public static void main(String[] args){
		new JoGL();
	}
	public JoGL(){
		JFrame win = new JFrame();
		fonts = new AllFonts();
		fonts.setWindow(win);
		
		//left side
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(10,1));
		leftPanel.add(new JLabel("Font"));
		leftPanel.add(fonts.getFonts());
		leftPanel.add(new JLabel("Output string mapping"));
		leftPanel.add(fonts.getFontsString());
		leftPanel.add(new JLabel("Font size"));
		leftPanel.add(fonts.getFontsSize());
		leftPanel.add(new JLabel("Output size"));
		leftPanel.add(fonts.getTexSizeX());
		leftPanel.add(fonts.getTexSizeY());
		leftPanel.add(fonts.getGenButton());
		
		//window panel
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(leftPanel, BorderLayout.WEST);
		mainPanel.add(fonts.getCanvas(), BorderLayout.CENTER);

		win.setExtendedState(JFrame.MAXIMIZED_BOTH);
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.add(mainPanel);
		win.setVisible(true);
	}
}
